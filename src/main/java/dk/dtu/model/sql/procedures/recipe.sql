DROP PROCEDURE IF EXISTS create_recipe;
DROP PROCEDURE IF EXISTS read_recipe;
DROP PROCEDURE IF EXISTS update_recipe;
DROP PROCEDURE IF EXISTS delete_recipe;

DROP PROCEDURE IF EXISTS get_recipe_details_by_id;

DROP PROCEDURE IF EXISTS recipe_name_of_recipes_containing_one_of_two_ingredients;
DROP PROCEDURE IF EXISTS recipe_name_of_recipes_containing_two_ingredients;
DROP PROCEDURE IF EXISTS recipe_name_of_recipes_not_containing_ingredient;
DROP PROCEDURE IF EXISTS recipe_containing_most_of_ingredient;

/**
Recipe CRUD
 */
DELIMITER //
CREATE PROCEDURE create_recipe(IN input_recipe_name TEXT)
BEGIN
  INSERT INTO recipe(recipe_name)
  VALUES(input_recipe_name);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE read_recipe(IN input_recipe_id INT)
BEGIN
  SELECT *
  FROM recipe
  WHERE input_recipe_id = recipe_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_recipe(IN input_recipe_id INT, IN input_recipe_name TEXT)
BEGIN
  UPDATE recipe
  SET recipe_name = input_recipe_name
  WHERE input_recipe_id = recipe_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_recipe(IN input_recipe_id INT)
BEGIN
  DELETE FROM recipe
  WHERE input_recipe_id = recipe_id;
END //
DELIMITER ;

/**
Recipe related
 */
DELIMITER //
CREATE PROCEDURE get_recipe_details_by_id(recipe_id_input INT)
BEGIN
  SELECT *
  FROM recipe_list
  WHERE recipe_id = recipe_id_input;
END //
DELIMITER ;

/**
Task 3 - Part 1 of task 3. Creates a list of recipes containing the ingredients 'skinke' OR 'champignon'
*/
DELIMITER //
CREATE PROCEDURE recipe_name_of_recipes_containing_one_of_two_ingredients(IN first_ing TEXT, IN second_ing TEXT)
BEGIN
  SELECT DISTINCT recipe_name
  FROM recipe NATURAL JOIN recipecomponent NATURAL JOIN produce
  WHERE produce_name = first_ing OR produce_name =second_ing;
END //
DELIMITER ;

/**
Task 3 - Part 2 of task 3. Creates a list of recipes containing the ingredients 'skinke' AND 'champignon'
*/
DELIMITER //
CREATE PROCEDURE recipe_name_of_recipes_containing_two_ingredients(IN first_ing TEXT, IN second_ing TEXT)
BEGIN
  SELECT t1.recipe_name
  FROM(((
      SELECT recipe_name
      FROM recipecomponent NATURAL JOIN produce NATURAL JOIN recipe
      WHERE produce_name = first_ing) AS t1)
    INNER JOIN((
      SELECT recipe_name
      FROM recipecomponent NATURAL JOIN produce NATURAL JOIN recipe
      WHERE produce_name =second_ing) AS t2)
    ON t1.recipe_name = t2.recipe_name);
END //
DELIMITER ;

/**
Task 4 - Creates a list of recipes not containing the ingredient 'champignon'
*/
DELIMITER //
CREATE PROCEDURE recipe_name_of_recipes_not_containing_ingredient(IN ingredient TEXT)
BEGIN
  SELECT DISTINCT recipe_name
  FROM recipe NATURAL JOIN recipecomponent
  WHERE NOT recipe_id =(
    SELECT recipe_id
    FROM produce NATURAL JOIN recipecomponent
    WHERE produce_name = ingredient);
END //
DELIMITER ;

/**
Task 5 - Shows the recipe, which contains the largest amount of an ingredient
 */
DELIMITER //
CREATE PROCEDURE recipe_containing_most_of_ingredient(IN ingredient TEXT)
BEGIN
  SELECT recipe_name, produce_name, nom_netto
  FROM recipecomponent NATURAL JOIN recipe NATURAL JOIN produce
  WHERE produce_name = ingredient AND nom_netto = (
    SELECT MAX(nom_netto)
    FROM recipecomponent NATURAL JOIN produce NATURAL JOIN recipe
    WHERE produce_name = ingredient);
END //
DELIMITER ;