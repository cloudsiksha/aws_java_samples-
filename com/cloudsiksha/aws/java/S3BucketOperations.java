package com.cloudsiksha.aws.java;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.Iterator;
import java.util.List;

public class S3BucketOperations {
	
	public static Bucket getBucket(String bucket_name) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }
	
	public static Bucket myBucketCreate(String bucket_name){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		Bucket b = null;
		if (s3.doesBucketExist(bucket_name)) {
		    System.out.format("Bucket %s already exists!\n", bucket_name);
		    b = getBucket(bucket_name);
		} else {
		    try {
		        b = s3.createBucket(bucket_name);
		    } catch (AmazonS3Exception e) {
		        System.err.println(e.getErrorMessage());
		    }
		}
		return b;
	}
	
	public static void myBucketObjectsDelete(String bucket_name){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
		    System.out.println(" - removing objects from bucket");
		    ObjectListing object_listing = s3.listObjects(bucket_name);
		    while (true) {
		        for (Iterator<?> iterator =
		                object_listing.getObjectSummaries().iterator();
		                iterator.hasNext();) {
		            S3ObjectSummary summary = (S3ObjectSummary)iterator.next();
		            s3.deleteObject(bucket_name, summary.getKey());
		        }

		        // more object_listing to retrieve?
		        if (object_listing.isTruncated()) {
		            object_listing = s3.listNextBatchOfObjects(object_listing);
		        } else {
		            break;
		        }
		    };
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
	
	public static void myBucketDelete(String bucket_name){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
		    s3.deleteBucket(bucket_name);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}

}
