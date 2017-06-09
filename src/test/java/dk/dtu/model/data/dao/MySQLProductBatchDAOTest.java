package dk.dtu.model.data.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.model.connector.Connector;
import dk.dtu.model.dao.MySQLProductBatchDAO;
import dk.dtu.model.dto.ProductBatchDTO;
import dk.dtu.model.dto.ProductBatchListDTO;
import dk.dtu.model.interfaces.DALException;

public class MySQLProductBatchDAOTest {
    private MySQLProductBatchDAO pbdao;
    @Before
    public void setUp() throws Exception {
    	Connector.getInstance().resetData();
        pbdao = new MySQLProductBatchDAO();
    }

    @After
    public void tearDown() throws Exception {
    	Connector.getInstance().resetData();
        pbdao = null;
    }

    /**
     * Positive test for getProductBatch
     * @throws Exception
     */
    @Test
    public void getProductBatch() throws Exception {
        ProductBatchDTO pb1, expected;
        expected = new ProductBatchDTO(1, 1, 2);
        pb1 = pbdao.readProductBatch(1);
        assertThat(pb1.toString(), is(equalTo(expected.toString())));
    }

    /**
     * Positive test for getProductBatchList
     * @throws Exception
     */
    @Test
    public void getProductBatchList() throws Exception {
        List<ProductBatchListDTO> list;
        list = pbdao.getProductBatchList();
        assertThat(list, notNullValue());
    }

    /**
     * Positive test for createProductBatch
     * @throws Exception
     */
    @Test
    public void createProductBatch() throws Exception {
        int batchCountBefore = pbdao.getProductBatchList().size();
        pbdao.createProductBatch(1);
        int batchCountAfter = pbdao.getProductBatchList().size();
        assertEquals(batchCountBefore, batchCountAfter-1);
    }

    /**
     * Negative test for createProductBatch
     * @throws Exception
     */
    @Test(expected=DALException.class)
    public void createProductBatchWithInvalidRecipeID() throws Exception {
        int batchCountBefore = pbdao.getProductBatchList().size();
        pbdao.createProductBatch(-1);
        int batchCountAfter = pbdao.getProductBatchList().size();
        assertEquals(batchCountBefore, batchCountAfter);
    }

    /**
     * Positive test for updateProductBatch
     * @throws Exception
     */
    @Test
    public void updateProductBatchStatus() throws Exception {
        ProductBatchDTO beforeEdit = pbdao.readProductBatch(1);
        ProductBatchDTO afterEdit = pbdao.readProductBatch(1);
        afterEdit.setStatus(0);

        pbdao.updateProductBatch(afterEdit);

        assertThat(afterEdit.toString(), is(equalTo(pbdao.readProductBatch(1).toString())));
        assertThat(beforeEdit.toString(), is(not(equalTo(pbdao.readProductBatch(1).toString()))));
    }

    /**
     * Negative test for updateProductBatch
     * @throws Exception
     */
    @Test(expected = DALException.class)
    public void updateProductBatchWithInvalidStatus() throws Exception {
        ProductBatchDTO beforeEdit = pbdao.readProductBatch(1);
        ProductBatchDTO afterEdit = pbdao.readProductBatch(1);
        afterEdit.setStatus(-1);

        pbdao.updateProductBatch(afterEdit);

        assertThat(afterEdit.toString(), is(not(equalTo(pbdao.readProductBatch(1).toString()))));
        assertThat(beforeEdit.toString(), is(equalTo(pbdao.readProductBatch(1).toString())));
    }
}