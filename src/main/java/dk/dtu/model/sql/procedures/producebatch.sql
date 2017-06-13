DROP PROCEDURE IF EXISTS create_produce_batch;
DROP PROCEDURE IF EXISTS read_produce_batch;
DROP PROCEDURE IF EXISTS update_produce_batch;
DROP PROCEDURE IF EXISTS delete_produce_batch;

DROP PROCEDURE IF EXISTS read_produce_batch_list;

DROP PROCEDURE IF EXISTS amount_of_produce_in_stock;

/**
Produce_batch CRUD
 */
DELIMITER //
CREATE PROCEDURE create_produce_batch(IN input_rb_id INT, IN input_produce_id INT, IN input_amount DOUBLE)
  BEGIN
    INSERT INTO producebatch(rb_id, produce_id, amount)
    VALUES(input_rb_id, input_produce_id, input_amount);
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE read_produce_batch(IN input_rb_id INT)
BEGIN
  SELECT *
  FROM producebatch
  WHERE producebatch.rb_id = input_rb_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_produce_batch(IN input_rb_id INT, IN input_amount DOUBLE)
BEGIN
  UPDATE producebatch
  SET amount = input_amount
  WHERE producebatch.rb_id = input_rb_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_produce_batch(IN input_rb_id INT)
BEGIN
  DELETE FROM producebatch
  WHERE producebatch.rb_id = input_rb_id;
END //
DELIMITER ;

/**
Produce_batch related
 */
DELIMITER //
CREATE PROCEDURE read_produce_batch_list(IN input_rb_id INT)
BEGIN
  SELECT *
  FROM produce_batch_list
  WHERE produce_batch_list.rb_id = input_rb_id;
END //
DELIMITER ;

/**
Q2 - Shows the amount of specified produce_name that is in stock.
 */
DELIMITER //
CREATE PROCEDURE amount_of_produce_in_stock(IN produce_name TEXT)
BEGIN
  SELECT (
    SELECT sum(producebatch.amount)
    FROM produce NATURAL JOIN producebatch
    WHERE produce_name = produce.produce_name)
  AS amount_in_stock;
END //
DELIMITER ;