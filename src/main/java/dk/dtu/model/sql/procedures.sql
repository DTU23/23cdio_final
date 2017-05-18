DROP PROCEDURE IF EXISTS create_operator;
DROP PROCEDURE IF EXISTS update_operator;
DROP PROCEDURE IF EXISTS create_produce_batch_from_produce_id;
DROP PROCEDURE IF EXISTS update_produce_batch_by_id;
DROP PROCEDURE IF EXISTS delete_produce_batch_by_id;
DROP PROCEDURE IF EXISTS create_produce;
DROP PROCEDURE IF EXISTS update_produce_by_id;
DROP PROCEDURE IF EXISTS create_product_batch_component;
DROP PROCEDURE IF EXISTS get_product_batch_component_supplier_details_by_pb_id;
DROP PROCEDURE IF EXISTS create_product_batch_from_recipe_id;
DROP PROCEDURE IF EXISTS update_product_batch_status;
DROP PROCEDURE IF EXISTS create_recipe;
DROP PROCEDURE IF EXISTS create_recipe_component;
# Tasks
DROP PROCEDURE IF EXISTS produce_with_at_least_number_occurences_in_producebatch;
DROP PROCEDURE IF EXISTS recipe_name_of_recipes_containing_one_of_two_ingredients;
DROP PROCEDURE IF EXISTS recipe_name_of_recipes_containing_two_ingredients;
DROP PROCEDURE IF EXISTS recipe_name_of_recipes_not_containing_ingredient;
DROP PROCEDURE IF EXISTS recipe_containing_most_of_ingredient;
DROP PROCEDURE IF EXISTS get_product_batch_with_largest_quantity;
DROP PROCEDURE IF EXISTS get_involved_operator;

# Q's
DROP PROCEDURE IF EXISTS number_of_product_batch_components_with_weight_greater_than;
DROP PROCEDURE IF EXISTS amount_of_produce_in_stock;
DROP PROCEDURE IF EXISTS ingredients_that_is_contained_in_number_of_recipes;
DROP PROCEDURE IF EXISTS reset_data;

/**
Operator
 */
DELIMITER //
CREATE PROCEDURE create_operator
(
  IN input_id INT,
  IN input_name TEXT,
  IN input_initials TEXT,
  IN input_cpr TEXT,
  IN input_password TEXT,
  IN input_admin BOOLEAN,
  IN input_role TEXT
)
BEGIN
  INSERT INTO operator(opr_id, opr_name, ini, cpr, password, admin, role)
  VALUES(input_id,input_name,input_initials,input_cpr,input_password,input_admin,input_role);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_operator
(
  IN input_id INT,
  IN input_name TEXT,
  IN input_initials TEXT,
  IN input_cpr TEXT,
  IN input_password TEXT,
  IN input_admin BOOLEAN,
  IN input_role TEXT
)
BEGIN
  UPDATE operator SET
    opr_name = input_name,
    ini = input_initials,
    cpr = input_cpr,
    password = input_password,
    admin = input_admin,
    role = input_role
  WHERE input_id = opr_id;
END //
DELIMITER ;

/**
Produce_batch
 */
DELIMITER //
CREATE PROCEDURE create_produce_batch_from_produce_id
  (
    IN input_produce_id INT,
    IN input_amount DOUBLE
  )
  BEGIN
  INSERT INTO producebatch(produce_id, amount)
  VALUES(input_produce_id, input_amount);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_produce_batch_by_id
(IN input_rb_id INT, IN input_amount DOUBLE)
BEGIN
  UPDATE producebatch SET
    amount = input_amount
  WHERE input_rb_id = rb_id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE delete_produce_batch_by_id
(IN input_rb_id INT)
BEGIN
  DELETE FROM producebatch
  WHERE producebatch.produce_id = input_rb_id;
END //
DELIMITER ;

/**
Q2 - Shows the amount of specified produce_name that is in stock.
 */
DELIMITER //
CREATE PROCEDURE amount_of_produce_in_stock(produce_name TEXT)
  BEGIN
    SELECT (
      SELECT sum(producebatch.amount)
      FROM produce NATURAL JOIN producebatch
      WHERE produce_name = produce.produce_name)
    AS amount_in_stock;
  END //
