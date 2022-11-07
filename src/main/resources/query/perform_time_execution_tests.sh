#!/bin/bash
connection_string=zsbd/Passw0rd@//localhost:1521/orclpdb
query_sql_paths=(
  "query-2.sql"
  "query-2.sql"
  "query-2.sql"
)

for i in {1..5}
do
	for query_sql_path in "${query_sql_paths[@]}"; 
	do
    sqlplus -l -s $connection_string < $query_sql_path
	done
done