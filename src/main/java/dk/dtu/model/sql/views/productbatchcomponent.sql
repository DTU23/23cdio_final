/**
Task 6 - create view to select product batch id, produce batch id and netto weighting. (Foreman)
*/
CREATE OR REPLACE VIEW product_batch_netto_list AS
    SELECT pb_id, rb_id, netto
    FROM productbatchcomponent;

/**
Task 9 - Creates a view to display various information across recipe, productbatch,
productbatchcomponent and producebatch + produce. (Pharmacist)
*/
CREATE OR REPLACE VIEW product_batch_component_overview AS
  SELECT pb_id, rb_id, recipe.recipe_id, recipe_name, productbatch.status, produce_name, netto, opr_id
  FROM recipe NATURAL JOIN productbatch NATURAL JOIN productbatchcomponent
    NATURAL JOIN producebatch NATURAL JOIN produce
  ORDER BY pb_id;