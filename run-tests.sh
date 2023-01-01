#!/bin/bash
for s in $(echo $values | jq -r "to_entries|map(\"\(.key)=\(.value|tostring)\")|.[]" config.json); do
    export $s
done
(docker exec cscb869 gradle test --stacktrace)