package ptit.tttn.phoneshop.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
public class AwsS3Controller {
    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @PostMapping("/uploadFile")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) {
        ModelAndView mav = new ModelAndView("redirect:/success");
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
        return mav;
    }
}
//https://imagephoneshop.s3.amazonaws.com/images/Screenshot+2023-07-26+232348.png