DROP PROCEDURE IF EXISTS create_product_batch_component;
DROP PROCEDURE IF EXISTS read_product_batch_component;
DROP PROCEDURE IF EXISTS update_product_batch_component;
DROP PROCEDURE IF EXISTS delete_product_batch_component;

DROP PROCEDURE IF EXISTS get_product_batch_component_supplier_details_by_pb_id;

DROP PROCEDURE IF EXISTS number_of_product_batch_components_with_weight_greater_than;

/**
Product_batch_component CRUD
 */
DELIMITER //
CREATE PROCEDURE create_product_batch_component(IN input_pb_id INT, IN input_rb_id INT, IN input_tara DOUBLE,
  IN input_netto DOUBLE, IN input_opr_id INT)
BEGIN
  INSERT INTO productbatchcomponent(pb_id,rb_id,tara,netto,opr_id)
  VALUES(input_pb_id,input_rb_id,input_tara,input_netto,input_opr_id);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE read_product_batch_component(IN input_pb_id INT, IN input_rb_id INT)
BEGIN
  SELECT *
  FROM productbatchcomponent
  WHERE productbatchcomponent.pb_id = input_pb_id AND productbatchcomponent.rb_id = input_rb_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_product_batch_component(IN input_pb_id INT, IN input_rb_id INT, IN input_tara DOUBLE,
  IN input_netto DOUBLE, IN input_opr_id INT)
BEGIN
  UPDATE productbatchcomponent
  SET tara = input_tara, netto = input_netto, opr_id = input_opr_id
  WHERE productbatchcomponent.pb_id = input_pb_id AND productbatchcomponent.rb_id = input_rb_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_product_batch_component(IN input_pb_id INT, IN input_rb_id INT)
BEGIN
  DELETE FROM productbatchcomponent
  WHERE productbatchcomponent.pb_id = input_pb_id AND productbatchcomponent.rb_id = input_rb_id;
END //
DELIMITER ;

/**
Produce_batch_component related
 */
DELIMITER //
CREATE PROCEDURE get_product_batch_component_supplier_details_by_pb_id(IN input_pb_id INT)
BEGIN
  SELECT productbatchcomponent.rb_id, produce_name, supplier, netto, opr_id
  FROM productbatchcomponent NATURAL JOIN produce NATURAL JOIN producebatch
  WHERE productbatchcomponent.pb_id = input_pb_id;
END //
DELIMITER ;

/**
Q1 - Counts the number og product batch components weighing more than specified weight.
 */
DELIMITER //
CREATE PROCEDURE number_of_product_batch_components_with_weight_greater_than(IN weight INT)
BEGIN
  SELECT count(*) AS count
  FROM productbatchcomponent
  WHERE netto > weight;
END //
DELIMITER ;