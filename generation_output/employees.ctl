load data 
infile 'employees.csv' "str '\r\n'"
append
into table EMPLOYEE
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( ID,
             NAME CHAR(255),
             SURNAME CHAR(255),
             EMAIL CHAR(255),
             PASSWORD CHAR(255),
             SALARY "translate(:SALARY, ',', '.' )",
             DATE_OF_EMPLOYMENT DATE "RRRR-MM-DD",
             SUPERVISOR_ID
           )
