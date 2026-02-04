package com.example.hrms_platform_document.controller;

import com.example.EmployeeManagement.Model.Employee;
import com.example.hrms_platform_document.entity.Document;
import com.example.hrms_platform_document.service.DocumentService;
import com.example.hrms_platform_document.service.S3StorageService;
import com.example.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hrms/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final S3StorageService storageService;
    private final SecurityUtil securityUtil;

    // UPLOAD DOCUMENT
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','ADMIN')
        or hasRole('EMPLOYEE')
    """)
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentName") String documentName,
            @RequestParam("documentType") String documentType,
            @RequestParam(value = "employeeId", required = false) Long employeeId
    ) throws Exception {

        Employee targetEmployee;

        // EMPLOYEE → can upload ONLY for self
        if (securityUtil.hasRole("EMPLOYEE")) {
            targetEmployee = securityUtil.getLoggedInEmployee();
        }
        // HR / ADMIN → can upload for anyone
        else {
            if (employeeId == null) {
                throw new RuntimeException("employeeId is required for HR/Admin upload");
            }
            targetEmployee = documentService.getEmployee(employeeId);
        }

        documentService.uploadDocument(
                file,
                documentName,
                documentType,
                targetEmployee.getEmployeeId()
        );

        return ResponseEntity.ok("Document uploaded successfully");
    }

    // =========================
    // DOWNLOAD DOCUMENT
    // =========================
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','ADMIN')
        or (hasRole('EMPLOYEE') and @documentService.isOwner(#documentId))
    """)
    @GetMapping("/{documentId}/download")
    public ResponseEntity<String> download(@PathVariable Long documentId) {

        String filePath = documentService.getFilePath(documentId);
        String downloadUrl = storageService.generatePresignedUrl(filePath);

        return ResponseEntity.ok(downloadUrl);
    }

    // =========================
    // VIEW DOCUMENT METADATA
    // =========================
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','ADMIN')
        or (hasRole('EMPLOYEE') and @documentService.isOwner(#documentId))
    """)
    @GetMapping("/{documentId}")
    public ResponseEntity<Document> getDocument(@PathVariable Long documentId) {

        Document document = documentService.getDocument(documentId);
        return ResponseEntity.ok(document);
    }

    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @GetMapping("/employee/{employeeId}")
    public List<Document> getDocumentsByEmployee(
            @PathVariable Long employeeId
    ) {
        return documentService.getDocumentsByEmployee(employeeId);
    }
}
