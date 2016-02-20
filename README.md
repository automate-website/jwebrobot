# JWebRobot

[![Join the chat at https://gitter.im/automate-website/jwebrobot](https://badges.gitter.im/automate-website/jwebrobot.svg)](https://gitter.im/automate-website/jwebrobot?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/website.automate/jwebrobot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/website.automate/jwebrobot) [![Build Status](https://travis-ci.org/automate-website/jwebrobot.svg?branch=master)](https://travis-ci.org/automate-website/jwebrobot) [![codecov.io](https://codecov.io/github/automate-website/jwebrobot/coverage.svg?branch=master)](https://codecov.io/github/automate-website/jwebrobot?branch=master)

## Short Description

**JWebRobot** is the reference implementation of [web automation markup language]. Currently the implementation is based on the [draft-02] schema.

## References
Refer to the [changelog] for recent notable changes and modifications.

Refer to the [waml-schema] for schema sources.

## Options
Options may be passed to the **JWebRobot** using single hyphen notation:

```
java -jar <path to jar> -<argument name> [<argument value> ...]
```

### Scenario Path
Scenario path may be a directory or a single scenario file. Scenario path is a *mandatory* argument.

```
java -jar <path to jar> -scenarioPath <scenario path>
```

### Context
Context is a multi value argument that populates the context utilized during expression evaluation. Context is an *optional* argument.

```
java -jar <path to jar> -context baseUrl=http://www.wikipedia.com language=en
```

### Browser

A browser can be selected by passing the ```-browser=<browser name>``` property when running JWebRobot. Please consider 
that some browsers require additional configuration parameters.

#### Firefox (Default)

WAML scenarios are executed with Mozilla Firefox per default. Firefox must be installed on the same machine.  

#### Chrome

Since Chrome does not provide embedded webdriver it has to be [downloaded manually](webdriver-chrome). When execute, the 
path to the downloaded executable has to be passed via the system property ```webdriver.chrome.driver```. Of course, 
Chrome must be present on the same machine.

```
java -jar <path to jar> -scenarioPath=<path to WAML> -browser=chrome -Dwebdriver.chrome.driver=<path to chrome driver>
```

## Source Build

An executable JAR can be created by executing the _package_ Maven goal:

```
mvn package
```

[webdriver-chrome]: http://chromedriver.storage.googleapis.com/index.html
[changelog]: CHANGELOG.md
[waml-schema]: http://waml-schema.org
[web automation markup language]: https://github.com/automate-website/waml
[draft-02]: http://waml-schema.org/draft-02/schema#