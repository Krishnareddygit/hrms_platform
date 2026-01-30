package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.EmployeeEducationDTO;
import com.example.EmployeeManagement.DTO.EmployeeEducationUpdateDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.EmployeeEducationNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmployeeEducation;
import com.example.EmployeeManagement.Repository.EmployeeEducationRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeEducationService {

    private EmployeeEducationRepository employeeEducationRepository;
    private EmployeeRepository employeeRepository;

    public List<EmployeeEducationDTO> getAllEmployeesEducation(){
        return employeeEducationRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public EmployeeEducationDTO getEmployeeEducationById(Long id){
        EmployeeEducation empEducation = employeeEducationRepository
                                                .findById(id)
                                                .orElseThrow(() -> new EmployeeEducationNotFoundException(id));
        return mapToDto(empEducation);
    }

    public List<EmployeeEducationDTO> getEmployeeEducationByEmployeeId(Long employeeId){
        Employee employee =  employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        List<EmployeeEducation> employeeEducations = employeeEducationRepository.findByEmployeeId(employeeId);

        return employeeEducations
                .stream()
                .map(this::mapToDto)
                .toList();
    }


    public EmployeeEducationDTO addEmployeeEducation(Long employeeId,
                                                     EmployeeEducation employeeEducation) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        employeeEducation.setEmployee(employee);
        employeeEducation.setCreatedAt(LocalDateTime.now());
        employeeEducation.setUpdatedAt(LocalDateTime.now());

        EmployeeEducation saved = employeeEducationRepository.save(employeeEducation);

        return mapToDto(saved);
    }


    public void deleteEmployeeEducationById(Long id){
        employeeEducationRepository.deleteById(id);
    }

    public void deleteEmployeeEducationByEmployeeId(Long employeeId){
        Employee employee =  employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeeEducationRepository.deleteByEmployeeId(employeeId);
    }


    @Transactional
    public EmployeeEducationDTO updateEducation(Long educationId,
                                             Long employeeId,
                                             EmployeeEducationUpdateDTO dto) {

        EmployeeEducation education = employeeEducationRepository
                .findById(educationId)
                .orElseThrow(() -> new EmployeeEducationNotFoundException(employeeId));

        // Security: ensure this education belongs to this employee
        if (!education.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not eligible to modify this data. Contact your admin");
        }

        // Partial update — only update what is not null
        if (dto.getYear() != null)
            education.setYear(dto.getYear());

        if (dto.getGrade() != null)
            education.setGrade(dto.getGrade());

        if (dto.getDegree() != null)
            education.setDegree(dto.getDegree());

        if (dto.getInstitution() != null)
            education.setInstitution(dto.getInstitution());

        if (dto.getIsHighest() != null)
            education.setIsHighest(dto.getIsHighest());

        education.setUpdatedAt(LocalDateTime.now());

        return mapToDto(employeeEducationRepository.save(education));
    }

    public EmployeeEducationDTO mapToDto(EmployeeEducation employeeEducation){

        EmployeeEducationDTO dto = new EmployeeEducationDTO();
        dto.setEmployeeEducationId(employeeEducation.getEmployeeEducationId());
        if(employeeEducation.getEmployee().getEmployeeId() != null)
          dto.setEmployeeId(employeeEducation.getEmployee().getEmployeeId());
        dto.setYear(employeeEducation.getYear());
        dto.setGrade(employeeEducation.getGrade());
        dto.setDegree(employeeEducation.getDegree());
        dto.setInstitution(employeeEducation.getInstitution());
        dto.setIsHighest(employeeEducation.getIsHighest());

        if(employeeEducation.getCreatedAt() != null)
            dto.setCreatedAt(employeeEducation.getCreatedAt());
        if(employeeEducation.getUpdatedAt() != null)
            dto.setUpdatedAt(employeeEducation.getUpdatedAt());
        return dto;
    }
}
