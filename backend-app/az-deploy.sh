#! /bin/sh

az webapp \
    deploy \
    --resource-group fed \
    --name fed-app \
    --src-path ./build/libs/fed-1.0.0.jar

