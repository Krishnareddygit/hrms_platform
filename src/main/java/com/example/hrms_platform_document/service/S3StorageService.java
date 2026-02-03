package com.example.hrms_platform_document.service;



import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.*;

import java.io.IOException;
import java.time.Duration;

@Service
public class S3StorageService {

    private final S3Client s3Client;
    private final String bucketName = "hrms-documents-chetna";

    public S3StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // Upload file
    public String upload(MultipartFile file, String key) throws IOException {

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return key;
    }

    // Generate download URL
    public String generatePresignedUrl(String key) {

        try (S3Presigner presigner = S3Presigner.create()) {

            GetObjectRequest getObjectRequest =
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build();

            GetObjectPresignRequest presignRequest =
                    GetObjectPresignRequest.builder()
                            .signatureDuration(Duration.ofMinutes(5))
                            .getObjectRequest(getObjectRequest)
                            .build();

            return presigner.presignGetObject(presignRequest)
                    .url()
                    .toString();
        }
    }
}
