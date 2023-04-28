#!/bin/sh

export OTEL_METRICS_EXPORTER=otlp

exec java -javaagent:./otel-javaagent.jar $1 -jar app.jar