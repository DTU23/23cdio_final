package dk.dtu.control;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.model.connector.DataSource;
import dk.dtu.model.dao.MySQLOperatorDAO;
import dk.dtu.model.dto.OperatorDTO;
import dk.dtu.model.dto.OperatorNewPWDTO;
import dk.dtu.model.exceptions.AuthException;
import dk.dtu.model.exceptions.DALException;
import dk.dtu.model.exceptions.ValidationException;
import dk.dtu.model.interfaces.OperatorDAO;

public class WebInterfaceControllerTest {

	IWebInterfaceController ctrl;
	OperatorDAO dao;

	@Before
	public void setUp() throws Exception {
		DataSource.getInstance().resetData();
		ctrl = new WebInterfaceController();
		dao = new MySQLOperatorDAO();
	}

	@After
	public void tearDown() throws Exception {
		DataSource.getInstance().resetData();
		ctrl = null;
		dao = null;
	}

	@Test
	public void editUserWithPWTest() throws DALException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorDTO editObject = null;
		oprBeforeEdit = dao.readOperator(1);
		editObject = new OperatorDTO(oprBeforeEdit);
		editObject.setOprName("John Doe");
		editObject.setIni("JD");
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(equalTo(editObject.toString())));
		assertThat(oprAfterEdit.toString(), is(not(equalTo(oprBeforeEdit.toString()))));
	}

	@Test
	public void editUserWithoutPWTest() throws DALException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorDTO editObject = null;
		oprBeforeEdit = dao.readOperator(1);
		editObject = new OperatorDTO(oprBeforeEdit);
		editObject.setOprName("John Doe");
		editObject.setIni("JD");
		editObject.setPassword(null);
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(equalTo(editObject.toString())));
		assertThat(oprAfterEdit.toString(), is(not(equalTo(oprBeforeEdit.toString()))));
	}

	@Test
	public void editUserAdminTest() throws DALException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorDTO editObject = null;
		oprBeforeEdit = dao.readOperator(1);
		editObject = new OperatorDTO(oprBeforeEdit);
		if(oprBeforeEdit.isAdmin()) {
			editObject.setAdmin(false);
		} else {
			editObject.setAdmin(true);
		}
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(equalTo(editObject.toString())));
		assertThat(oprAfterEdit.toString(), is(not(equalTo(oprBeforeEdit.toString()))));
	}

	@Test
	public void editUserRoleTest() throws DALException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorDTO editObject = null;
		oprBeforeEdit = dao.readOperator(1);
		editObject = new OperatorDTO(oprBeforeEdit);
		if(oprBeforeEdit.getRole().equals("Operator")) {
			editObject.setRole("Foreman");
		} else {
			editObject.setRole("Operator");
		}
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(equalTo(editObject.toString())));
		assertThat(oprAfterEdit.toString(), is(not(equalTo(oprBeforeEdit.toString()))));
	}

	@Test
	public void editProfileWithPWTest() throws DALException, AuthException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorNewPWDTO editObject = null;
		OperatorDTO controlObject = null;
		oprBeforeEdit = dao.readOperator(1);
		controlObject = new OperatorDTO(oprBeforeEdit);
		controlObject.setOprName("John Doe");
		controlObject.setIni("JD");
		editObject = new OperatorNewPWDTO(controlObject);
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(equalTo(controlObject.toString())));
		assertThat(oprAfterEdit.toString(), is(not(equalTo(oprBeforeEdit.toString()))));
	}

	@Test
	public void editProfileWithNewPWTest() throws DALException, AuthException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorNewPWDTO editObject = null;
		OperatorDTO controlObject = null;
		oprBeforeEdit = dao.readOperator(1);
		editObject = new OperatorNewPWDTO(oprBeforeEdit);
		editObject.setNewPassword("n3w p4sSw0rD");
		controlObject = new OperatorDTO(editObject);
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(equalTo(controlObject.toString())));
		assertThat(oprAfterEdit.toString(), is(not(equalTo(oprBeforeEdit.toString()))));
	}

	@Test(expected = AuthException.class)
	public void editProfileWithoutPWTest() throws DALException, AuthException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorNewPWDTO editObject = null;
		OperatorDTO controlObject = null;
		oprBeforeEdit = dao.readOperator(1);
		editObject = new OperatorNewPWDTO(oprBeforeEdit);
		editObject.setOprName("John Doe");
		editObject.setIni("JD");
		editObject.setPassword(null);
		controlObject = new OperatorDTO(editObject);
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(not(equalTo(controlObject.toString()))));
		assertThat(oprAfterEdit.toString(), is(equalTo(oprBeforeEdit.toString())));
	}
	
	@Test(expected = AuthException.class)
	public void editProfileWithEmptyStringPWTest() throws DALException, AuthException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorNewPWDTO editObject = null;
		OperatorDTO controlObject = null;
		oprBeforeEdit = dao.readOperator(1);
		editObject = new OperatorNewPWDTO(oprBeforeEdit);
		editObject.setOprName("John Doe");
		editObject.setIni("JD");
		editObject.setPassword("");
		controlObject = new OperatorDTO(editObject);
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(not(equalTo(controlObject.toString()))));
		assertThat(oprAfterEdit.toString(), is(equalTo(oprBeforeEdit.toString())));
	}

	@Test(expected = AuthException.class)
	public void editProfileWithWrongPWTest() throws DALException, AuthException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorNewPWDTO editObject = null;
		OperatorDTO controlObject = null;
		oprBeforeEdit = dao.readOperator(1);
		editObject = new OperatorNewPWDTO(oprBeforeEdit);
		editObject.setOprName("John Doe");
		editObject.setIni("JD");
		editObject.setPassword("wrong password");
		controlObject = new OperatorDTO(editObject);
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(not(equalTo(controlObject.toString()))));
		assertThat(oprAfterEdit.toString(), is(equalTo(oprBeforeEdit.toString())));
	}

	@Test
	public void editProfileAdminTest() throws DALException, AuthException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorNewPWDTO editObject = null;
		OperatorDTO controlObject = null;
		oprBeforeEdit = dao.readOperator(1);
		controlObject = new OperatorDTO(oprBeforeEdit);
		if(oprBeforeEdit.isAdmin()) {
			controlObject.setAdmin(false);
		} else {
			controlObject.setAdmin(true);
		}
		editObject = new OperatorNewPWDTO(controlObject);
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(not(equalTo(controlObject.toString()))));
		assertThat(oprAfterEdit.toString(), is(equalTo(oprBeforeEdit.toString())));
	}

	@Test
	public void editProfileRoleTest() throws DALException, AuthException, ValidationException {
		OperatorDTO oprBeforeEdit = null;
		OperatorDTO oprAfterEdit = null;
		OperatorNewPWDTO editObject = null;
		OperatorDTO controlObject = null;
		oprBeforeEdit = dao.readOperator(1);
		controlObject = new OperatorDTO(oprBeforeEdit);
		if(oprBeforeEdit.getRole().equals("Operator")) {
			controlObject.setRole("Foreman");
		} else {
			controlObject.setRole("Operator");
		}
		editObject = new OperatorNewPWDTO(controlObject);
		ctrl.updateOperatorValidation(editObject);
		oprAfterEdit = dao.readOperator(1);
		assertThat(oprAfterEdit.toString(), is(not(equalTo(controlObject.toString()))));
		assertThat(oprAfterEdit.toString(), is(equalTo(oprBeforeEdit.toString())));
	}
}
