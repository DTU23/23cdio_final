/**
operator (Admin)
 */
CREATE OR REPLACE VIEW operator_list AS
  SELECT opr_id, ini, opr_name, cpr, admin, role
  FROM operator;

/**
produce (Foreman)
 */
CREATE OR REPLACE VIEW produce_overview AS
  SELECT produce.produce_id, produce.produce_name, producebatch.amount
  FROM produce NATURAL JOIN producebatch;

/**
Q3 - Shows an overview of how much os each produce type is in stock.
 */
CREATE OR REPLACE VIEW produce_overview_sum AS
  SELECT produce.produce_name, SUM(producebatch.amount) AS "amount"
  FROM produce NATURAL JOIN producebatch
  GROUP BY produce.produce_name;

/**
produce_batch (Foreman)
 */
CREATE OR REPLACE VIEW produce_batch_list AS
  SELECT producebatch.rb_id, produce.produce_name, produce.supplier, producebatch.amount
  FROM producebatch INNER JOIN produce WHERE produce.produce_id = producebatch.produce_id;

/**
product_batch (Foreman)
*/
CREATE OR REPLACE VIEW product_batch_list AS
  SELECT productbatch.recipe_id, productbatch.status, recipe.recipe_name
  FROM productbatch NATURAL JOIN recipe;

/**
product_batch (Foreman)
Task 6 - create view to select product batch id, produce batch id and netto weighting
*/
CREATE OR REPLACE VIEW product_batch_netto_list AS
    SELECT pb_id, rb_id, netto
    FROM productbatchcomponent;

/**
product_batch_component (Pharmacist)
Task 9 - Creates a view to display various information across recipe, productbatch, productbatchcomponent and producebatch + produce
*/
CREATE OR REPLACE VIEW product_batch_component_overview AS
  SELECT pb_id, recipe.recipe_id, recipe_name, productbatch.status, produce_name, netto, opr_id
  FROM recipe NATURAL JOIN productbatch NATURAL JOIN productbatchcomponent
  NATURAL JOIN producebatch NATURAL JOIN produce
  ORDER BY pb_id;

/**
recipe (Pharmacist)
*/
CREATE OR REPLACE VIEW recipe_list AS
  SELECT recipe_id, recipe_name, produce_id, produce_name, nom_netto, tolerance
  FROM recipe NATURAL JOIN recipecomponent NATURAL JOIN produce
  ORDER BY recipe_id;

/**
recipe (Pharmacist)
Task 2 - Creates a view from recipecomponent that shows the recipe id, the recipe name and the produce name
 */
CREATE OR REPLACE VIEW recipe_overview AS
  SELECT recipe_id, recipe_name, produce_name
  FROM recipe NATURAL JOIN recipecomponent NATURAL JOIN produce;