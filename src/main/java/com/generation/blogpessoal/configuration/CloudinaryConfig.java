package com.generation.blogpessoal.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${CLOUD_NAME}")
    private String cloudName;

    @Value("${CLOUD_KEY}")
    private String cloudKey;

    @Value("${CLOUD_SECRET}")
    private String cloudSecret;

    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary  = new Cloudinary("cloudinary://" + cloudName + "/" + cloudKey + "/" + cloudSecret);
        return  cloudinary;
    }
}
