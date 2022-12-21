#!/bin/bash
gradle build -x test
java -jar build/libs/*.jar