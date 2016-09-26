# JWebRobot

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/website.automate/jwebrobot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/website.automate/jwebrobot) [![Build Status](https://travis-ci.org/automate-website/jwebrobot.svg?branch=master)](https://travis-ci.org/automate-website/jwebrobot) [![codecov.io](https://codecov.io/github/automate-website/jwebrobot/coverage.svg?branch=master)](https://codecov.io/github/automate-website/jwebrobot?branch=master) [![Gitter](https://badges.gitter.im/automate-website/jwebrobot.svg)](https://gitter.im/automate-website/jwebrobot?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

Latest release can be downloaded directly from [here].

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

| Name  | Type | Description | Default | Example |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| *scenarioPath*  | optional  | Scenario path may be a directory or a single scenario file. | `./` |`../path/to/my/scenario` |
| *scenarioPaths*  | optional | Scenario paths may be a directory, a single scenario file or a set of both. |  |`../path/to/my/scenario` `../path/to/my/another/scenario` |
| *scenarioPattern*  | optional | If set, only non fragment scenarios with a name matching the pattern are executed. | `-` | `'^desired-scenario$'` |
| context  | optional | Context is a multi value argument that populates the context utilized during expression evaluation. | `-` |`baseUrl=http://www.wikipedia.com language=en` |
| *timeout* | optional | Timeout waiting for conditions to be fulfilled in seconds. Globally overrides timeout settings defined in the scenarios. | `-` | `5` |
| *screenshotPath*  | optional | Path to the directory where created screenshots must be saved. | `./` | `./` |
| *screenshotType*  | optional | Defines the way screenshots must be taken - fullscreen vs. viewport. | `VIEW_PORT` | `FULLSCREEN` |
| *screenshotFormat*  | optional | Defines the screenshot format. | `png` | `gif` |
| *takeScreenshots*  | optional | Defines when to take screenshots: NEVER, ON_FAILURE, ON_EVERY_STEP | `ON_FAILURE` | `./` |
| *browser*  | optional | A browser can be selected by passing this option when running JWebRobot. Please consider that some browsers require additional configuration parameters. WAML scenarios are executed with Mozilla Firefox per default. Firefox must be installed on the same machine. E.g.: Chrome does not provide embedded webdriver so that it has to be [downloaded manually](webdriver-chrome). The path to the downloaded executable has to be forwarded via the system property `webdriver.chrome.driver`. Of course, Chrome must be present on the same machine. | `firefox` | `chrome` |
| *reportPath*  | optional  | Path to which the report is written to. | `./report.yaml` | `./myreport.yaml` |

## Expressions
Expression are evaluated by the awesome templating engine [freemarker]. The expression syntax and result may be tested using online [template-tester].

| Expression | Context | Result | Description |
| ------------- | ------------- | ------------- | ------------- |
| ${foo} | foo="bar" | bar | Renders the context parameter value |
| ${(foo=="bar")?c} | foo="bar" | true | Tests context parameter foo for having the value "bar" and renders the boolean value. |

### Reserved Namespace

The lodash (`_`) name space is reserved for utility functions.

#### Mock

Mock utility provides an easy way to create test data required during test execution.

Find some examples below:

| Expression | Description |
| ------------- | ------------- |
| ${ _.mock.person().fullName() } | Creates a sane person full name |
| ${ _.mock.person().email() } | Creates a valid email address |
| ${ _.mock.person().telephoneNumber() } | Creates a valid phone number |
| ${ _.mock.company().name() } | Creates a sane company name |

Behind the scenes a powerful test data framework [jfairy] is doing the job. More usage examples may be found there.

### Store Criteria & Element Reference
While using steps containing filter criteria (e.g. `ensure`, `click`, `enter`, `select`, `move`) the reference to the filtered element may be stored using the `store` criteria, e.g.:

    ensure:
        selector: input[type=text]
        store: userEmailInput
    store:
        userEmailInputEnabled: ${userEmailInput.isEnabled()}

Note that the element reference currently exports the underlying [selenium webelement api]. The direct access is an experimental feature and may be subject to change in the future releases.

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
[here]: http://repo1.maven.org/maven2/website/automate/jwebrobot/1.4.0/jwebrobot-1.4.0.jar
[freemarker]: http://freemarker.org
[template-tester]: http://freemarker-online.kenshoo.com/
[jfairy]: https://github.com/Codearte/jfairy
[selenium webelement api]: https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/WebElement.html