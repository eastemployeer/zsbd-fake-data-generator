load data 
infile 'screenings.csv' "str '\r\n'"
append
into table SCREENING
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( ID,
             ROOM_NAME CHAR(255),
             MOVIE_VERSION_ID,
             DATETIME TIMESTAMP "YYYY-MM-DD HH24:MI:SS.FF",
             IS_PREMIERE,
             IS_DISCOUNTABLE,
             ADS_LENGTH,
             PRICE "translate(:PRICE, ',', '.' )"
           )
