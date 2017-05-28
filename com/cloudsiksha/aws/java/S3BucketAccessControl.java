package com.cloudsiksha.aws.java;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.Permission;
import java.util.List;

public class S3BucketAccessControl {
	public static void myBucketListAccessControl(String bucket_name){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
		    AccessControlList acl = s3.getBucketAcl(bucket_name);
		    List<Grant> grants = acl.getGrantsAsList();
		    for (Grant grant : grants) {
		        System.out.format("  %s: %s\n", grant.getGrantee().getIdentifier(),
		                grant.getPermission().toString());
		    }
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
	
	public static void myBucketSetAccessControl(String bucket_name, String email, String access){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
		    // get the current ACL
		    AccessControlList acl = s3.getBucketAcl(bucket_name);
		    // set access for the grantee
		    EmailAddressGrantee grantee = new EmailAddressGrantee(email);
		    Permission permission = Permission.valueOf(access);
		    acl.grantPermission(grantee, permission);
		    s3.setBucketAcl(bucket_name, acl);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
	
	public static void myObjectListAccessControl(String bucket_name, String object_key){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
		try {
		    AccessControlList acl = s3.getObjectAcl(bucket_name, object_key);
		    List<Grant> grants = acl.getGrantsAsList();
		    for (Grant grant : grants) {
		        System.out.format("  %s: %s\n", grant.getGrantee().getIdentifier(),
		                grant.getPermission().toString());
		    }
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
	
	public static void myObjectAccessControlSet(String bucket_name, String object_key, String email, String access){
		final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
	    try {
	        // get the current ACL
	        AccessControlList acl = s3.getObjectAcl(bucket_name, object_key);
	        // set access for the grantee
	        EmailAddressGrantee grantee = new EmailAddressGrantee(email);
	        Permission permission = Permission.valueOf(access);
	        acl.grantPermission(grantee, permission);
	        s3.setObjectAcl(bucket_name, object_key, acl);
	    } catch (AmazonServiceException e) {
	        System.err.println(e.getErrorMessage());
	        System.exit(1);
	    }
		
	}
}
