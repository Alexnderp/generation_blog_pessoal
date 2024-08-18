package com.generation.blogpessoal.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public  String uploadImage(MultipartFile multipartFile) throws IOException{
        Map<String, Object> options = ObjectUtils.asMap(
          "use_filename",true,
                "asset_folder",""
        );

        try {
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), options);
            return uploadResult.get("secure_url").toString();
        } catch (Exception e){
            throw new RemoteException("Erro durante o upload");
        }

    }
}
