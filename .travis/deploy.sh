#!/usr/bin/env bash
if ( [ "$TRAVIS_BRANCH" = 'master' ] || [ ! -z "$TRAVIS_TAG" ] ) && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	if [ ! -z "$TRAVIS_TAG" ]; then
	    mvn --settings .travis/settings.xml org.codehaus.mojo:versions-maven-plugin:2.1:set -DnewVersion=$TRAVIS_TAG 1>/dev/null 2>/dev/null
	fi
    mvn deploy -P sign-artifacts --settings .travis/settings.xml -DskipTests=true -B -U
fi