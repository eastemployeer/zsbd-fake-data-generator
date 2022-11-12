load data 
infile 'rooms.csv' "str '\r\n'"
append
into table ROOM
fields terminated by ','
OPTIONALLY ENCLOSED BY '"' AND '"'
trailing nullcols
           ( NAME CHAR(255),
             SEATS_IN_ROW,
             "ROWS",
             SPONSOR CHAR(255),
             IS_3D,
             IS_VIP
           )
