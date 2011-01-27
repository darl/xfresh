#!/bin/sh

APPLICATION=ff-yaletServer

kill -9 `cat $APPLICATION.pid`
rm $APPLICATION.pid
echo $APPLICATION was stopped