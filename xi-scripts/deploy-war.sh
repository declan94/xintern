#!/bin/bash

if [[ -d xi-scripts ]]; then
    cd xi-scripts
fi

# SERVER=yegames.cn
SERVER=x.xiangshixi.cc

./package-war.sh $@

scp ../xi-server/target/xi-server-0.1.0.war root@${SERVER}:/opt/web/mybase/webapps/ROOT.war

ssh root@${SERVER} "chown jetty /opt/web/mybase/webapps/ROOT.war; service jetty restart;"