DELIMITER ;

/**
Produce
 */
DELIMITER //
CREATE PROCEDURE create_produce
(
  IN input_produce_name TEXT,
  IN input_supplier TEXT
)
BEGIN
  INSERT INTO produce(produce_name, supplier) VALUES(input_produce_name, input_supplier);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_produce_by_id
(IN input_produce_id INT, IN input_produce_name TEXT, IN input_supplier TEXT)
BEGIN
  UPDATE produce SET
    produce_name = input_produce_name,
    supplier = input_supplier
  WHERE input_produce_id = produce_id;
END //
DELIMITER ;

/**
Task 1 - Shows produce with at least 'x' amount of occurences in producebatches.
We assume that the supplier is without significance of the shown produces.
*/
DELIMITER //
CREATE PROCEDURE produce_with_at_least_number_occurences_in_producebatch(produce_count INT)
  BEGIN
    SELECT produce_name
    FROM produce NATURAL JOIN producebatch
    GROUP BY produce_name
    HAVING count(produce_name) >= produce_count;
  END //
DELIMITER ;

/**
Q4 - Returns a list with the names of ingredients that are contained in the specified
number or more recipes.
 */
DELIMITER //
CREATE PROCEDURE ingredients_that_is_contained_in_number_of_recipes(times_contained INT)
  BEGIN
    SELECT produce_name
    FROM ((
          SELECT produce_name, count(produce_name) AS produce_count
          FROM recipecomponent NATURAL JOIN produce
          GROUP BY produce_name) AS T)
	  WHERE produce_count >= times_contained;
  END //
DELIMITER ;

/**
Product_batch_component
 */
DELIMITER //
CREATE PROCEDURE create_product_batch_component
(
  IN input_pb_id INT,
  IN input_rb_id INT,
  IN input_tara DOUBLE,
  IN input_netto DOUBLE,
  IN input_opr_id INT
)
BEGIN
  INSERT INTO productbatchcomponent(pb_id,rb_id,tara,netto,opr_id)
  VALUES(input_pb_id,input_rb_id,input_tara,input_netto,input_opr_id);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE get_product_batch_component_supplier_details_by_pb_id
(IN input_pb_id INT)
BEGIN
  SELECT productbatchcomponent.rb_id, produce_name, supplier, netto, opr_id
  FROM productbatchcomponent NATURAL JOIN produce NATURAL JOIN producebatch
  WHERE productbatchcomponent.pb_id = input_pb_id;
END //
DELIMITER ;

/**
Product_batch
*/
DELIMITER //
CREATE PROCEDURE create_product_batch_from_recipe_id
(
  IN recipe_id_input INT
)
BEGIN
  INSERT INTO productbatch(status, recipe_id) VALUES(0, recipe_id_input);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE update_product_batch_status(IN input_pb_id INT, IN input_status INT)
  BEGIN
    UPDATE productbatch
    SET status = input_status
    WHERE productbatch.pb_id = input_pb_id;
  END //
DELIMITER ;

/**
Product_batch
Task 7 - Finds which product batch has the largest quantity of a certain produce (by produce name
 */
DELIMITER //
CREATE PROCEDURE get_product_batch_with_largest_quantity(IN input_produce_name TEXT)
  BEGIN
    SELECT pb_id, produce_name, netto
    FROM ((
      SELECT pb_id, produce_name, netto
      FROM product_batch_component_overview
      WHERE produce_name = input_produce_name) AS T)
    WHERE netto = (SELECT MAX(netto)
                   FROM (
                      SELECT pb_id, produce_name, netto
                      FROM product_batch_component_overview
                      WHERE produce_name = input_produce_name) AS T2);
  END //
DELIMITER ;

/**
Product_batch
Task 8 - Finds the operator(s) that has created a specific recipe
 */
DELIMITER //
CREATE PROCEDURE get_involved_operator(IN input_recipe_name TEXT)
  BEGIN
    SELECT DISTINCT(opr_name) FROM product_batch_component_overview NATURAL JOIN operator
    WHERE recipe_name = input_recipe_name;
  END //
DELIMITER ;

/**
Q1 - Counts the number og product batch components weighing more than specified weight.
 */
