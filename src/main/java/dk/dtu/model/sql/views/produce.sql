/**
Pharmacist
 */
CREATE OR REPLACE VIEW produce_overview AS
  SELECT produce.produce_id, produce.produce_name, produce.supplier, producebatch.amount
  FROM produce NATURAL JOIN producebatch;