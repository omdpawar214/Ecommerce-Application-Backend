package com.ecommerce.Ecommerce_App.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //get original file name
        String originalFileName = file.getOriginalFilename();

        //make a unique file name to resolve conflicts
        String randomId = UUID.randomUUID().toString();
        String filename = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.separator + filename;

        //check if path exists
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();

        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //return filename
        return filename;
    }
}
