package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class MyPipelineApp {
        public static void main(final String[] args) {
                App app = new App();

                new MyPipelineStack(app, "PipelineStack", StackProps.builder()
                                .env(Environment.builder()
                                                .account("698566732460")
                                                .region("us-east-1")
                                                .build())
                                .build());

                app.synth();

        }
}
