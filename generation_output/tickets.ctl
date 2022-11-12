load data 
infile 'tickets.csv' "str '\r\n'"
append
into table TICKET
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( ID,
             SCREENING_ID,
             CUSTOMER_ID,
             EMPLOYEE_ID,
             "ROW" CHAR(1),
             SEAT,
             DISCOUNT "translate(:DISCOUNT, ',', '.' )",
             PURCHASE_DATETIME TIMESTAMP "YYYY-MM-DD HH24:MI:SS.FF"
           )
