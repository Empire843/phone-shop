package ptit.tttn.phoneshop.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AwsS3Service {
    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    try {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (fileExtension != null) {
            String contentType = "image/" + fileExtension;
            metadata.setContentType(contentType);
        }
        String fileName = "images/" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
        System.out.println("File URL: " + fileUrl);
        mav.addObject("url", fileUrl);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
