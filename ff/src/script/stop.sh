#!/bin/sh

APPLICATION=ff-server

kill -9 `cat $APPLICATION.pid`
rm $APPLICATION.pid
echo $APPLICATION was stopped