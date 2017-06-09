DROP PROCEDURE IF EXISTS create_recipe_component;
DROP PROCEDURE IF EXISTS read_recipe_component;
DROP PROCEDURE IF EXISTS update_recipe_component;
DROP PROCEDURE IF EXISTS delete_recipe_component;

/**
Recipe_component CRUD
 */
DELIMITER //
CREATE PROCEDURE create_recipe_component(IN input_recipe_id INT, IN input_produce_id INT, IN input_netto DOUBLE,
  IN input_tolerance DOUBLE)
BEGIN
  INSERT INTO recipecomponent(recipe_id, produce_id, nom_netto, tolerance)
  VALUES(input_recipe_id, input_produce_id, input_netto, input_tolerance);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE read_recipe_component(IN input_recipe_id INT, IN input_produce_id INT)
BEGIN
  SELECT *
  FROM recipecomponent
  WHERE recipecomponent.recipe_id = input_recipe_id AND recipecomponent.produce_id = input_produce_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_recipe_component(IN input_recipe_id INT, IN input_produce_id INT, IN input_netto DOUBLE,
  IN input_tolerance DOUBLE)
BEGIN
  UPDATE recipecomponent
  SET nom_netto = input_netto, tolerance = input_tolerance
  WHERE recipecomponent.recipe_id = input_recipe_id AND recipecomponent.produce_id = input_produce_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_recipe_component(IN input_recipe_id INT, IN input_produce_id INT)
BEGIN
  DELETE FROM recipecomponent
  WHERE recipecomponent.recipe_id = input_recipe_id AND recipecomponent.produce_id = input_produce_id;
END //
DELIMITER ;

/**
Recipe_component related
 */
