#!/usr/bin/env bash

printf '\n\nBuilding the Java Lambda Function!\n\n'
mvn clean verify shade:shade
if [ $? -ne 0 ]; then
  printf '\n\nJava application build failed! No new Lambda Function will be deployed!!!i\n'
  exit -1
fi

printf '\n\nStarting the Terraforming!\n\n'
terraform plan -out=plan.out
terraform apply plan.out
