#!/bin/bash

./package-war.sh

if [[ $? -ne 0 ]]; then
	exit $?
fi

rm -rf ../dist

mkdir ../dist
mkdir ../dist/scripts

cp ./* ../dist/scripts/
cp ../xi-server/target/xi-server-0.1.0.war ../dist/