alter system flush buffer_cache;
alter system flush shared_pool;
INSERT INTO Query_execution_time(query_name, start_time_ms)
VALUES ('DELETE', dbms_utility.get_time());

--Usunięcie restauracji, których średni miesięczny przychód na miejsce siedzące jest mniejszy niż zadana wartość przychodu i posiadają one określoną liczbę pracowników
DECLARE
    income               number := 1234;
    min_employees_number number := 1;
    max_employees_number number := 37;
BEGIN
    DELETE
    FROM RESTAURANT R
    WHERE (
              SELECT ROUND(total_income / R.SEATS_COUNT, 2)
              FROM (
                       SELECT SUM(D.PRICE * OD.AMOUNT) as total_income
                       FROM RESTAURANT_ORDER RO
                                JOIN ORDERED_DISH OD on RO.RESTAURANT_ORDER_ID = OD.RESTAURANT_ORDER_ID
                                JOIN DISH D on D.DISH_ID = OD.DISH_ID
                       WHERE RO.RESTAURANT_ID = R.RESTAURANT_ID)
          ) <= income
      AND (
        SELECT COUNT(EMPLOYEE_ID)
        FROM EMPLOYMENT E
        WHERE R.RESTAURANT_ID = E.RESTAURANT_ID
    ) BETWEEN min_employees_number AND max_employees_number;
END;

UPDATE Query_execution_time
SET end_time_ms = dbms_utility.get_time()
WHERE query_name = 'DELETE'
  AND end_time_ms IS NULL;
