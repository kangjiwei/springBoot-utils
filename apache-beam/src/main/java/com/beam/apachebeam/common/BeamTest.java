package com.beam.apachebeam.common;

public class BeamTest {

    public static void main(String[] args) {

        String inputsDir = "data/*";
        String outputsPrefix = "outputs/part";

//        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).create();
//        Pipeline pipeline = Pipeline.create(options);
//        pipeline
//                .apply("Read lines", TextIO.read().from(inputsDir))
//                .apply("Find words", FlatMapElements.into(TypeDescriptors.strings())
//                        .via((String line) -> Arrays.asList(line.split("[^\\p{L}]+"))))
//                .apply("Filter empty words", Filter.by((String word) -> !word.isEmpty()))
//                .apply("Count words", Count.perElement())
//                .apply("Write results", MapElements.into(TypeDescriptors.strings())
//                        .via((KV<String, Long> wordCount) ->
//                                wordCount.getKey() + ": " + wordCount.getValue()))
//                .apply(TextIO.write().to(outputsPrefix));
//        pipeline.run();
    }

}