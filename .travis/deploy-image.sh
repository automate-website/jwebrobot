#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" == "master" ]; then
    docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD";
    docker push automatewebsite/jwebrobot-chrome;
fi

if [ ! -z "$TRAVIS_TAG" ]; then
    docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD";
    docker push automatewebsite/jwebrobot-chrome:$TRAVIS_TAG;
fi
