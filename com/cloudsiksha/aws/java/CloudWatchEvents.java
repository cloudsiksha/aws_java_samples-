package com.cloudsiksha.aws.java;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequestEntry;
import com.amazonaws.services.cloudwatchevents.model.PutEventsResult;
import com.amazonaws.services.cloudwatchevents.model.PutRuleRequest;
import com.amazonaws.services.cloudwatchevents.model.PutRuleResult;
import com.amazonaws.services.cloudwatchevents.model.RuleState;
import com.amazonaws.services.cloudwatchevents.model.PutTargetsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutTargetsResult;
import com.amazonaws.services.cloudwatchevents.model.Target;

public class CloudWatchEvents {
	public static void putCloudWatchEvent(String resource_arn){
		final AmazonCloudWatchEvents cwe =
	            AmazonCloudWatchEventsClientBuilder.defaultClient();

	        final String EVENT_DETAILS =
	            "{ \"key1\": \"value1\", \"key2\": \"value2\" }";

	        PutEventsRequestEntry request_entry = new PutEventsRequestEntry()
	            .withDetail(EVENT_DETAILS)
	            .withDetailType("sampleSubmitted")
	            .withResources(resource_arn)
	            .withSource("aws-sdk-java-cloudwatch-example");

	        PutEventsRequest request = new PutEventsRequest()
	            .withEntries(request_entry);

	        PutEventsResult response = cwe.putEvents(request);

	        System.out.println("Successfully put CloudWatch event");
	}
	
	public static void putRule(String rule_name, String role_arn){
		final AmazonCloudWatchEvents cwe =
	            AmazonCloudWatchEventsClientBuilder.defaultClient();

	        PutRuleRequest request = new PutRuleRequest()
	            .withName(rule_name)
	            .withRoleArn(role_arn)
	            .withScheduleExpression("rate(5 minutes)")
	            .withState(RuleState.ENABLED);

	        PutRuleResult response = cwe.putRule(request);

	        System.out.printf(
	            "Successfully created CloudWatch events rule %s with arn %s",
	            rule_name, response.getRuleArn());
	}
	
	public static void putTargets(String rule_name, String function_arn, String target_id){
		final AmazonCloudWatchEvents cwe =
	            AmazonCloudWatchEventsClientBuilder.defaultClient();

	        Target target = new Target()
	            .withArn(function_arn)
	            .withId(target_id);

	        PutTargetsRequest request = new PutTargetsRequest()
	            .withTargets(target)
	            .withRule(rule_name);

	        PutTargetsResult response = cwe.putTargets(request);

	        System.out.printf(
	            "Successfully created CloudWatch events target for rule %s",
	            rule_name);
	}

}
