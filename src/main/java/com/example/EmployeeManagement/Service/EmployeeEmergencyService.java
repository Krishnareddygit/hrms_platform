package com.example.EmployeeManagement.Service;

import org.springframework.stereotype.Service;


import com.example.EmployeeManagement.DTO.EmployeeEmergencyDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmployeeEmergency;
import com.example.EmployeeManagement.Repository.EmployeeEmergencyRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeEmergencyService {

    private EmployeeEmergencyRepository employeeEmergencyRepository;
    private EmployeeRepository employeeRepository;

    // Get all emergency contacts
    public List<EmployeeEmergencyDTO> getAllEmergencies() {
        return employeeEmergencyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get by emergencyId
    public EmployeeEmergencyDTO getEmergencyById(Long id) {
        EmployeeEmergency emergency = employeeEmergencyRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Emergency contact not found with id: " + id));
        return mapToDto(emergency);
    }

    // Get all emergency contacts of an employee
    public List<EmployeeEmergencyDTO> getEmergenciesByEmployeeId(Long employeeId) {
        List<EmployeeEmergency> emergencies = employeeEmergencyRepository.findByEmployeeId(employeeId);
        return emergencies.stream()
                .map(this::mapToDto)
                .toList();
    }

    // Add emergency contact
    public EmployeeEmergencyDTO addEmergency(Long employeeId, EmployeeEmergency emergency) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        emergency.setEmployee(employee);
        emergency.setCreatedAt(LocalDateTime.now());
        emergency.setUpdatedAt(LocalDateTime.now());

        return mapToDto(employeeEmergencyRepository.save(emergency));
    }

    // Partial update
    @Transactional
    public EmployeeEmergencyDTO updateEmergency(
            Long emergencyId,
            Long employeeId,
            EmployeeEmergencyDTO dto) {

        EmployeeEmergency emergency = employeeEmergencyRepository.findById(emergencyId)
                .orElseThrow(() -> new DataNotFoundException("Emergency contact not found with id: " + emergencyId));

        // Security check
        if (!emergency.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not allowed to modify this emergency contact");
        }

        if (dto.getContactName() != null)
            emergency.setContactName(dto.getContactName());

        if (dto.getRelation() != null)
            emergency.setRelation(dto.getRelation());

        if (dto.getPhoneNumber() != null)
            emergency.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getEffectiveFrom() != null)
            emergency.setEffectiveFrom(dto.getEffectiveFrom());

        if (dto.getEffectiveTo() != null)
            emergency.setEffectiveTo(dto.getEffectiveTo());

        emergency.setUpdatedAt(LocalDateTime.now());

        return mapToDto(emergency);
    }

    // Delete one emergency contact
    public void deleteByEmergencyId(Long id) {
        employeeEmergencyRepository.deleteById(id);
    }

    // Delete all emergencies of an employee
    public void deleteByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        employeeEmergencyRepository.deleteByEmployeeID(employeeId);
    }

    // Mapper
    public EmployeeEmergencyDTO mapToDto(EmployeeEmergency emergency) {
        EmployeeEmergencyDTO dto = new EmployeeEmergencyDTO();
        dto.setEmergencyId(emergency.getEmergencyId());

        if (emergency.getEmployee() != null)
            dto.setEmployeeId(emergency.getEmployee().getEmployeeId());

        dto.setContactName(emergency.getContactName());
        dto.setRelation(emergency.getRelation());
        dto.setPhoneNumber(emergency.getPhoneNumber());
        dto.setEffectiveFrom(emergency.getEffectiveFrom());
        dto.setEffectiveTo(emergency.getEffectiveTo());
        dto.setCreatedAt(emergency.getCreatedAt());
        dto.setUpdatedAt(emergency.getUpdatedAt());

        return dto;
    }
}

