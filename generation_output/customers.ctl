load data 
infile 'customers.csv' "str '\r\n'"
append
into table CUSTOMER
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( ID,
             NAME CHAR(255),
             SURNAME CHAR(255),
             EMAIL CHAR(255),
             PASSWORD CHAR(255),
             GENDER CHAR(255),
             BIRTH_DATE DATE "RRRR-MM-DD"
           )
