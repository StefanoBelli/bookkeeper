#!/bin/bash

SUBPROJECT=bookkeeper-common-allocator

mkdir -pv ac-reports

cp dataflow.xml ac-reports/ -v

cp $SUBPROJECT/target/pit-reports ac-reports -rv

cp $SUBPROJECT/target/site/jacoco ac-reports -rv

exit 0