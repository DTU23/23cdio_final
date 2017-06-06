DROP PROCEDURE IF EXISTS create_produce;
DROP PROCEDURE IF EXISTS read_produce;
DROP PROCEDURE IF EXISTS update_produce;
DROP PROCEDURE IF EXISTS delete_produce;

DROP PROCEDURE IF EXISTS produce_with_at_least_number_occurrences_in_produce_batch;
DROP PROCEDURE IF EXISTS ingredients_that_is_contained_in_number_of_recipes;

/**
Produce CRUD
 */
DELIMITER //
CREATE PROCEDURE create_produce(IN input_produce_name TEXT, IN input_supplier TEXT)
BEGIN
  INSERT INTO produce(produce_name, supplier) VALUES(input_produce_name, input_supplier);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE read_produce(IN input_produce_id INT)
BEGIN
  SELECT *
  FROM produce
  WHERE input_produce_id = produce_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_produce(IN input_produce_id INT, IN input_produce_name TEXT, IN input_supplier TEXT)
BEGIN
  UPDATE produce
  SET produce_name = input_produce_name, supplier = input_supplier
  WHERE input_produce_id = produce_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_produce(IN input_produce_id INT)
BEGIN
  DELETE FROM produce
  WHERE input_produce_id = produce_id;
END //
DELIMITER ;

/**
Produce related
 */

/**
Task 1 - Shows produce with at least 'x' amount of occurrences in produce batches.
We assume that the supplier is without significance of the shown produces.
*/
DELIMITER //
CREATE PROCEDURE produce_with_at_least_number_occurrences_in_produce_batch(IN produce_count INT)
BEGIN
  SELECT produce_name
  FROM produce NATURAL JOIN producebatch
  GROUP BY produce_name
  HAVING count(produce_name) >= produce_count;
END //
DELIMITER ;
/**
Q4 - Returns a list with the names of ingredients that are contained in the specified
number or more recipes.
 */
DELIMITER //
CREATE PROCEDURE ingredients_that_is_contained_in_number_of_recipes(IN times_contained INT)
BEGIN
  SELECT produce_name
  FROM ((
    SELECT produce_name, count(produce_name) AS produce_count
    FROM recipecomponent NATURAL JOIN produce
    GROUP BY produce_name) AS T)
  WHERE produce_count >= times_contained;
END //
DELIMITER ;
