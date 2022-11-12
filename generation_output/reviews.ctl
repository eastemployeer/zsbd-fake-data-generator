load data 
infile 'reviews.csv' "str '\r\n'"
append
into table REVIEW
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( CUSTOMER_ID,
             MOVIE_ID,
             DESCRIPTION CHAR(1000),
             RATING,
             "DATE" DATE "RRRR-MM-DD",
             LIKES
           )
