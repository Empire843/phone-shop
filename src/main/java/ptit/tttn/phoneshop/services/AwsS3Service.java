package ptit.tttn.phoneshop.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class AwsS3Service {
    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

   public String uploadFile(MultipartFile file, String path) {
       String fileUrl = "";
       try {
           ObjectMetadata metadata = new ObjectMetadata();
           metadata.setContentLength(file.getSize());

           String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
           if (fileExtension != null) {
               String contentType = "image/" + fileExtension;
               metadata.setContentType(contentType);
           }
           String fileName = path + "/" + file.getOriginalFilename();
           s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                   .withCannedAcl(CannedAccessControlList.PublicRead));

           fileUrl = s3Client.getUrl(bucketName, fileName).toString();
           System.out.println("File URL: " + fileUrl);
       } catch (Exception e) {
           throw new RuntimeException("Fail to upload file");
       }
       return fileUrl;
   }
    public List<String> uploadMultipleFile(MultipartFile[] files, String path) {
        List<String> urls = new ArrayList<>();
        String fileUrl = "";
        try {
            for (MultipartFile file : files) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());

                String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
                if (fileExtension != null) {
                    String contentType = "image/" + fileExtension;
                    metadata.setContentType(contentType);
                }
                String fileName = path + "/" + file.getOriginalFilename();
                s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                fileUrl = s3Client.getUrl(bucketName, fileName).toString();
                urls.add(fileUrl);
                System.out.println("File URL: " + fileUrl);
            }
        } catch (Exception e) {
            throw new RuntimeException("Fail to upload file");
        }
        return urls;
    }
    public void deleteFile(String fileUrl) {
        try {
            String decodedUrl = URLDecoder.decode(fileUrl, "UTF-8");
            String bucketName = "imagephoneshop";
            String fileName = decodedUrl.substring(decodedUrl.lastIndexOf("/") + 1);
            s3Client.deleteObject(bucketName, fileName);
            System.out.println("File deleted successfully.");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Fail to delete file");
        }
    }
}
