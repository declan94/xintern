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

echo "[package war]"
cd ../xi-server
sed -i '' "s/localhost/yegames.cn/g" ./src/main/webapp/api-doc/xi-api.yaml
mvn clean package -Dmaven.test.skip=true
sed -i '' "s/yegames.cn/localhost/g" ./src/main/webapp/api-doc/xi-api.yaml