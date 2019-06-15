#!/bin/bash

##### Constants
declare -a Services=( "config-server" "eureka-server" "zuul-server" "zipkin-server" "authentication-service" "licensing-service" "organization-service")

##### Main
for i in "${Services[@]}"
do
    java -jar build/$i/libs/$i.jar &> /dev/null &
done