DELIMITER //
CREATE PROCEDURE number_of_product_batch_components_with_weight_greater_than(weight INT)
  BEGIN
      SELECT count(*) AS count
      FROM productbatchcomponent
      WHERE netto > weight;
  END //
DELIMITER ;

/**
Recipe
 */
DELIMITER //
CREATE PROCEDURE create_recipe
(
  IN input_recipe_name TEXT
)
BEGIN
  INSERT INTO recipe(recipe_name) VALUES(input_recipe_name);
END //
DELIMITER ;

/**
Recipe name of recipes containing champignon OR skinke
Task 3 - Part 1 of task 3. Creates a view of recipes containing the ingredients 'skinke' OR 'champignon'
*/
DELIMITER //
CREATE PROCEDURE recipe_name_of_recipes_containing_one_of_two_ingredients(first_ing TEXT, second_ing TEXT)
  BEGIN
    SELECT DISTINCT recipe_name
    FROM recipe NATURAL JOIN recipecomponent NATURAL JOIN produce
    WHERE produce_name = first_ing OR produce_name =second_ing;
  END //
DELIMITER ;

/**
Recipe name of recipes containing champignon AND skinke
Task 3 - Part 2 of task 3. Creates a view of recipes containing the ingredients 'skinke' AND 'champignon'
*/
DELIMITER //
CREATE PROCEDURE recipe_name_of_recipes_containing_two_ingredients(first_ing TEXT, second_ing TEXT)
  BEGIN
    SELECT t1.recipe_name
    FROM (((SELECT recipe_name
            FROM recipecomponent NATURAL JOIN produce NATURAL JOIN recipe
            WHERE produce_name = first_ing) AS t1)
            INNER JOIN
          ((SELECT recipe_name
            FROM recipecomponent NATURAL JOIN produce NATURAL JOIN recipe
            WHERE produce_name =second_ing) AS t2)
    ON t1.recipe_name = t2.recipe_name);
  END //
DELIMITER ;

/**
Recipe name of recipes not containing champignon
Task 4 - Creates a view of recipes not containing the ingredient 'champignon'
*/
DELIMITER //
CREATE PROCEDURE recipe_name_of_recipes_not_containing_ingredient(ingredient TEXT)
  BEGIN
    SELECT DISTINCT recipe_name
    FROM recipe NATURAL JOIN recipecomponent
    WHERE NOT recipe_id =(
      SELECT recipe_id
      FROM produce NATURAL JOIN recipecomponent
      WHERE produce_name = ingredient);
  END //
DELIMITER ;

/**
Task 5 - Shows the recipes, which contains the largest amount of any ingredient
 */
DELIMITER //
CREATE PROCEDURE recipe_containing_most_of_ingredient(ingredient TEXT)
  BEGIN
    SELECT recipe_name, produce_name, nom_netto
    FROM recipecomponent NATURAL JOIN recipe NATURAL JOIN produce
    WHERE produce_name = ingredient AND nom_netto = (
      SELECT MAX(nom_netto)
      FROM recipecomponent NATURAL JOIN produce NATURAL JOIN recipe
      WHERE produce_name = ingredient);
  END //
DELIMITER ;

/**
Recipe_component
 */
DELIMITER //
CREATE PROCEDURE create_recipe_component
(
  IN input_recipe_id INT,
  IN input_produce_id INT,
  IN input_netto DOUBLE,
  IN input_tolerance DOUBLE
)
BEGIN
  INSERT INTO recipecomponent(recipe_id, produce_id, nom_netto, tolerance)
  VALUES(input_recipe_id, input_produce_id, input_netto, input_tolerance);
END //
DELIMITER ;

/**
Reset Data
 */
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
      (1, 'Angelo A', 'AA', '070770-7007', 'lKje4fa', FALSE, 'Foreman'),
      (2, 'Antonella B', 'AB', '080880-8008', 'atoJ21v', FALSE, 'Pharmacist'),
      (3, 'Luigi C', 'LC', '090990-9009', 'jEfm5aQ', FALSE, 'Operator'),
      (4, 'Super Admin', 'SA', '000000-0000', 'admin', TRUE, 'Pharmacist');

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