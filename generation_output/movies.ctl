load data 
infile 'movies.csv' "str '\r\n'"
append
into table MOVIE
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( ID,
             TITLE CHAR(255),
             DIRECTOR CHAR(255),
             LENGTH,
             PREMIERE_DATE DATE "RRRR-MM-DD",
             GENRE CHAR(255)
           )
