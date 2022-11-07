alter system flush buffer_cache;
alter system flush shared_pool;
INSERT INTO Query_execution_time(query_name, start_time_ms) VALUES ('UPDATE 1', dbms_utility.get_time());

-- podniesienie wysokosci pensji w zaleznosci od dlugosci zatrudnienia
-- (ostatnie zatrudnienie))
UPDATE EMPLOYMENT EMP
SET SALARY = ROUND(SALARY * (1 + 0 / 100), 2)
WHERE END_DATE IS NULL
  AND TRUNC(MONTHS_BETWEEN(SYSDATE, START_DATE)) >= 24
  AND TRUNC(MONTHS_BETWEEN(SYSDATE, START_DATE)) <= 240
  AND (
          SELECT ROUND(AVG(TIP), 2)
          FROM RESTAURANT_ORDER
          WHERE WAITER_ID = EMP.EMPLOYEE_ID
      ) >= 5;

UPDATE Query_execution_time SET end_time_ms = dbms_utility.get_time()
WHERE query_name = 'UPDATE 1' AND end_time_ms IS NULL;