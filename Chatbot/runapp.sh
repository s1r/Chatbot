#!/bin/sh

CLASSPATH=./lib/derby.jar:./lib/twitter4j-core-4.0.2.jar:./lib/jargp.jar:./lib/lucene-gosen-4.5.1-ipadic.jar:.

cp -p twitter4j.properties twitter4j.properties.b
cp -p twitter4j.properties.bk twitter4j.properties

java -classpath $CLASSPATH Main

mv twitter4j.properties.b twitter4j.properties