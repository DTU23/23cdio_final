/**
Foreman
 */
CREATE OR REPLACE VIEW produce_batch_list AS
  SELECT producebatch.rb_id, produce.produce_name, produce.supplier, producebatch.amount
  FROM producebatch INNER JOIN produce WHERE produce.produce_id = producebatch.produce_id
  ORDER BY produce_name;

CREATE OR REPLACE VIEW total_amount_by_produce_name AS
  SELECT produce.produce_name, SUM(producebatch.amount) AS total
  FROM producebatch NATURAL JOIN produce
  GROUP BY produce.produce_name;

CREATE OR REPLACE VIEW amount_used_by_produce_name AS
  SELECT produce.produce_name, SUM(productbatchcomponent.netto) AS used
  FROM productbatchcomponent NATURAL JOIN producebatch NATURAL JOIN produce
  GROUP BY produce.produce_name;

/**
Q3 - Shows an overview of how much of each produce type is in stock. (Foreman)
 */
CREATE OR REPLACE VIEW produce_in_stock AS
  SELECT total_amount_by_produce_name.produce_name,
    total_amount_by_produce_name.total - amount_used_by_produce_name.used AS current_stock
  FROM total_amount_by_produce_name JOIN amount_used_by_produce_name
  USING (produce_name);