package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.EmployeeBandDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmployeeBand;
import com.example.EmployeeManagement.Repository.EmployeeBandRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeBandService {

    private EmployeeBandRepository bandRepository;
    private EmployeeRepository employeeRepository;

    // Get all band history
    public List<EmployeeBandDTO> getAllBands() {
        return bandRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Get band history by id
    public EmployeeBandDTO getById(Long id) {
        EmployeeBand band = bandRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Band history not found with id: " + id));
        return mapToDto(band);
    }

    // Get band history of employee
    public List<EmployeeBandDTO> getByEmployeeId(Long employeeId) {
        return bandRepository.findByEmployeeId(employeeId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // Add band record
    public EmployeeBandDTO addBand(Long employeeId, EmployeeBand band) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        band.setEmployee(employee);
        band.setCreatedAt(LocalDateTime.now());
        band.setUpdatedAt(LocalDateTime.now());

        return mapToDto(bandRepository.save(band));
    }

    // Partial update
    @Transactional
    public EmployeeBandDTO updateBand(Long historyId, Long employeeId, EmployeeBandDTO dto) {

        EmployeeBand band = bandRepository.findById(historyId)
                .orElseThrow(() -> new DataNotFoundException("Band history not found with id: " + historyId));

        // Security check
        if (!band.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not allowed to modify this band history");
        }

        if (dto.getBandLevel() != null)
            band.setBandLevel(dto.getBandLevel());

        if (dto.getFromDate() != null)
            band.setFromDate(dto.getFromDate());

        if (dto.getToDate() != null)
            band.setToDate(dto.getToDate());

        band.setUpdatedAt(LocalDateTime.now());

        return mapToDto(band);
    }

    // Delete one band history
    public void deleteById(Long id) {
        bandRepository.deleteById(id);
    }

    // Delete all band history of employee
    public void deleteByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        bandRepository.deleteByEmployeeId(employeeId);
    }

    // Mapper
    public EmployeeBandDTO mapToDto(EmployeeBand band) {

        EmployeeBandDTO dto = new EmployeeBandDTO();
        dto.setHistoryId(band.getHistoryId());

        if (band.getEmployee() != null)
            dto.setEmployeeId(band.getEmployee().getEmployeeId());

        dto.setBandLevel(band.getBandLevel());
        dto.setFromDate(band.getFromDate());
        dto.setToDate(band.getToDate());
        dto.setCreatedAt(band.getCreatedAt());
        dto.setUpdatedAt(band.getUpdatedAt());

        return dto;
    }
}
