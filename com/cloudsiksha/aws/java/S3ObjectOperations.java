package com.cloudsiksha.aws.java;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class S3ObjectOperations {
	public static void uploadObject(String bucket_name, String key_name, String file_path){
	  final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
	  try {
	    s3.putObject(bucket_name, key_name, file_path);
	  } catch (AmazonServiceException e) {
	      System.err.println(e.getErrorMessage());
	      System.exit(1);
	  }
	}
	
	public static void listObjects(String bucket_name){
		// final AmazonS3 s3 = new AmazonS3Client();
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		ObjectListing ol = s3.listObjects(bucket_name);
		List<S3ObjectSummary> objects = ol.getObjectSummaries();
		for (S3ObjectSummary os: objects) {
		    System.out.println("* " + os.getKey());
		}
	}
	
	public static void downloadObject(String bucket_name, String key_name){
		//final AmazonS3 s3 = new AmazonS3Client();
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
		    S3Object o = s3.getObject(bucket_name, key_name);
		    S3ObjectInputStream s3is = o.getObjectContent();
		    FileOutputStream fos = new FileOutputStream(new File(key_name));
		    byte[] read_buf = new byte[1024];
		    int read_len = 0;
		    while ((read_len = s3is.read(read_buf)) > 0) {
		        fos.write(read_buf, 0, read_len);
		    }
		    s3is.close();
		    fos.close();
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		} catch (FileNotFoundException e) {
		    System.err.println(e.getMessage());
		    System.exit(1);
		} catch (IOException e) {
		    System.err.println(e.getMessage());
		    System.exit(1);
		}
	}
	
	public static void copyObject(String from_bucket, String object_key, String to_bucket){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
		    s3.copyObject(from_bucket, object_key, to_bucket, object_key);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
	
	public static void deleteObject(String bucket_name, String object_key){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
		    s3.deleteObject(bucket_name, object_key);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
	
	public static void deleteMultipleObjects(String[] object_keys, String bucket_name){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
		    DeleteObjectsRequest dor = new DeleteObjectsRequest(bucket_name)
		        .withKeys(object_keys);
		    s3.deleteObjects(dor);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
		
}
