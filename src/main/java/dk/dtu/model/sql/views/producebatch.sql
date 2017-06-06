/**
Foreman
 */
CREATE OR REPLACE VIEW produce_batch_list AS
  SELECT producebatch.rb_id, produce.produce_name, produce.supplier, producebatch.amount
  FROM producebatch INNER JOIN produce WHERE produce.produce_id = producebatch.produce_id;

/**
Q3 - Shows an overview of how much of each produce type is in stock. (Foreman)
 */
CREATE OR REPLACE VIEW produce_overview_sum_T3 AS
  SELECT produce_name, total
  FROM produce_overview_sum_T2;

CREATE OR REPLACE VIEW produce_overview_sum_T2 AS
  SELECT produce.produce_name, SUM(producebatch.amount) AS total
  FROM producebatch NATURAL JOIN produce
  GROUP BY produce.produce_name;

CREATE OR REPLACE VIEW produce_overview_sum_T AS
  SELECT produce.produce_name, SUM(productbatchcomponent.netto) AS used
  FROM productbatchcomponent NATURAL JOIN producebatch NATURAL JOIN produce
  GROUP BY produce.produce_name;


CREATE OR REPLACE VIEW produce_overview_sum AS
  SELECT produce_overview_sum_T.produce_name, produce_overview_sum_T3.total - produce_overview_sum_T.used AS CurrentStock
  FROM produce_overview_sum_T3 JOIN produce_overview_sum_T
  USING (produce_name);