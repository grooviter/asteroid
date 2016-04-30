# Change log
All notable changes to this project will be documented in this file.

## [0.2.0] - 2016-04-xx

This version has many breaking changes, that's why the minor version
changes. Changes are focused on:

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
