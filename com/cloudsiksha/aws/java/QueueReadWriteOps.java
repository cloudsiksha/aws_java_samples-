package com.cloudsiksha.aws.java;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import java.util.Date;
import java.util.List;

public class QueueReadWriteOps {
	final static void sendMessagetoQueue(String queue_url){
		 AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		 SendMessageRequest send_msg_request = new SendMessageRequest()
	                .withQueueUrl(queue_url)
	                .withMessageBody("hello world")
	                .withDelaySeconds(5);
	        sqs.sendMessage(send_msg_request);
	}
	final static void sendBatchMessagetoQueue(String queue_url){
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
                .withQueueUrl(queue_url)
                .withEntries(
                        new SendMessageBatchRequestEntry(
                                "msg_1", "Hello from message 1"),
                        new SendMessageBatchRequestEntry(
                                "msg_2", "Hello from message 2")
                                .withDelaySeconds(10));
        sqs.sendMessageBatch(send_batch_request);
	}
	final static List<Message> receiveMessagefromQueue(String queueUrl){
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
		return messages;
	}
	final static void deleteMessagesfromQueue(String queueUrl, List<Message> messages){
		AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
		for (Message m : messages) {
            sqs.deleteMessage(queueUrl, m.getReceiptHandle());
	}
		
	}
}
