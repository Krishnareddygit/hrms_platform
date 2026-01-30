package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.EmployeeManagerHistoryDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmployeeManagerHistory;
import com.example.EmployeeManagement.Repository.EmployeeManagerHistoryRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeManagerHistoryService {

    private EmployeeManagerHistoryRepository historyRepository;
    private EmployeeRepository employeeRepository;

    // Get all history
    public List<EmployeeManagerHistoryDTO> getAll() {
        return historyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get by historyId
    public EmployeeManagerHistoryDTO getById(Long id) {
        EmployeeManagerHistory history = historyRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Manager history not found with id: " + id));
        return mapToDto(history);
    }

    // Get manager history of employee
    public List<EmployeeManagerHistoryDTO> getByEmployeeId(Long employeeId) {
        return historyRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get employees under a manager
    public List<EmployeeManagerHistoryDTO> getByManagerId(Long managerId) {
        return historyRepository.findByManagerId(managerId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Add manager history
    public EmployeeManagerHistoryDTO addHistory(Long employeeId, EmployeeManagerHistory history) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        history.setEmployee(employee);
        history.setCreatedAt(LocalDateTime.now());
        history.setUpdatedAt(LocalDateTime.now());

        return mapToDto(historyRepository.save(history));
    }

    // Partial update
    @Transactional
    public EmployeeManagerHistoryDTO updateHistory(
            Long historyId,
            Long employeeId,
            EmployeeManagerHistoryDTO dto) {

        EmployeeManagerHistory history = historyRepository.findById(historyId)
                .orElseThrow(() -> new DataNotFoundException("Manager history not found with id: " + historyId));

        // Security check
        if (!history.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not allowed to modify this manager history");
        }

        if (dto.getManagerId() != null)
            history.setManagerId(dto.getManagerId());

        if (dto.getFromDate() != null)
            history.setFromDate(dto.getFromDate());

        if (dto.getToDate() != null)
            history.setToDate(dto.getToDate());

        if (dto.getReason() != null)
            history.setReason(dto.getReason());

        history.setUpdatedAt(LocalDateTime.now());

        return mapToDto(history);
    }

    // Delete one history
    public void deleteById(Long id) {
        historyRepository.deleteById(id);
    }

    // Delete all history of employee
    public void deleteByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        historyRepository.deleteByEmployeeId(employeeId);
    }

    // Mapper
    private EmployeeManagerHistoryDTO mapToDto(EmployeeManagerHistory history) {

        EmployeeManagerHistoryDTO dto = new EmployeeManagerHistoryDTO();
        dto.setHistoryId(history.getHistoryId());

        if (history.getEmployee() != null)
            dto.setEmployeeId(history.getEmployee().getEmployeeId());

        dto.setManagerId(history.getManagerId());
        dto.setFromDate(history.getFromDate());
        dto.setToDate(history.getToDate());
        dto.setReason(history.getReason());
        dto.setCreatedAt(history.getCreatedAt());
        dto.setUpdatedAt(history.getUpdatedAt());

        return dto;
    }
}
