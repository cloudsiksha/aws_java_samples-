import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.Bucket;
import java.util.List;
import com.cloudsiksha.aws.java.*;

public class TestingS3 {
	public static void main(String[] args)
	{
		S3Sample.listbucket();
		// BucketCreation.myBucketDelete("cloudsiksha2");
	}

}
