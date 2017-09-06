#!/bin/bash

if [[ -d xi-scripts ]]; then
    cd xi-scripts
fi

cat xintern.sql | mysql -uroot -p