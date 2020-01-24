package io.blockchainetl.pubsub_to_firestore;

import org.apache.beam.runners.dataflow.options.DataflowWorkerHarnessOptions;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.SdkHarnessOptions;
import org.apache.beam.sdk.options.StreamingOptions;
import org.apache.beam.sdk.options.Validation;

public interface PubsubToFirestorePipelineOptions extends PipelineOptions, StreamingOptions, SdkHarnessOptions,
    DataflowWorkerHarnessOptions {

    @Description("Input PubSub subscription")
    @Validation.Required
    String getPubsubSubscription();

    void setPubsubSubscription(String value);
    
    @Description("Output Firestore collection")
    @Validation.Required
    String getFirestoreCollection();

    void setFirestoreCollection(String value);
}
