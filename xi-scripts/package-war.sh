#!/bin/bash

# echo "[package web]"
# cd ../ew-web
# ./deploy.sh $@

# if [[ $? -ne 0 ]]; then
# 	exit $?
# fi

echo "[package war]"
cd ../xi-server
mvn clean package -Dmaven.test.skip=true