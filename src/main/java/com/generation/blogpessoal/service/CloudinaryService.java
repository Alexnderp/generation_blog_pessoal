package com.generation.blogpessoal.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public  String uploadImage(MultipartFile multipartFile) throws IOException{
        Map<String, String> options = new HashMap<>();
        options.put("folder", "users");

        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), options);
        return uploadResult.get("url").toString();
    }
}
