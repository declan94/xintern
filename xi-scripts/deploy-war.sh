#!/bin/bash

./package-war.sh $@

scp ../xi-server/target/xi-server-0.1.0.war root@yegames.cn:/opt/web/mybase/webapps/ROOT.war

ssh root@yegames.cn "chown jetty /opt/web/mybase/webapps/ROOT.war; service jetty restart;"