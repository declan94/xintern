#!/bin/bash

sql1=`cat ew-init.sql`
sql2=`cat eyewater.sql`
echo "$sql1\n$sql2" | mysql -uroot -p
