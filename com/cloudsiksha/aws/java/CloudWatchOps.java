package com.cloudsiksha.aws.java;

/* Ths class contains:
 * List Metrics
 * Put Metrics
 * Create Alarm
 * List Alarm
 * Delete Alarm
 * Enable Alarm Actions
 */

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsResult;
import com.amazonaws.services.cloudwatch.model.MetricAlarm;
import com.amazonaws.services.cloudwatch.model.DeleteAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DeleteAlarmsResult;
import com.amazonaws.services.cloudwatch.model.EnableAlarmActionsRequest;
import com.amazonaws.services.cloudwatch.model.EnableAlarmActionsResult;
import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmResult;
import com.amazonaws.services.cloudwatch.model.Statistic;

public class CloudWatchOps {
	public static void listMetrics(String name, String namespace){
		final AmazonCloudWatch cw =
	            AmazonCloudWatchClientBuilder.defaultClient();
		boolean done = false;

        while(!done) {
            ListMetricsRequest request = new ListMetricsRequest()
                .withMetricName(name)
                .withNamespace(namespace);

            ListMetricsResult response = cw.listMetrics(request);

            for(Metric metric : response.getMetrics()) {
                System.out.printf(
                    "Retrieved metric %s", metric.getMetricName());
            }

            request.setNextToken(response.getNextToken());

            if(response.getNextToken() == null) {
                done = true;
            }
        }
        
     }
	 
	 public static void putMetrics(double data_point){
		 final AmazonCloudWatch cw =
		            AmazonCloudWatchClientBuilder.defaultClient();

		        Dimension dimension = new Dimension()
		            .withName("UNIQUE_PAGES")
		            .withValue("URLS");

		        MetricDatum datum = new MetricDatum()
		            .withMetricName("PAGES_VISITED")
		            .withUnit(StandardUnit.None)
		            .withValue(data_point)
		            .withDimensions(dimension);

		        PutMetricDataRequest request = new PutMetricDataRequest()
		            .withNamespace("SITE/TRAFFIC")
		            .withMetricData(datum);

		        PutMetricDataResult response = cw.putMetricData(request);

		        System.out.printf("Successfully put data point %f", data_point);
     }
	 
	 public static void listMyAlarms(){
		 final AmazonCloudWatch cw =
		            AmazonCloudWatchClientBuilder.defaultClient();

		        boolean done = false;

		        while(!done) {
		            DescribeAlarmsRequest request = new DescribeAlarmsRequest();

		            DescribeAlarmsResult response = cw.describeAlarms(request);

		            for(MetricAlarm alarm : response.getMetricAlarms()) {
		                System.out.printf("Retrieved alarm %s", alarm.getAlarmName());
		            }

		            request.setNextToken(response.getNextToken());

		            if(response.getNextToken() == null) {
		                done = true;
		            }
		        }
	 }
	 
	 public static void deleteMyAlarms(String alarm_name){
		 
		 final AmazonCloudWatch cw =
		            AmazonCloudWatchClientBuilder.defaultClient();

		        DeleteAlarmsRequest request = new DeleteAlarmsRequest()
		            .withAlarmNames(alarm_name);

		        DeleteAlarmsResult response = cw.deleteAlarms(request);

		        System.out.printf("Successfully deleted alarm %s", alarm_name);
	 }
	 
	 public static void enableAlarmActions(String alarm){
		 final AmazonCloudWatch cw =
		            AmazonCloudWatchClientBuilder.defaultClient();

		        EnableAlarmActionsRequest request = new EnableAlarmActionsRequest()
		            .withAlarmNames(alarm);

		        EnableAlarmActionsResult response = cw.enableAlarmActions(request);

		        System.out.printf(
		            "Successfully enabled actions on alarm %s", alarm);
	 }
	 
	 public static void createMyAlarm(String alarmName, String instanceId){
		 final AmazonCloudWatch cw =
				    AmazonCloudWatchClientBuilder.defaultClient();

				Dimension dimension = new Dimension()
				    .withName("InstanceId")
				    .withValue(instanceId);

				PutMetricAlarmRequest request = new PutMetricAlarmRequest()
				    .withAlarmName(alarmName)
				    .withComparisonOperator(
				        ComparisonOperator.GreaterThanThreshold)
				    .withEvaluationPeriods(1)
				    .withMetricName("CPUUtilization")
				    .withNamespace("AWS/EC2")
				    .withPeriod(60)
				    .withStatistic(Statistic.Average)
				    .withThreshold(70.0)
				    .withActionsEnabled(false)
				    .withAlarmDescription(
				        "Alarm when server CPU utilization exceeds 70%")
				    .withUnit(StandardUnit.Seconds)
				    .withDimensions(dimension);

				PutMetricAlarmResult response = cw.putMetricAlarm(request);
	 }

}
