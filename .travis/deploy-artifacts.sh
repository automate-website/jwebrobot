#!/usr/bin/env bash

signArtifacts(){
    openssl aes-256-cbc -K $encrypted_69a424f7a752_key -iv $encrypted_69a424f7a752_iv -in .travis/codesigning.asc.enc -out .travis/codesigning.asc -d
    gpg --fast-import .travis/codesigning.asc
}

deployArtifacts(){
    if [ ! -z "$TRAVIS_TAG" ]; then
        mvn --settings .travis/settings.xml org.codehaus.mojo:versions-maven-plugin:2.1:set -DnewVersion=$TRAVIS_TAG 1>/dev/null 2>/dev/null
    fi
    mvn deploy -P sign-artifacts --settings .travis/settings.xml -DskipTests=true -B -U
}

isDeploy(){
    if ( [[ "$TRAVIS_BRANCH" = 'master' ]] || [[ ! -z "$TRAVIS_TAG" ]] ) && [[ "$TRAVIS_PULL_REQUEST" == 'false' ]]; then
        return 0
    else
        return 1
    fi
}

if isDeploy; then
    signArtifacts
    deployArtifacts
fi

