package com.cloudsiksha.aws.java;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.SetQueueAttributesRequest;

public class DeadLetterQueue {
	public static void createDeadLetterQueue(String src_queue_name, String dl_queue_name){
		final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		
		 // Create source queue
        try {
            sqs.createQueue(src_queue_name);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }

        // Create dead-letter queue
        try {
            sqs.createQueue(dl_queue_name);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }

        // Get dead-letter queue ARN
        String dl_queue_url = sqs.getQueueUrl(dl_queue_name)
                                 .getQueueUrl();

        GetQueueAttributesResult queue_attrs = sqs.getQueueAttributes(
                new GetQueueAttributesRequest(dl_queue_url)
                    .withAttributeNames("QueueArn"));

        String dl_queue_arn = queue_attrs.getAttributes().get("QueueArn");

        // Set dead letter queue with redrive policy on source queue.
        String src_queue_url = sqs.getQueueUrl(src_queue_name)
                                  .getQueueUrl();

        SetQueueAttributesRequest request = new SetQueueAttributesRequest()
                .withQueueUrl(src_queue_url)
                .addAttributesEntry("RedrivePolicy",
                        "{\"maxReceiveCount\":\"5\", \"deadLetterTargetArn\":\""
                        + dl_queue_arn + "\"}");

        sqs.setQueueAttributes(request);
		
	}

}
