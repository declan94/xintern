#!/bin/bash

if [[ -d xi-server ]]; then
    cd xi-server
fi;

mvn jetty:run