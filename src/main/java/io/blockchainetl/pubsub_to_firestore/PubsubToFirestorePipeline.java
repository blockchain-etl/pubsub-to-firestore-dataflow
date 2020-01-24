package io.blockchainetl.pubsub_to_firestore;

import io.blockchainetl.pubsub_to_firestore.fns.WriteToFirestoreFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PubsubToFirestorePipeline {

    private static final Logger LOG = LoggerFactory.getLogger(PubsubToFirestorePipeline.class);

    public static void main(String[] args) {
        PubsubToFirestorePipelineOptions options =
            PipelineOptionsFactory.fromArgs(args).withValidation().as(PubsubToFirestorePipelineOptions.class);

        runPipeline(options);
    }

    public static void runPipeline(
        PubsubToFirestorePipelineOptions options
    ) {
        Pipeline p = Pipeline.create(options);

        // Build pipeline

        PCollection<String> input = p.apply("ReadFromPubsub",
            PubsubIO.readStrings()
                .fromSubscription(options.getPubsubSubscription()));

        PCollection<String> writeResult = input
            .apply("WriteToFirestore", ParDo.of(new WriteToFirestoreFn(options.getFirestoreCollection())));

        // Run pipeline

        PipelineResult pipelineResult = p.run();
        LOG.info(pipelineResult.toString());
        pipelineResult.waitUntilFinish();
    }
}
