#!/usr/bin/env bash

mvn -Pdirect-runner compile exec:java \
-Dexec.mainClass=io.blockchainetl.pubsub_to_firestore.PubsubToFirestorePipeline \
-Dexec.args="\
--pubsubSubscription=projects/crypto-etl-ethereum-dev/subscriptions/test0.sub \
--firestoreCollection=transactions \
--tempLocation=gs://crypto-etl-ethereum-dev-dataflow-temp/dataflow"

