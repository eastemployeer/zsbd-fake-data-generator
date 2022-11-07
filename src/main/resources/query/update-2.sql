alter system flush buffer_cache;
alter system flush shared_pool;
INSERT INTO Query_execution_time(query_name, start_time_ms) VALUES ('UPDATE 2', dbms_utility.get_time());

-- zmiana cen dań zawierających składniki z wybranej kategorii
UPDATE DISH
SET PRICE = ROUND(PRICE * 1.01, 2)
WHERE DISH_ID IN (
    SELECT DISTINCT DISH_ID
    FROM DISH
             NATURAL JOIN INGREDIENT
             JOIN PRODUCT on INGREDIENT.PRODUCT_ID = PRODUCT.PRODUCT_ID
    WHERE PRODUCT.CATEGORY IN ('DAIRY', 'CEREALS', 'SUGARS')
);

UPDATE Query_execution_time SET end_time_ms = dbms_utility.get_time()
WHERE query_name = 'UPDATE 2' AND end_time_ms IS NULL;