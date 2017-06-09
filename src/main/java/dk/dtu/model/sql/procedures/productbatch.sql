DROP PROCEDURE IF EXISTS create_product_batch;
DROP PROCEDURE IF EXISTS read_product_batch;
DROP PROCEDURE IF EXISTS update_product_batch;
DROP PROCEDURE IF EXISTS delete_product_batch;

DROP PROCEDURE IF EXISTS get_product_batch_details_by_pb_id;
DROP PROCEDURE IF EXISTS get_product_batch_list_details_by_pb_id;

DROP PROCEDURE IF EXISTS get_product_batch_with_largest_quantity;
DROP PROCEDURE IF EXISTS get_involved_operator;

/**
Product_batch CRUD
 */
DELIMITER //
CREATE PROCEDURE create_product_batch(IN input_recipe_id INT)
BEGIN
  INSERT INTO productbatch(recipe_id, productbatch.status)
  VALUES(input_recipe_id, 0);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE read_product_batch(IN input_pb_id INT)
BEGIN
  SELECT *
  FROM productbatch
  WHERE productbatch.pb_id = input_pb_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_product_batch(IN input_pb_id INT, IN input_recipe_id INT, IN input_status INT)
BEGIN
  UPDATE productbatch
  SET productbatch.recipe_id = input_recipe_id, productbatch.status = input_status
  WHERE productbatch.pb_id = input_pb_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_product_batch(IN input_pb_id INT)
BEGIN
  DELETE FROM productbatch
  WHERE productbatch.pb_id = input_pb_id;
END //
DELIMITER ;

/**
Produce_batch related
 */
DELIMITER //
CREATE PROCEDURE get_product_batch_details_by_pb_id(IN pb_id_input INT)
BEGIN
  SELECT *
  FROM product_batch_component_overview
  WHERE pb_id = pb_id_input;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_product_batch_list_details_by_pb_id(IN input_pb_id INT)
BEGIN
  SELECT *
  FROM product_batch_list
  WHERE pb_id = input_pb_id;
END //
DELIMITER ;

/**
Task 7 - Finds which product batch has the largest quantity of a certain produce (by produce name)
 */
DELIMITER //
CREATE PROCEDURE get_product_batch_with_largest_quantity(IN input_produce_name TEXT)
BEGIN
  SELECT pb_id, produce_name, netto
  FROM ((
    SELECT pb_id, produce_name, netto
    FROM product_batch_component_overview
    WHERE produce_name = input_produce_name) AS T)
  WHERE netto = (SELECT MAX(netto)
                 FROM(
                    SELECT pb_id, produce_name, netto
                    FROM product_batch_component_overview
                    WHERE produce_name = input_produce_name) AS T2);
END //
DELIMITER ;

/**
Task 8 - Finds the operator(s) that has created a specific recipe
 */
DELIMITER //
CREATE PROCEDURE get_involved_operator(IN input_recipe_name TEXT)
BEGIN
  SELECT DISTINCT(opr_name) FROM product_batch_component_overview NATURAL JOIN operator
  WHERE recipe_name = input_recipe_name;
END //
DELIMITER ;