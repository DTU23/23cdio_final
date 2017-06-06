DROP PROCEDURE IF EXISTS create_operator;
DROP PROCEDURE IF EXISTS read_operator;
DROP PROCEDURE IF EXISTS update_operator;
DROP PROCEDURE IF EXISTS delete_operator;

/**
Operator CRUD
 */
DELIMITER //
CREATE PROCEDURE create_operator(IN input_id INT, IN input_name TEXT, IN input_initials TEXT,
  IN input_cpr TEXT, IN input_password TEXT, IN input_admin BOOLEAN, IN input_role TEXT)
BEGIN
  INSERT INTO operator(opr_id, opr_name, ini, cpr, password, admin, role)
  VALUES(input_id,input_name,input_initials,input_cpr,input_password,input_admin,input_role);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE read_operator(IN input_id INT)
BEGIN
  SELECT *
  FROM operator
  WHERE input_id = opr_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_operator(IN input_id INT, IN input_name TEXT, IN input_initials TEXT,
  IN input_cpr TEXT, IN input_password TEXT, IN input_admin BOOLEAN, IN input_role TEXT)
BEGIN
  UPDATE operator
  SET opr_name = input_name, ini = input_initials, cpr = input_cpr, password = input_password,
    admin = input_admin, role = input_role
  WHERE input_id = opr_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_operator(IN input_id INT)
BEGIN
  DELETE FROM operator
  WHERE input_id = opr_id;
END //
DELIMITER ;

/**
Operator related
 */
