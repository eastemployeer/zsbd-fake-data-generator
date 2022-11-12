sqlplus admin/admin@localhost:1521/xepdb1 < drop_all_tables.sql
sqlplus admin/admin@localhost:1521/xepdb1 < create_schema.sql
sqlldr admin/admin@localhost:1521/xepdb1 control=customers.ctl
sqlldr admin/admin@localhost:1521/xepdb1 control=employees.ctl
sqlldr admin/admin@localhost:1521/xepdb1 control=movies.ctl
sqlldr admin/admin@localhost:1521/xepdb1 control=movie_versions.ctl
sqlldr admin/admin@localhost:1521/xepdb1 control=reviews.ctl
sqlldr admin/admin@localhost:1521/xepdb1 control=rooms.ctl
sqlldr admin/admin@localhost:1521/xepdb1 control=screenings.ctl
sqlldr admin/admin@localhost:1521/xepdb1 control=screening_employees.ctl
sqlldr admin/admin@localhost:1521/xepdb1 control=tickets.ctl
sqlplus admin/admin@localhost:1521/xepdb1 < check_counts.sql