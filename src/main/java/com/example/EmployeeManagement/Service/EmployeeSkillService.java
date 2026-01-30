package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.EmployeeSkillDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmployeeSkill;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import com.example.EmployeeManagement.Repository.EmployeeSkillRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeSkillService {

    private EmployeeSkillRepository employeeSkillRepository;
    private EmployeeRepository employeeRepository;

    // Get all skills
    public List<EmployeeSkillDTO> getAllSkills() {
        return employeeSkillRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get by skillId
    public EmployeeSkillDTO getSkillById(Long id) {
        EmployeeSkill skill = employeeSkillRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Skill not found with id: " + id));
        return mapToDto(skill);
    }

    // Get all skills of an employee
    public List<EmployeeSkillDTO> getSkillsByEmployeeId(Long employeeId) {
        return employeeSkillRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Add skill
    public EmployeeSkillDTO addSkill(Long employeeId, EmployeeSkill skill) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        skill.setEmployee(employee);
        skill.setCreatedAt(LocalDateTime.now());
        skill.setUpdatedAt(LocalDateTime.now());

        return mapToDto(employeeSkillRepository.save(skill));
    }

    // Partial update
    @Transactional
    public EmployeeSkillDTO updateSkill(Long skillId, Long employeeId, EmployeeSkillDTO dto) {

        EmployeeSkill skill = employeeSkillRepository.findById(skillId)
                .orElseThrow(() -> new DataNotFoundException("Skill not found with id: " + skillId));

        // Security check
        if (!skill.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not allowed to modify this skill");
        }

        if (dto.getSkillName() != null)
            skill.setSkillName(dto.getSkillName());

        if (dto.getSkillType() != null)
            skill.setSkillType(dto.getSkillType());

        if (dto.getProficiencyLevel() != null)
            skill.setProficiencyLevel(dto.getProficiencyLevel());

        if (dto.getYearsOfExperience() != null)
            skill.setYearsOfExperience(dto.getYearsOfExperience());

        if (dto.getLastUsedYear() != null)
            skill.setLastUsedYear(dto.getLastUsedYear());

        if (dto.getIsPrimary() != null)
            skill.setIsPrimary(dto.getIsPrimary());

        skill.setUpdatedAt(LocalDateTime.now());

        return mapToDto(skill);
    }

    // Delete one skill
    public void deleteBySkillId(Long id) {
        employeeSkillRepository.deleteById(id);
    }

    // Delete all skills of an employee
    public void deleteByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        employeeSkillRepository.deleteByEmployeeId(employeeId);
    }

    // Mapper
    public EmployeeSkillDTO mapToDto(EmployeeSkill skill) {

        EmployeeSkillDTO dto = new EmployeeSkillDTO();
        dto.setEmployeeSkillId(skill.getEmployeeSkillId());

        if (skill.getEmployee() != null)
            dto.setEmployeeId(skill.getEmployee().getEmployeeId());

        dto.setSkillName(skill.getSkillName());
        dto.setSkillType(skill.getSkillType());
        dto.setProficiencyLevel(skill.getProficiencyLevel());
        dto.setYearsOfExperience(skill.getYearsOfExperience());
        dto.setLastUsedYear(skill.getLastUsedYear());
        dto.setIsPrimary(skill.getIsPrimary());
        dto.setCreatedAt(skill.getCreatedAt());
        dto.setUpdatedAt(skill.getUpdatedAt());

        return dto;
    }
}

