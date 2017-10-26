#!/bin/bash

# echo "[package web]"
# cd ../ew-web
# ./deploy.sh $@

# if [[ $? -ne 0 ]]; then
# 	exit $?
# fi

if [[ -d xi-scripts ]]; then
    cd xi-scripts
fi

# SERVER=yegames.cn
SERVER=x.xiangshixi.cc

echo "[package war]"
cd ../xi-server
sed -i '' "s/localhost/${SERVER}/g" ./src/main/webapp/api-doc/xi-api.yaml
mvn clean package -Dmaven.test.skip=true
sed -i '' "s/${SERVER}/localhost/g" ./src/main/webapp/api-doc/xi-api.yaml