CREATE OR REPLACE FUNCTION extract_quarter_from_date(d date) RETURN number AS
    month number(2);
BEGIN
    month := EXTRACT(MONTH FROM d);

    RETURN CASE
               WHEN month BETWEEN 1 AND 3 THEN 1
               WHEN month BETWEEN 4 AND 6 THEN 2
               WHEN month BETWEEN 7 AND 9 THEN 3
               WHEN month BETWEEN 10 AND 12 THEN 4
        END;
end;

alter system flush buffer_cache;
alter system flush shared_pool;
INSERT INTO Query_execution_time(query_name, start_time_ms) VALUES ('QUERY 1', dbms_utility.get_time());

-- kwartalne zestawienie przychodow restauracji ze wzgledu na lokalizacje
SELECT COUNTRY,
       REGION,
       CITY,
       extract_quarter_from_date(cast(ORDER_TIME as date)) as quarter,
       EXTRACT(YEAR FROM cast(ORDER_TIME as date))         as year,
       SUM(PRICE * OD.AMOUNT)                              as total_income
FROM DISH
         NATURAL JOIN ORDERED_DISH OD
         JOIN RESTAURANT_ORDER RO ON OD.RESTAURANT_ORDER_ID = RO.RESTAURANT_ORDER_ID
         JOIN RESTAURANT R on RO.RESTAURANT_ID = R.RESTAURANT_ID
         JOIN ADDRESS A on R.ADDRESS_ID = A.ADDRESS_ID
WHERE EXTRACT(YEAR FROM cast(ORDER_TIME as date)) BETWEEN 1999 AND 2019
  AND R.SEATS_COUNT >= 15
  AND R.SEATS_COUNT <= 100
group by extract_quarter_from_date(cast(ORDER_TIME as date)), EXTRACT(YEAR FROM cast(ORDER_TIME as date)), COUNTRY,
         REGION, CITY
order by COUNTRY, REGION, CITY, year, quarter;

UPDATE Query_execution_time SET end_time_ms = dbms_utility.get_time()
WHERE query_name = 'QUERY 1' AND end_time_ms IS NULL;