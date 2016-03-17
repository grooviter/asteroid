# Change log
All notable changes to this project will be documented in this file.

## [0.1.2] - 2016-03-17
### Breaking changes
- Global transformers use criterias to locate classes and expressions
- MethodCallExpressionTransformed removed in favor of ExpressionTransformer

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
