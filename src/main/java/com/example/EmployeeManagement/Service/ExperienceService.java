package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.ExperienceDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.Experience;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import com.example.EmployeeManagement.Repository.ExperienceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ExperienceService {

    private ExperienceRepository experienceRepository;
    private EmployeeRepository employeeRepository;

    // Get all experience
    public List<ExperienceDTO> getAllExperience() {
        return experienceRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get by experienceId
    public ExperienceDTO getExperienceById(Long id) {
        Experience exp = experienceRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Experience not found with id: " + id));
        return mapToDto(exp);
    }

    // Get all experience of employee
    public List<ExperienceDTO> getExperienceByEmployeeId(Long employeeId) {
        return experienceRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Add experience
    public ExperienceDTO addExperience(Long employeeId, Experience experience) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        experience.setEmployee(employee);
        experience.setCreatedAt(LocalDateTime.now());
        experience.setUpdatedAt(LocalDateTime.now());

        return mapToDto(experienceRepository.save(experience));
    }

    // Partial update
    @Transactional
    public ExperienceDTO updateExperience(Long experienceId, Long employeeId, ExperienceDTO dto) {

        Experience experience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new DataNotFoundException("Experience not found with id: " + experienceId));

        // Security check
        if (!experience.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not allowed to modify this experience");
        }

        if (dto.getCompany() != null)
            experience.setCompany(dto.getCompany());

        if (dto.getDesignation() != null)
            experience.setDesignation(dto.getDesignation());

        if (dto.getFromDate() != null)
            experience.setFromDate(dto.getFromDate());

        if (dto.getToDate() != null)
            experience.setToDate(dto.getToDate());

        experience.setUpdatedAt(LocalDateTime.now());

        return mapToDto(experience);
    }

    // Delete one experience
    public void deleteByExperienceId(Long id) {
        experienceRepository.deleteById(id);
    }

    // Delete all experience of employee
    public void deleteByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        experienceRepository.deleteByEmployeeId(employeeId);
    }

    // Mapper
    public ExperienceDTO mapToDto(Experience experience) {

        ExperienceDTO dto = new ExperienceDTO();
        dto.setExperienceId(experience.getExperienceId());

        if (experience.getEmployee() != null)
            dto.setEmployeeId(experience.getEmployee().getEmployeeId());

        dto.setCompany(experience.getCompany());
        dto.setDesignation(experience.getDesignation());
        dto.setFromDate(experience.getFromDate());
        dto.setToDate(experience.getToDate());
        dto.setCreatedAt(experience.getCreatedAt());
        dto.setUpdatedAt(experience.getUpdatedAt());

        return dto;
    }
}
