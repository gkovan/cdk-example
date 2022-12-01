package com.myorg;

import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StageProps;
import software.amazon.awscdk.pipelines.StageDeployment;
import software.amazon.awscdk.pipelines.ManualApprovalStep;


import java.util.Arrays;
import software.amazon.awscdk.pipelines.CodePipeline;
import software.amazon.awscdk.pipelines.CodePipelineSource;
import software.amazon.awscdk.pipelines.ConnectionSourceOptions;
import software.amazon.awscdk.pipelines.ShellStep;

public class MyPipelineStack extends Stack {
    public MyPipelineStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public MyPipelineStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        CodePipeline pipeline = CodePipeline.Builder.create(this, "pipeline")
        .pipelineName("MyPipeline")
        .synth(ShellStep.Builder.create("Synth")
           .input(CodePipelineSource.connection("gkovan/cdk-example", "main", ConnectionSourceOptions.builder().connectionArn("arn:aws:codestar-connections:us-east-1:698566732460:connection/58227ce1-f364-4649-aa6e-91ddf7c4e875").build()))
           .commands(Arrays.asList("npm install -g aws-cdk", "cdk synth"))
           .build())
        .build();

        StageDeployment testingStage = pipeline.addStage(new MyPipelineAppStage(this, "test", StageProps.builder()
            .env(Environment.builder()
                .account("698566732460")
                .region("us-east-1")
                .build())
            .build()));

        testingStage.addPost(new ManualApprovalStep("approval"));


    }
}
