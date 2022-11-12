load data 
infile 'movie_versions.csv' "str '\r\n'"
append
into table MOVIEVERSION
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( ID,
             MOVIE_ID,
             LANGUAGE CHAR(255),
             HAS_SUBTITLES,
             IS_3D,
             IS_ATMOS
           )
