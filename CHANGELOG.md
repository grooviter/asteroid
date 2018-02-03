# Change log
All notable changes to this project will be documented in this file.

## [0.2.8] - [2018-02-03]

### Fixed

- Fix getting label desc property in `A.UTIL.METHODX.extractLabelFrom`
- Fix some PMD warnings

### Added

- `A.EXPR.mapX()` y `A.EXPR.mapEntryX()`
- Getting expression from `ArgumentListExpression` by index

## [0.2.7] - [2018-01-31]
### Breaking changes

- Removed functions deprecated in version `0.2.4`

### Added
- `emptyS` to create empty statements
- `whileS` to create while loop statements

### Changed

- Upgrade to Groovy `2.4.13`
- Upgrade to Spock `1.1-groovy-2.4`

### Deprecated

- A.STMT.`doWhileStatement` -> A.STMT.`doWhileS`
- A.STMT.`emptyStatement` -> A.STMT.`emptyS`

### Fixed

- `RetryImpl` in `asteroid-test` examples

## [0.2.6] - [2017-08-03]
### Added
- Add do-while statement (Thanks to josejuanmontiel)

## [0.2.5] - [2017-04-08]

Adding all remaining targets for local transforms annotations.

### Added

- All targets available individually
- Now you can use ANNOTATED to address more than one target at a time

## [0.2.4] - [2017-03-12]

Criterias release

### Fixed

- Fix Repo badges
- Update gh-pages published documentation

### Added

- `asteroid.Criterias` now gathers all criterias. And can be used not
only in transformers but also as simple predicates to filter out lists
of nodes
- Criterias can also be accessed via `A.CRITERIA`
- `NodeUtils` javadoc
- `asteroid.statement` package javadoc
- Deprecation information added to java compilation
- Binaries are now available at jcenter()

### Deprecated

- All criteras inside transformer classes are now deprecated in favor
of `asteroid.Criterias`
- Miscellaneos methods `and`, `or` should be access via `asteroid.Criterias`

## [0.2.3] - 2017-02-12

Added some minor features, and improved safety in transformers, and
fixed a typo in docs.

### Added

- New "or", "and" combinations in `MiscellaneousUtils` to help
  creating complex criterias in Transformers or Transformations
- Documentation about transformer criterias
- New `TryCatchStatementBuilder` to make it easier to create try/catch
  statements
- Added type of node affected when creating a transformer in global
  transformations

### Fixed

- Fixed Typo in documentation thanks to @jagedn :)

### Upgraded

- Groovy version to 2.4.8

## [0.2.2] - 2016-05-18

Maintenance release

### Added

- Add new utility method to check if there is a given ClassNode fields
of a certain type

### Fixed

- Mismatching javadoc/implementation. Fixed following javadoc
  specification

## [0.2.1] - 2016-05-13

### Added

- Some information about how to stop compilation through
  transformation's `addError` method
- shield.io badges for travis/bintray/license

### Fixed

- NPE: SourceUnit was not passed properly to `AbstractLocalTransformation#doVisit`

### Breaking changes

- Changed behavior in local transformations. If compiler doesn't
provide the required type, number of nodes to the transformation, the
transformation will be skipped.  At the moment it would throw a
`GroovyBugError`

## [0.2.0] - 2016-05-01

This version has many breaking changes, that's why the minor version
changes (I'm not using semantic version properly yet). Changes are
focused on:

- Reducing imports
- Searching a common way of declaring a transformation
- Making the implementation comply with best practices using PMD

### Breaking changes
- All abstract classes have changed their names to AbstractXXX
- Transformer now is an interface
- Both Local And Global base transformations moved to `asteroid` pkg
- No longer there is an annotation to indicate the type of transformation: @GlobalTransformation
and @LocalTransformation are removed in favor of @Phase
- @Apply removed and the target type is indicated in the `applyTo` @Local annotation attribute
- Compilation phases are now located under @Phase annotation

### Added
- More documentation

### Fixed
- Javadoc
- Modifiers in class ConstructorNodeBuilder

### Future plans
- Code coverage
- More examples
- Get rid of some expression creation in favor of Groovy macros

## [0.1.7] - 2016-04-28
### Breaking changes
- SourceUnit is no longer passed as an argument in local transformations
- A.PHASE_LOCAL and A.PHASE_GLOBAL are both CompilePhase instances

### Added
- Jacoco added to asteroid-test

## [0.1.6] - 2016-04-13
### Breaking changes
- Global transformations implementation has changed and may break previous uses

### Fixes
- Adding default VariableScope to closure produced by A.EXPR.closureX()
- Fixing type in example: ChangeMethodsTransformations
- Possible NPE when retrieving value from annotation
- Javadoc typos

### Added
- Adding imports through A.CLASS.addImport
- New ClassNodeTransformer critera "byAnnotationName"
- PMD for core project
- Updated documentation

## [0.1.5] - 2016-04-XX
### Breaking changes
- Move from janus repo to grooviter organization
- Remove checkers in favor of spock-like block statements

### Added
- New check statements
- More documentation
- Compatibility test to work with AnnotationCollector

## [0.1.4] - 2016-03-31
### Breaking changes
- Move utility functions to different modules (A.UTIL.CLASS/ANNOTATION/...)

### Added
- New utility functions to add elements to a ClassNode (methods, properties)
- More documentation in node related classes

### Fixes
- Fix generic type in global transformation example

## [0.1.3] - 2016-03-18
### Added
- Safe instance method call to A.UTIL
- Javadoc now shows version at index

## [0.1.2] - 2016-03-17
### Breaking changes
- Global transformers use criterias to locate classes and expressions
- MethodCallExpressionTransformed removed in favor of ExpressionTransformer
- Added methods to Util to manipulate method call arguments

### Added
- Multimodule project
- `asteroid-core`: Asteroid api implementation
- `asteroid-docs`: Asteroid documentation
- `asteroid-samples`: Tests and examples
- Spock used for global transformation tests
- Some global transformation examples
- Checkers documentation
- Global transformation documentation
- `checkTrue` method in checker builder to accept AnnotatedNode instances

### Fixes
- public visibility of global transformation classes

## [0.1.1] - 2016-03-16
### Breaking changes
- LocalTransformation, LocalTransformationImpl, Apply moved to `asteroid.local` package

### Added
- New global transformation abstractions located at `asteroid.global`

## [0.1.0] - 2016-03-16
### Breaking changes
- A.UTIL.addMethodToClass -> A.UTIL.addMethod

### Added
- More utils (e.g add interfaces to a given class node)
- Static method call expressions
- Adding versioning to public api (javadoc)
- Added javadoc accessible from asciidoc
