#!/bin/bash
sed 's/INSERT INTO/INTO/g' < insert_generated_data.sql > batch_insert_generated_data.sql
sed -i 's/);/)/g' batch_insert_generated_data.sql
sed -i '2~100 s/$/\nINSERT ALL/g' batch_insert_generated_data.sql
sed -i '103~101 s/$/\nSELECT * FROM dual;/g' batch_insert_generated_data.sql
