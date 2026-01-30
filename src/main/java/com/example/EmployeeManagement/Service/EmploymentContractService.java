package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.EmploymentContractDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmploymentContract;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import com.example.EmployeeManagement.Repository.EmploymentContractRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EmploymentContractService {

    private EmploymentContractRepository contractRepository;
    private EmployeeRepository employeeRepository;

    // Get all contracts
    public List<EmploymentContractDTO> getAllContracts() {
        return contractRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get contract by id
    public EmploymentContractDTO getContractById(Long id) {
        EmploymentContract contract = contractRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Contract not found with id: " + id));
        return mapToDto(contract);
    }

    // Get all contracts of an employee
    public List<EmploymentContractDTO> getContractsByEmployeeId(Long employeeId) {
        return contractRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Add contract
    public EmploymentContractDTO addContract(Long employeeId, EmploymentContract contract) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        contract.setEmployee(employee);
        contract.setCreatedAt(LocalDateTime.now());
        contract.setUpdatedAt(LocalDateTime.now());

        return mapToDto(contractRepository.save(contract));
    }

    // Partial update
    @Transactional
    public EmploymentContractDTO updateContract(Long contractId, Long employeeId, EmploymentContractDTO dto) {

        EmploymentContract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new DataNotFoundException("Contract not found with id: " + contractId));

        // Security check
        if (!contract.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not allowed to modify this contract");
        }

        if (dto.getContractStart() != null)
            contract.setContractStart(dto.getContractStart());

        if (dto.getContractEnd() != null)
            contract.setContractEnd(dto.getContractEnd());

        if (dto.getContractType() != null)
            contract.setContractType(dto.getContractType());

        contract.setUpdatedAt(LocalDateTime.now());

        return mapToDto(contract);
    }

    // Delete one contract
    public void deleteByContractId(Long id) {
        contractRepository.deleteById(id);
    }

    // Delete all contracts of employee
    public void deleteByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        contractRepository.deleteByEmployeeId(employeeId);
    }

    // Mapper
    public EmploymentContractDTO mapToDto(EmploymentContract contract) {

        EmploymentContractDTO dto = new EmploymentContractDTO();
        dto.setContractId(contract.getContractId());

        if (contract.getEmployee() != null)
            dto.setEmployeeId(contract.getEmployee().getEmployeeId());

        dto.setContractStart(contract.getContractStart());
        dto.setContractEnd(contract.getContractEnd());
        dto.setContractType(contract.getContractType());
        dto.setCreatedAt(contract.getCreatedAt());
        dto.setUpdatedAt(contract.getUpdatedAt());

        return dto;
    }
}
