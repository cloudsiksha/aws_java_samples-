package com.cloudsiksha.aws.java;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import java.util.Date;
public class SQSOperations {
	 
	public static String createQueue(String QueueName){
		 AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

	        // Creating a Queue
	        CreateQueueRequest create_request = new CreateQueueRequest(QueueName)
	                .addAttributesEntry("DelaySeconds", "60")
	                .addAttributesEntry("MessageRetentionPeriod", "86400");

	        try {
	            sqs.createQueue(create_request);
	        } catch (AmazonSQSException e) {
	            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
	                throw e;
	            }
	        }
	        String queue_url = sqs.getQueueUrl(QueueName).getQueueUrl();
	        return queue_url;
	}
	public static void deleteQueue(String queue_url){
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		sqs.deleteQueue(queue_url);
	}
	public static void listQueue(String queue_url){
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		ListQueuesResult lq_result = sqs.listQueues();
        System.out.println("Your SQS Queue URLs:");
        for (String url : lq_result.getQueueUrls()) {
            System.out.println(url);
        }
	}

}
