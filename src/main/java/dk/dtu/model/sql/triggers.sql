/**
Trigger will ensure product batch statuses are properly set when weightings are added to the database dependent on how many weightings has been made on a productbatch compared to how many the recipe says it should have.
 */
DROP TRIGGER IF EXISTS after_component_insert;
DELIMITER //
CREATE TRIGGER after_component_insert
  AFTER INSERT
    ON productbatchcomponent FOR EACH ROW
BEGIN

  SET @recipe_component_count = (SELECT count(produce_id) FROM recipecomponent NATURAL JOIN recipe NATURAL JOIN productbatch WHERE pb_id = new.pb_id);
  SET @product_batch_component_count = (SELECT count(pb_id) FROM productbatch NATURAL JOIN productbatchcomponent WHERE pb_id = new.pb_id);

  IF(@product_batch_component_count = 0) THEN
    UPDATE productbatch SET status = 0 WHERE productbatch.pb_id = new.pb_id;
  ELSEIF(@recipe_component_count > @product_batch_component_count) THEN
    UPDATE productbatch SET status = 1 WHERE productbatch.pb_id = new.pb_id;
  ELSEIF(@recipe_component_count = @product_batch_component_count) THEN
    UPDATE productbatch SET status = 2 WHERE productbatch.pb_id = new.pb_id;
  END IF;

END //
DELIMITER ;