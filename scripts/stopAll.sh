#!/bin/bash

##### Constants
declare -a Services=( "config-server" "eureka-server" "zuul-server" "zipkin-server" "authentication-service" "licensing-service" "organization-service")

##### Main
for i in "${Services[@]}"
do
	echo "kill $i"
    kill $(ps | grep $i | grep -v "grep" |awk '{print $1}')
done