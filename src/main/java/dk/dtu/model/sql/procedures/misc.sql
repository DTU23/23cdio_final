DROP PROCEDURE IF EXISTS insert_users;
DROP PROCEDURE IF EXISTS insert_produce;
DROP PROCEDURE IF EXISTS insert_recipe;
DROP PROCEDURE IF EXISTS insert_producebatch;
DROP PROCEDURE IF EXISTS insert_productbatch;
DROP PROCEDURE IF EXISTS insert_productbatchcomponents;
DROP PROCEDURE IF EXISTS reset_data;

DELIMITER //
CREATE PROCEDURE insert_users()
  BEGIN
    INSERT INTO operator(opr_id, opr_name, ini, cpr, password, admin, role) VALUES
      (22, 'Mads Pedersen', 'MP', '0109162407', 'root', FALSE , 'Pharmacist'),
      (23, 'Christian Niemann', 'CN', '0109162407', 'root', FALSE, 'Foreman'),
      (24, 'Frederik Værnegaard', 'FV', '0109162407', '123', FALSE, 'Operator');
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE insert_produce()
  BEGIN
    INSERT INTO produce(produce_id, produce_name, supplier) VALUES
      (22, 'gorgonzola', 'We Love Cheese');
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE insert_recipe()
  BEGIN
    INSERT INTO recipe(recipe_id, recipe_name) VALUES
      (99, 'El Formaggio');

    INSERT INTO recipecomponent(recipe_id, produce_id, nom_netto, tolerance) VALUES
      (99, 1, 10.0, 0.1),
      (99, 2, 2.0, 0.1),
      (99, 5, 3.0, 0.1),
      (99, 22, 1.55, 0.1);
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE insert_producebatch()
  BEGIN
    INSERT INTO producebatch(rb_id, produce_id, amount) VALUES
      (23, 22, 2000);
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE insert_productbatch()
  BEGIN
    INSERT INTO productbatch(pb_id, recipe_id, status) VALUES
      (23, 99, 0);
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE insert_productbatchcomponents()
  BEGIN
    INSERT INTO productbatchcomponent(pb_id, rb_id, tara, netto, opr_id) VALUES
      (23, 1, 0.5, 10.05, 1),
      (23, 2, 0.5, 1.98, 1),
      (23, 5, 0.5, 3.01, 1);
  END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE reset_data()
  BEGIN
    DELETE FROM productbatchcomponent;
    DELETE FROM productbatch;
    DELETE FROM operator;
    DELETE FROM recipecomponent;
    DELETE FROM recipe;
    DELETE FROM producebatch;
    DELETE FROM produce;
    INSERT INTO operator(opr_id, opr_name, ini, cpr, password, admin, role) VALUES
      (1, 'Angelo A', 'AA', '0707707003', 'lKjE4fa', FALSE, 'Foreman'),
      (2, 'Antonella B', 'AB', '0808802236', 'aToJ21v', FALSE, 'Pharmacist'),
      (3, 'Luigi C', 'LC', '0909909007', 'jEfm5aQ', FALSE, 'Operator'),
      (4, 'Super User', 'SU', '0109162407', 'root', TRUE, 'Pharmacist'),
      (5, 'Admin', 'ADM', '0109162407', 'root', TRUE, 'None'),
      (6, 'Pharmacist', 'PHA', '0109162407', 'root', FALSE , 'Pharmacist'),
      (7, 'Foreman', 'FM', '0109162407', 'root', FALSE, 'Foreman'),
      (8, 'Operator', 'OPR', '0109162407', 'root', FALSE, 'Operator'),
      (9, 'None', 'NONE', '0109162407', '123', FALSE, 'None'),
      (21, 'Viktor Poulsen', 'VP', '0109162407', 'root', TRUE, 'None');

    INSERT INTO produce(produce_id, produce_name, supplier) VALUES
      (1, 'dej', 'Wawelka'),
      (2, 'tomat', 'Knoor'),
      (3, 'tomat', 'Veaubais'),
      (4, 'tomat', 'Franz'),
      (5, 'ost', 'Ost og Skinke A/S'),
      (6, 'skinke', 'Ost og Skinke A/S'),
      (7, 'champignon', 'Igloo Frostvarer');

    INSERT INTO producebatch(rb_id, produce_id, amount) VALUES
      (1, 1, 1000),
      (2, 2, 300),
      (3, 3, 300),
      (4, 5, 100),
      (5, 5, 100),
      (6, 6, 100),
      (7, 7, 100);

    INSERT INTO recipe(recipe_id, recipe_name) VALUES
      (1, 'margherita'),
      (2, 'prosciutto'),
      (3, 'capricciosa');


    INSERT INTO recipecomponent(recipe_id, produce_id, nom_netto, tolerance) VALUES
      (1, 1, 10.0, 0.1),
      (1, 2, 2.0, 0.1),
      (1, 5, 2.0, 0.1),

      (2, 1, 10.0, 0.1),
      (2, 3, 2.0, 0.1),
      (2, 5, 1.5, 0.1),
      (2, 6, 1.5, 0.1),

      (3, 1, 10.0, 0.1),
      (3, 4, 1.5, 0.1),
      (3, 5, 1.5, 0.1),
      (3, 6, 1.0, 0.1),
      (3, 7, 1.0, 0.1);

    INSERT INTO productbatch(pb_id, recipe_id, status) VALUES
      (1, 1, 2),
      (2, 1, 2),
      (3, 2, 2),
      (4, 3, 1),
      (5, 3, 0);

    INSERT INTO productbatchcomponent(pb_id, rb_id, tara, netto, opr_id) VALUES
      (1, 1, 0.5, 10.05, 1),
      (1, 2, 0.5, 2.03, 1),
      (1, 4, 0.5, 1.98, 1),

      (2, 1, 0.5, 10.01, 2),
      (2, 2, 0.5, 1.99, 2),
      (2, 5, 0.5, 1.47, 2),

      (3, 1, 0.5, 10.07, 1),
      (3, 3, 0.5, 2.06, 2),
      (3, 4, 0.5, 1.55, 1),
      (3, 6, 0.5, 1.53, 2),

      (4, 1, 0.5, 10.02, 3),
      (4, 5, 0.5, 1.57, 3),
      (4, 6, 0.5, 1.03, 3),
      (4, 7, 0.5, 0.99, 3);
  END //
DELIMITER ;