alter system flush buffer_cache;
alter system flush shared_pool;
INSERT INTO Query_execution_time(query_name, start_time_ms) VALUES ('QUERY 3', dbms_utility.get_time());

SELECT DISTINCT R.NAME,
                P.NAME,
                SUM(OD.AMOUNT * COALESCE(I.AMOUNT, 0)) as "total amount product used",
                P.STORING_UNIT
FROM RESTAURANT R
         LEFT JOIN RESTAURANT_ORDER RO ON R.RESTAURANT_ID = RO.RESTAURANT_ID
         JOIN ORDERED_DISH OD on RO.RESTAURANT_ORDER_ID = OD.RESTAURANT_ORDER_ID
         LEFT JOIN DISH D on OD.DISH_ID = D.DISH_ID
         CROSS JOIN PRODUCT P
         LEFT JOIN INGREDIENT I on D.DISH_ID = I.DISH_ID and P.PRODUCT_ID = I.PRODUCT_ID
WHERE RO.ORDER_TIME >= TO_DATE('1996-06-12', 'YYYY-MM-DD')
  AND RO.ORDER_TIME <= TO_DATE('2016-06-11', 'YYYY-MM-DD')
  AND P.NAME
    IN (
        'violet-fuchsia-ivory Mustard Seed',
        'orchid-teal-green Blue Cheese',
        'blue-white-turquoise Cardamom',
        'ivory-silver-yellow Aniseed',
        'plum-orange-black Soy Milk',
        'sky blue-turquoise-gold Blue Swimmer Crab',
        'maroon-orchid-lime Wholewheat Flour',
        'teal-orchid-pink White Flour',
        'pink-yellow-lime Prunes',
        'red-lavender-olive Grapefruit',
        'green-grey-orange Onion',
        'silver-orange-lime Dark Chocolate',
        'teal-orchid-pink White Flour'
          )
GROUP BY R.NAME, P.NAME, P.STORING_UNIT
ORDER BY R.NAME, P.NAME;

UPDATE Query_execution_time SET end_time_ms = dbms_utility.get_time()
WHERE query_name = 'QUERY 3' AND end_time_ms IS NULL;