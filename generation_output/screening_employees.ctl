load data 
infile 'screening_employees.csv' "str '\r\n'"
append
into table SCREENINGEMPLOYEE
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( SCREENING_ID,
             EMPLOYEE_ID,
             RESPONSIBILITY CHAR(255)
           )
