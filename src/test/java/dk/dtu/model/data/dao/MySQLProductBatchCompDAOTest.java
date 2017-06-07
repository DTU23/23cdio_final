package dk.dtu.model.data.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dao.MySQLProductBatchCompDAO;
import dk.dtu.model.dao.MySQLProductBatchDAO;
import dk.dtu.model.dto.ProductBatchCompDTO;
import dk.dtu.model.dto.ProductBatchCompOverviewDTO;
import dk.dtu.model.dto.ProductBatchCompSupplierDetailsDTO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.interfaces.DALException;

public class MySQLProductBatchCompDAOTest {
    private MySQLProductBatchCompDAO pbcdao;
    private MySQLProductBatchDAO pbdao;
    @Before
    public void setUp() throws Exception {
        new Connector();
        Connector.resetData();
        pbcdao = new MySQLProductBatchCompDAO();
        pbdao = new MySQLProductBatchDAO();
    }

    @After
    public void tearDown() throws Exception {
        pbcdao = null;
        Connector.resetData();
    }

    /**
     * Positive test for getProductBatchComp
     * @throws Exception
     */
    @Test
    public void getProductBatchComp() throws Exception {
        ProductBatchCompDTO pbc1;
        ProductBatchCompDTO expected = new ProductBatchCompDTO(1, 1, 0.5, 10.05, 1);
        pbc1 = pbcdao.readProductBatchComp(1, 1);
        assertThat(pbc1.toString(), is(equalTo(expected.toString())));
    }

    /**
     * Negative test for getProductBatchComp
     * @throws Exception
     */
    @Test (expected = DALException.class)
    public void getProductBatchCompWithNonExistentID() throws Exception {
        ProductBatchCompDTO pbc1;
        pbc1 = pbcdao.readProductBatchComp(-1, -1);
        assertThat(pbc1, nullValue());
    }

    /**
     * Tests that the trigger correctly updates product batch status on inserts
     * @throws Exception
     */
    @Test
    public void testStatusTrigger() throws Exception{
        ProductBatchDTO pb4 = pbdao.readProductBatch(4);
        assertThat(pb4.getStatus(), is(equalTo(1)));
        ProductBatchCompDTO pbc1 = new ProductBatchCompDTO(4, 2, 5, 10, 1);
        pbcdao.createProductBatchComp(pbc1);
        pb4 = pbdao.readProductBatch(4);
        assertThat(pb4.getStatus(), is(equalTo(2)));
    }
    /**
     * Positive test for createProductBatchComp
     * @throws Exception
     */
    @Test
    public void createProductBatchComp() throws Exception {
        int pb_id = 5;
        int batchCountBefore = pbcdao.getProductBatchCompList().size();
        pbcdao.createProductBatchComp(new ProductBatchCompDTO(pb_id, 1, 10, 10, 1));
        int batchCountAfter = pbcdao.getProductBatchCompList().size();
        assertEquals(batchCountBefore, batchCountAfter-1);
        assertThat(pbdao.readProductBatch(pb_id).getStatus(), is(not(0)));
    }

    /**
     * Negative test for createProductBatchComp
     * @throws Exception
     */
    @Test(expected = DALException.class)
    public void createProductBatchCompWithInvalidID() throws Exception {
        int batchCountBefore = pbcdao.getProductBatchCompList().size();
        ProductBatchCompDTO pbc = new ProductBatchCompDTO(-1, 1, 10, 10, 1);
        pbcdao.createProductBatchComp(pbc);
        int batchCountAfter = pbcdao.getProductBatchCompList().size();
        assertEquals(batchCountBefore, batchCountAfter);
    }

    /**
     * Negative test for createProductBatchComp
     * @throws Exception
     */
    @Test(expected = DALException.class)
    public void createProductBatchCompWithInvalidNetto() throws Exception {
        int batchCountBefore = pbcdao.getProductBatchCompList().size();
        pbcdao.createProductBatchComp(new ProductBatchCompDTO(1, 1, 10, -10, 1));
        int batchCountAfter = pbcdao.getProductBatchCompList().size();
        assertEquals(batchCountBefore, batchCountAfter);
    }

    /**
     * Negative test for createProductBatchComp
     * @throws Exception
     */
    @Test(expected = DALException.class)
    public void createProductBatchCompWithInvalidTara() throws Exception {
        int batchCountBefore = pbcdao.getProductBatchCompList().size();
        pbcdao.createProductBatchComp(new ProductBatchCompDTO(1, 1, -10, 10, 1));
        int batchCountAfter = pbcdao.getProductBatchCompList().size();
        assertEquals(batchCountBefore, batchCountAfter);
    }

    /**
     * Positive test for getProductBatchCompList
     * @throws Exception
     */
    @Test
    public void getProductBatchCompList() throws Exception {
        List<ProductBatchCompDTO> pbcl;
        pbcl = pbcdao.getProductBatchCompList();
        assertThat(pbcl, notNullValue());
    }

    /**
     * Positive test for getProductBatchCompOverview
     * @throws Exception
     */
    @Test
    public void getProductBatchCompOverview() throws Exception {
        List<ProductBatchCompOverviewDTO> pbco;
        pbco = pbcdao.getProductBatchCompOverview();
        assertThat(pbco, notNullValue());
    }

    /**
     * Positive test for getSupplierDetailById
     * @throws Exception
     */
    @Test
    public void getSupplierDetailById() throws Exception {
        List<ProductBatchCompSupplierDetailsDTO> pbcsd;
        pbcsd = pbcdao.getSupplierDetailById(1);
        assertThat(pbcsd, notNullValue());
    }

    /**
     * Negative test for getSupplierDetailById
     * @throws Exception
     */
    @Test(expected = DALException.class)
    public void getSupplierDetailByNonExistentId() throws Exception {
        List<ProductBatchCompSupplierDetailsDTO> pbcsd;
        pbcsd = pbcdao.getSupplierDetailById(-1);
        assertTrue(pbcsd.size() == 0);
    }
}