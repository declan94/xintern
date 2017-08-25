#!/bin/bash

./package-war.sh $@

scp ../ew-server/target/ew-server-0.1.0.war root@118.178.181.45:/opt/web/jetty-base/webapps/ROOT.war

ssh root@118.178.181.45 "chown jetty /opt/web/jetty-base/webapps/ROOT.war; service jetty restart;"