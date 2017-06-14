/**
Foreman
*/
CREATE OR REPLACE VIEW product_batch_list AS
  SELECT productbatch.pb_id, productbatch.recipe_id, recipe.recipe_name, productbatch.status
  FROM productbatch NATURAL JOIN recipe;