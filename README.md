# WebRobot for Java

[![Build Status](https://travis-ci.org/automate-website/jwebrobot.svg?branch=master)](https://travis-ci.org/automate-website/jwebrobot)

## Building an Executable JAR

An executable JAR can be created by executing the _assembly_ Maven plugin:

```
mvn assembly:assembly
```

## Execution

A scenario can be executed from console by us passing _scenario_ parameter, e.g.:

```
java -jar <path to jar> --scenario <path to yaml>
```
