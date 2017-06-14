/**
Admin
 */
CREATE OR REPLACE VIEW operator_list AS
  SELECT opr_id, ini, opr_name, cpr, admin, role
  FROM operator;