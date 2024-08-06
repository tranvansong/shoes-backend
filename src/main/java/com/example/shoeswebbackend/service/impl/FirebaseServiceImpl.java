package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.service.FirebaseService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    public static final String BUCKET_NAME = "shoes-image.appspot.com";

    @Override
    public String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }

    @Override
    public void delete(String url) {
        try {
            // Extract the file name from the URL
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));

            // Initialize Google Cloud Storage client
            InputStream inputStream = FirebaseServiceImpl.class.getClassLoader().getResourceAsStream("firebase-private-key.json"); // change the file name with your one
            Credentials credentials = null;
            if (inputStream != null) {
                credentials = GoogleCredentials.fromStream(inputStream);
            }
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            BlobId blobId = BlobId.of(BUCKET_NAME, fileName);

            boolean deleted = storage.delete(blobId);

            if (deleted) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("File not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deleting file");
        }
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = FirebaseServiceImpl.class.getClassLoader().getResourceAsStream("firebase-private-key.json"); // change the file name with your one
        Credentials credentials = null;
        if (inputStream != null) {
            credentials = GoogleCredentials.fromStream(inputStream);
        }
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/"+BUCKET_NAME+"/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
