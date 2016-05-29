package s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import play.Configuration;

import javax.inject.Singleton;
import java.io.File;
import java.io.InputStream;

@Singleton
public class S3Manager {

    public static final String S3_BASE_URL = "https://s3-us-west-2.amazonaws.com/";

    private static final String BUCKET_NAME = "cloud-images-andes";

    private static final String AWS_ACCESS_KEY = "aws.access.key";
    private static final String AWS_SECRET_KEY = "aws.secret.key";

    public static AmazonS3 amazonS3;

    private static S3Manager ourInstance;

    private S3Manager() {}

    public static S3Manager getInstance(Configuration configuration) {
        if (ourInstance == null) {
            ourInstance = new S3Manager();
            initAmazonS3(configuration);
        }
        return ourInstance;
    }

    private static void initAmazonS3(Configuration configuration) {
        String accessKey = configuration.getString(AWS_ACCESS_KEY);
        String secretKey = configuration.getString(AWS_SECRET_KEY);

        if ((accessKey != null) && (secretKey != null)) {
            AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            amazonS3 = new AmazonS3Client(awsCredentials);
        }
    }

    public boolean saveFile(String fileName, InputStream inputStream, ObjectMetadata metadata) {
        if (amazonS3 != null) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME,fileName,inputStream, metadata);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for allObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION
            amazonS3.putObject(putObjectRequest);// upload file
            return true;
        }
        return false;
    }

    public static String getS3ImageUrl(String fileName) {
        return S3_BASE_URL + BUCKET_NAME + "/" + fileName;
    }
}

