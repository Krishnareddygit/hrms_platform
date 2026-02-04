package com.example.hrms_platform_document.service;

import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import com.example.hrms_platform_document.entity.Document;
import com.example.hrms_platform_document.entity.DocumentVersion;
import com.example.hrms_platform_document.repository.DocumentRepository;
import com.example.hrms_platform_document.repository.DocumentVersionRepository;
import com.example.hrms_platform_document.util.FileNameUtil;
import com.example.security.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepo;
    private final DocumentVersionRepository versionRepo;
    private final EmployeeRepository employeeRepo;
    private final DocumentAuditService auditService;
    private final S3StorageService storageService;

    private SecurityUtil securityUtil;

    // =========================
    // 📤 UPLOAD DOCUMENT
    // =========================
    @Transactional
    public void uploadDocument(
            MultipartFile file,
            String name,
            String type,
            Long employeeId
    ) throws Exception {

        // 1️⃣ Fetch managed Employee
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        // 2️⃣ Save Document metadata
        Document document = new Document();
        document.setDocumentName(name);
        document.setDocumentType(type);
        document.setEmployee(emp);
        document.setUploadedBy(emp);
        document.setCreatedAt(LocalDateTime.now());

        documentRepo.save(document);

        // 3️⃣ Prepare S3 file path
        String safeFileName = FileNameUtil.sanitize(file.getOriginalFilename());

        String filePath =
                "employee_" + employeeId +
                        "/doc_" + document.getDocumentId() +
                        "_v1_" + safeFileName;

        // 4️⃣ Upload to AWS S3
        storageService.upload(file, filePath);

        // 5️⃣ Save DocumentVersion metadata
        DocumentVersion version = new DocumentVersion();
        version.setDocument(document);
        version.setFilePath(filePath);
        version.setMimeType(file.getContentType());
        version.setFileSize(file.getSize());
        version.setVersionNumber(1);
        version.setUploadedBy(emp);
        version.setUploadedAt(LocalDateTime.now());

        versionRepo.save(version);

        // 6️⃣ Update current version in Document
        document.setCurrentVersionId(version.getVersionId());
        documentRepo.save(document);

        // 7️⃣ Audit log
        auditService.log(document, version, "UPLOAD", emp);
    }

    // =========================
    // 📥 GET FILE PATH (FOR DOWNLOAD)
    // =========================
    public String getFilePath(Long documentId) {

        Document doc = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        DocumentVersion version = versionRepo.findById(doc.getCurrentVersionId())
                .orElseThrow(() -> new RuntimeException("Document version not found"));

        return version.getFilePath();
    }

    // =========================
    // 📄 GET DOCUMENT METADATA
    // =========================
    public Document getDocument(Long documentId) {

        return documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public boolean isOwner(Long documentId) {


        Employee loggedIn = securityUtil.getLoggedInEmployee();

        Document document = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return document.getEmployee().getEmployeeId().equals(loggedIn.getEmployeeId());
    }

    public Employee getEmployee(Long employeeId) {
        return employeeRepo.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(employeeId)
                );
    }

    public List<Document> getDocumentsByEmployee(Long employeeId) {

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(employeeId)
                );

        return documentRepo.findByEmployee(employee);
    }

}
