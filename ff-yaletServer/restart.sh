#!/bin/bash

cd build
./stop.sh
cd ..
ant
cd build
./start.sh
cd ..
