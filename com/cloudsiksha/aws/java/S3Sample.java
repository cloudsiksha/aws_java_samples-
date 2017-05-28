package com.cloudsiksha.aws.java;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.*;
import java.util.List;
import com.cloudsiksha.aws.java.*;

public class S3Sample{
  public static void listbucket(){
	final AmazonS3 s3 = new AmazonS3Client();
	List<Bucket> buckets = s3.listBuckets();
	System.out.println("Your Amazon S3 buckets:");
	for (Bucket b : buckets) {
	  System.out.println("* " + b.getName());
	}
  }
}


