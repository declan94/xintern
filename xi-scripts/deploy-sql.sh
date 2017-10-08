#!/bin/bash

if [[ -d xi-scripts ]]; then
    cd xi-scripts
fi

scp xintern.sql root@yegames.cn:/root/dist/xi-scripts/
scp fakedata.sql root@yegames.cn:/root/dist/xi-scripts/

ssh root@yegames.cn
# ssh root@yegames.cn "cd /root/dist/xi-scripts; ./drop_database.sh; ./init_database.sh"