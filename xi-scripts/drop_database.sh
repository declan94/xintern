#!/bin/bash

if [[ -d xi-scripts ]]; then
    cd xi-scripts
fi

echo "DROP USER 'xintern'@'localhost'; DROP DATABASE xintern;" | mysql -uroot -p
