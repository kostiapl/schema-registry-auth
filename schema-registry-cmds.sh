#!/bin/sh

# Get schemas without authentication
curl -s -X GET http://localhost:8081/subjects/


# Get schemas with authentication
curl -u ckp_tester:test_secret -s -X GET http://localhost:8081/subjects/ 

#Add schema to Schema-registry

jq '. | {schema: tojson}' ./test-avro.avsc  | \
    curl -X POST http://localhost:8081/subjects/test-avro/versions/ \
         -H "Content-Type:application/json" \
         -d @-
#List  schema in Schema-registry
#curl -X GET  http://localhost:8081/subjects/test-avro/versions/1 

#List all schema 

#curl -X GET  http://localhost:8081

# references
# https://ranjitnagi.medium.com/commonly-used-confluent-schema-registry-apis-methods-cf94ce9a68f7