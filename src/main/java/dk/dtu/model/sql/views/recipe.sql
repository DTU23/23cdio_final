/**
Pharmacist
*/
CREATE OR REPLACE VIEW recipe_list AS
  SELECT recipe_id, recipe_name, produce_id, produce_name, nom_netto, tolerance
  FROM recipe NATURAL JOIN recipecomponent NATURAL JOIN produce
  ORDER BY recipe_id;

/**
Task 2 - Creates a view from recipe component that shows the recipe id, the recipe name
and the produce name. (Pharmacist)
 */
CREATE OR REPLACE VIEW recipe_overview AS
  SELECT recipe_id, recipe_name, produce_name
  FROM recipe NATURAL JOIN recipecomponent NATURAL JOIN produce;