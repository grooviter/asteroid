package asteroid.local.collector

import groovy.transform.ToString
import groovy.transform.AnnotationCollector

@ToJson
@ToString
@AnnotationCollector
@interface ToEverything { }
