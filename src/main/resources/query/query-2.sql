-- 2.	Ranking średniej wysokości napiwków w wybranym miesiącu i roku
alter system flush buffer_cache;
alter system flush shared_pool;
INSERT INTO Query_execution_time(query_name, start_time_ms) VALUES ('QUERY 2', dbms_utility.get_time());

SELECT ROUND(AVG(R_O.TIP), 2)         AS "AVERAGE TIP",
       MAX(R_O.TIP)                   AS "LARGEST TIP",
       COUNT(R_O.RESTAURANT_ORDER_ID) AS "ORDERS COUNT",
       ROUND(AVG(REV.RATING), 2)         "AVERAGE RATING",
       R.NAME,
       A.COUNTRY,
       A.REGION,
       A.CITY,
       A.STREET,
       A.BUILDING_NUMBER
FROM RESTAURANT R
         JOIN ADDRESS A on R.ADDRESS_ID = A.ADDRESS_ID
         LEFT JOIN RESTAURANT_ORDER R_O on R.RESTAURANT_ID = R_O.RESTAURANT_ID AND
                                           EXTRACT(YEAR from R_O.ORDER_TIME) = 2015 AND
                                           EXTRACT(MONTH from R_O.ORDER_TIME) = 6
         LEFT JOIN REVIEW REV on R.RESTAURANT_ID = REV.RESTAURANT_ID AND
                                 EXTRACT(YEAR from REV.REVIEW_DATE) = 2015 AND
                                 EXTRACT(MONTH from REV.REVIEW_DATE) = 6
WHERE R.DATE_OF_OPENING < TO_DATE('01-06-2015', 'DD-MM-YYYY')
GROUP BY R.NAME, A.COUNTRY, A.REGION, A.CITY, A.STREET, A.BUILDING_NUMBER
HAVING COUNT(R_O.RESTAURANT_ORDER_ID) > 30
ORDER BY AVG(TIP) DESC NULLS LAST;

UPDATE Query_execution_time SET end_time_ms = dbms_utility.get_time()
WHERE query_name = 'QUERY 2' AND end_time_ms IS NULL;