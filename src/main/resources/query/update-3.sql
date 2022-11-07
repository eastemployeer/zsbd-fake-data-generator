alter system flush buffer_cache;
alter system flush shared_pool;
INSERT INTO Query_execution_time(query_name, start_time_ms)
VALUES ('UPDATE 3', dbms_utility.get_time());

--Zmiana cen dań w zależności od ich popularności
DECLARE
    price_percent            number := 0.01;
    is_vegan                 number := 1;
    is_vegetarian            number := 1;
    percent_dishes_to_update number := 0.01;
BEGIN
    UPDATE DISH
    SET PRICE = ROUND(PRICE * (1 + price_percent), 2)
    WHERE DISH_ID IN (
        SELECT DISH_ID
        FROM (
                 SELECT RANK() OVER (ORDER BY SUM(AMOUNT)) popularity, D.DISH_ID, PRICE
                 FROM DISH D
                          JOIN ORDERED_DISH OD on D.DISH_ID = OD.DISH_ID
                 WHERE VEGAN = is_vegan
                   AND VEGETARIAN = is_vegetarian
                   AND (
                           SELECT DELIVERY_TIME
                           FROM RESTAURANT_ORDER RO
                           WHERE RO.RESTAURANT_ORDER_ID = OD.RESTAURANT_ORDER_ID) IS NOT NULL
                 GROUP BY D.DISH_ID, PRICE)
        WHERE (
                  SELECT dish_no * percent_dishes_to_update
                  FROM (SELECT COUNT(*) as dish_no FROM DISH)
              ) >= popularity);

    UPDATE DISH
    SET PRICE = ROUND(PRICE * (1 + price_percent), 2)
    WHERE DISH_ID IN (
        SELECT DISH_ID
        FROM (
                 SELECT RANK() OVER (ORDER BY SUM(AMOUNT) DESC) popularity, D.DISH_ID, PRICE
                 FROM DISH D
                          JOIN ORDERED_DISH OD on D.DISH_ID = OD.DISH_ID
                 WHERE VEGAN = is_vegan
                   AND VEGETARIAN = is_vegetarian
                   AND (
                           SELECT DELIVERY_TIME
                           FROM RESTAURANT_ORDER RO
                           WHERE RO.RESTAURANT_ORDER_ID = OD.RESTAURANT_ORDER_ID) IS NOT NULL
                 GROUP BY D.DISH_ID, PRICE)
        WHERE (
                  SELECT dish_no * percent_dishes_to_update
                  FROM (SELECT COUNT(*) as dish_no FROM DISH)
              ) >= popularity);
END;

UPDATE Query_execution_time
SET end_time_ms = dbms_utility.get_time()
WHERE query_name = 'UPDATE 3'
  AND end_time_ms IS NULL;
