# JWebRobot Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased]

## [1.4.0]
### Added
- added experimental support for store criteria to be able to persist the element reference of the filtered element in the memory and reuse it during the scenario execution.

## [1.3.0]
### Changed
- allowed setting multiple scenario paths as cmd line argument values (#50)

### Fixed
- made scenario path command line argument optional as stated
- fixed interpretation of the timeout step criteria value as seconds (#49)

## [1.2.0]
### Changed
- updated waml-report-io to 0.8.0 and added browser log filter option

## [1.1.0]
### Added
- added browser log gathering and serialization into the report

### Changed
- updated to waml-io 0.6.1 and waml-report-io 0.7.0

## [1.0.0]
### Fixed
- fixed delegation of context parameters through command line containing equality sign in the value

### Changed
- added default for scenario path command line argument
- prevented loading of existing report file from the scenario root directory
- updated to waml-io 0.5.1

### [0.11.4]
- repaired screenshot indexing

## [0.11.3]
### Fixed
- included include steps into the step count utilized for the screenshot naming

## [0.11.2]
### Fixed
- updated selenium webdriver to fix firefox browser communication issue
- repaired report generation for included scenarios
- screenshots for implicit steps are not generated any more

## [0.11.1]
### Fixed
- fixed xpath expression for text element filter

## [0.11.0]
### Added
- added opera browser support

## [0.10.0]
### Changed
- updated to waml-io 0.4.0 and waml-report-io 0.5.0

## [0.9.0]
### Added
- url criteria value without protocol defaults to http

### Fixed
- context parameter must be preferred over scenario stored parameters

## [0.8.1]
### Fixed
- fixed timeout propagation, global timeout overrides those defined in the scenarios only if set

## [0.8.0]
### Added
- added support for taking viewport vs. fullpage screenshots
- added support for screenshot output type selection

## [0.7.2]
### Fixed
- upgraded to selenium-java 2.53.0

## [0.7.1]
### Changed
- upgraded to waml-report-io 0.4.1
- upgraded to waml-io 0.3.1

## [0.7.0]
### Changed
- upgraded to waml-report-io 0.4.0
- upgraded to waml-io 0.3.0

## [0.6.0]
### Changed
- upgraded to waml-report-io 0.3.1

## [0.5.2]
### Changed
- Adjusted screenshot naming pattern to `<root scenario name>-<step number>-<step type>`

### Fixed
- Fixed takeScreenshots argument value recognition

## [0.5.1]
### Fixed
- Fixed jackson library dependency conflicts

### Changed
- Renamed fat jar to jwebrobot-<version>-full.jar

## [0.5.0]
### Fixed
- Fixed scenario pattern handling in case the give argument value is empty.

### Added
- Added report path command line argument to be able to control the path the report is written to.

## [0.4.0]
### Added
- Added scenarioPattern command line argument to provide a possibility to filter non fragment scenarios that must be executed.

## [0.3.0]
### Changed
- Updated to [waml-io] 0.2.0

### Fixed
- Fixed element filter chain issue resulted in selection of wrong element

## [0.2.0]
### Changed
- Switched to [waml-io] for (de)serialiazation of waml scenarios

## [0.1.0]
### Added
- Created initial implementation of [waml] draft-02


[0.1.0]: https://github.com/automate-website/jwebrobot/compare/a23fabaf1d3557278bdef476d665e5ffe84a799f...0.1.0
[0.2.0]: https://github.com/automate-website/jwebrobot/compare/0.1.0...0.2.0
[0.3.0]: https://github.com/automate-website/jwebrobot/compare/0.2.0...0.3.0
[0.4.0]: https://github.com/automate-website/jwebrobot/compare/0.3.0...0.4.0
[0.5.0]: https://github.com/automate-website/jwebrobot/compare/0.4.0...0.5.0
[0.5.1]: https://github.com/automate-website/jwebrobot/compare/0.5.0...0.5.1
[0.5.2]: https://github.com/automate-website/jwebrobot/compare/0.5.1...0.5.2
[0.6.0]: https://github.com/automate-website/jwebrobot/compare/0.5.2...0.6.0
[0.7.0]: https://github.com/automate-website/jwebrobot/compare/0.6.0...0.7.0
[0.7.1]: https://github.com/automate-website/jwebrobot/compare/0.7.0...0.7.1
[0.7.2]: https://github.com/automate-website/jwebrobot/compare/0.7.1...0.7.2
[0.8.0]: https://github.com/automate-website/jwebrobot/compare/0.7.2...0.8.0
[0.8.1]: https://github.com/automate-website/jwebrobot/compare/0.8.0...0.8.1
[0.9.0]: https://github.com/automate-website/jwebrobot/compare/0.8.1...0.9.0
[0.10.0]: https://github.com/automate-website/jwebrobot/compare/0.9.0...0.10.0
[0.11.0]: https://github.com/automate-website/jwebrobot/compare/0.10.0...0.11.0
[0.11.1]: https://github.com/automate-website/jwebrobot/compare/0.11.0...0.11.1
[0.11.2]: https://github.com/automate-website/jwebrobot/compare/0.11.1...0.11.2
[0.11.3]: https://github.com/automate-website/jwebrobot/compare/0.11.2...0.11.3
[0.11.4]: https://github.com/automate-website/jwebrobot/compare/0.11.3...0.11.4
[1.0.0]: https://github.com/automate-website/jwebrobot/compare/0.11.4...1.0.0
[1.1.0]: https://github.com/automate-website/jwebrobot/compare/1.0.0...1.1.0
[1.2.0]: https://github.com/automate-website/jwebrobot/compare/1.1.0...1.2.0
[1.3.0]: https://github.com/automate-website/jwebrobot/compare/1.2.0...1.3.0
[1.4.0]: https://github.com/automate-website/jwebrobot/compare/1.3.0...1.4.0
[Unreleased]: https://github.com/automate-website/jwebrobot/compare/1.4.0...master
[waml]: https://github.com/automate-website/waml
[waml-io]: https://github.com/automate-website/waml-io