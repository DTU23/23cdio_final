/* must be dropped in this order to avoid constraint violations */
DROP TABLE IF EXISTS productbatchcomponent;
DROP TABLE IF EXISTS productbatch;
DROP TABLE IF EXISTS operator;
DROP TABLE IF EXISTS recipecomponent;
DROP TABLE IF EXISTS recipe;
DROP TABLE IF EXISTS producebatch;
DROP TABLE IF EXISTS produce;

CREATE TABLE operator(
   opr_id INT PRIMARY KEY,
   opr_name TEXT,
   ini TEXT,
   cpr TEXT,
   password TEXT,
   admin BOOLEAN DEFAULT FALSE,
   role ENUM('Operator', 'Foreman', 'Pharmacist', 'None') DEFAULT 'None'
) ENGINE=innoDB;
 
CREATE TABLE produce(produce_id INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY, produce_name TEXT, supplier TEXT) ENGINE=innoDB;

CREATE TABLE producebatch(rb_id INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY, produce_id INT, amount REAL,
   FOREIGN KEY (produce_id) REFERENCES produce(produce_id)) ENGINE=innoDB;

CREATE TABLE recipe(recipe_id INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY, recipe_name TEXT) ENGINE=innoDB;

CREATE TABLE recipecomponent(recipe_id INT, produce_id INT, nom_netto REAL, tolerance REAL,
   PRIMARY KEY (recipe_id, produce_id),
   FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id),
   FOREIGN KEY (produce_id) REFERENCES produce(produce_id)) ENGINE=innoDB;

CREATE TABLE productbatch(pb_id INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY, status INT, recipe_id INT,
   FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id)) ENGINE=innoDB;

CREATE TABLE productbatchcomponent(pb_id INT, rb_id INT, tara REAL, netto REAL, opr_id INT,
   PRIMARY KEY (pb_id, rb_id),
   FOREIGN KEY (pb_id) REFERENCES productbatch(pb_id),
   FOREIGN KEY (rb_id) REFERENCES producebatch(rb_id),
   FOREIGN KEY (opr_id) REFERENCES operator(opr_id)) ENGINE=innoDB;

CALL reset_data();