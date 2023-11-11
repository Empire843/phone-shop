package ptit.tttn.phoneshop.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ptit.tttn.phoneshop.services.AwsS3Service;

import java.io.IOException;
import java.util.List;

@RestController
public class AwsS3Controller {
    @Autowired
    private AwsS3Service awsS3Service;
    @PostMapping("/uploadFile")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile[] files) {
        ModelAndView mav = new ModelAndView("success");
        try {
//            String fileUrl = awsS3Service.uploadFile(files, "images");
            List<String> urls = awsS3Service.uploadMultipleFile(files, "images");
            mav.addObject("urls", urls);
        } catch (Exception e) {
            mav.setViewName("error");
            mav.addObject("errorMessage", "Upload failed: " + e.getMessage());
            return mav;
        }
        return mav;
    }
    @DeleteMapping("/deleteFile")
    public ModelAndView deleteFile(@RequestParam("url") String fileUrl) {
        ModelAndView mav = new ModelAndView("success");
        try {
            awsS3Service.deleteFile(fileUrl);
        } catch (Exception e) {
            mav.setViewName("error");
            mav.addObject("errorMessage", "Delete failed: " + e.getMessage());
            return mav;
        }
        return mav;
    }

    @GetMapping("/success")
    public ModelAndView success() {
        ModelAndView mav = new ModelAndView("success");
        return mav;
    }
    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView("error");
        return mav;
    }

}
//https://imagephoneshop.s3.amazonaws.com/images/Screenshot+2023-07-26+232348.png