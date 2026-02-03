package com.example.hrms_platform_document.controller;

import com.example.hrms_platform_document.entity.Document;
import com.example.hrms_platform_document.service.DocumentService;
import com.example.hrms_platform_document.service.S3StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/hrms/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final S3StorageService storageService;

    public DocumentController(
            DocumentService documentService,
            S3StorageService storageService
    ) {
        this.documentService = documentService;
        this.storageService = storageService;
    }

    // =========================
    // UPLOAD DOCUMENT
    // =========================
    @PostMapping(
            value = "/upload",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentName") String documentName,
            @RequestParam("documentType") String documentType,
            @RequestParam("employeeId") Long employeeId
    ) throws Exception {

        documentService.uploadDocument(
                file,
                documentName,
                documentType,
                employeeId
        );

        return ResponseEntity.ok("Document uploaded successfully");
    }

    // =========================
    // DOWNLOAD DOCUMENT
    // =========================
    @GetMapping("/{documentId}/download")
    public ResponseEntity<String> download(@PathVariable Long documentId) {

        //  Get S3 file path from DB
        String filePath = documentService.getFilePath(documentId);

        // Generate presigned URL
        String downloadUrl = storageService.generatePresignedUrl(filePath);

        // Return URL
        return ResponseEntity.ok(downloadUrl);
    }


    @GetMapping("/{documentId}")
    public ResponseEntity<Document> getDocument(@PathVariable Long documentId) {

        Document document = documentService.getDocument(documentId);
        return ResponseEntity.ok(document);
    }
}
