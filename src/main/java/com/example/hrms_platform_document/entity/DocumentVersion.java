package com.example.hrms_platform_document.entity;

import com.example.EmployeeManagement.Model.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_version")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long versionId;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;


    private Integer versionNumber;
    private String checksum;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private Employee uploadedBy;

    private LocalDateTime uploadedAt;

    // getters & setters
}

