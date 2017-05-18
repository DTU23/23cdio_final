CALL reset_data();
# 1
CALL produce_with_at_least_number_occurences_in_producebatch(2);
# 2
SELECT * FROM recipe_overview;
# 3
CALL recipe_name_of_recipes_containing_one_of_two_ingredients('champignon','skinke');
CALL recipe_name_of_recipes_containing_two_ingredients('champignon','skinke');
# 4
CALL recipe_name_of_recipes_not_containing_ingredient('champignon');
# 5
CALL recipe_containing_most_of_ingredient('tomat');
# 6
SELECT * FROM product_batch_netto_list;
# 7
CALL get_product_batch_with_largest_quantity('tomat');
# 8
CALL get_involved_operator('margherita');
# 9
SELECT * FROM product_batch_component_overview;
# Q1
CALL number_of_product_batch_components_with_weight_greater_than(10);
# Q2
CALL amount_of_produce_in_stock('tomat');
# Q3
SELECT * FROM produce_overview;
# Q4
CALL ingredients_that_is_contained_in_number_of_recipes(3);