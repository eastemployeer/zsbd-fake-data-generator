alter system flush buffer_cache;
alter system flush shared_pool;
INSERT INTO Query_execution_time(query_name, start_time_ms) VALUES ('INSERT', dbms_utility.get_time());
--Dodanie nowych dań i produktów
INSERT INTO PRODUCT(name, CALORIES_PER_100G, STORING_CONDITIONS, STORING_UNIT, CATEGORY) VALUES ()
INSERT INTO DISH(price, description, vegetarian, vegan, spiciness, name, category) VALUES ()
INSERT INTO INGREDIENT(product_id, dish_id, amount) VALUES ()


UPDATE Query_execution_time SET end_time_ms = dbms_utility.get_time()
WHERE query_name = 'INSERT' AND end_time_ms IS NULL;
