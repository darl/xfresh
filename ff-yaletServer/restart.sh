#!/bin/bash

cd build
sh ./stop.sh
cd ..
ant
cd build
sh ./start.sh
cd ..
