package util;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ImageUtil {

    private static final String PNG = "png";
    private static final String BASE_64_SEPARATOR = ",";

    public static byte [] getByteArrayFromBase64(String base64) {
        String [] base64Parts = base64.split(BASE_64_SEPARATOR);
        String base64Data = "";

        if (base64Parts.length == 2) {
            base64Data = base64Parts[1];
        } else if (base64Parts.length == 1) {
            base64Data = base64Parts[0];
        }

        return Base64.decodeBase64(base64Data);
    }

    public static InputStream getInputStreamFromByteArray(byte [] byteArray) {
        return new ByteArrayInputStream(byteArray);
    }

    public static ObjectMetadata getMetadata(byte [] byteArray) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(byteArray.length);
        metadata.setContentType(PNG);
        return  metadata;
    }

}
