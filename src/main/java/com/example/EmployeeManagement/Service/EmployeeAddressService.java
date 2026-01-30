package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.EmployeeAddressDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmployeeAddress;
import com.example.EmployeeManagement.Repository.EmployeeAddressRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeAddressService {

    private EmployeeAddressRepository employeeAddressRepository;

    private EmployeeRepository employeeRepository;

    public List<EmployeeAddressDTO> getAllEmployeesAddress(){
        return employeeAddressRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public EmployeeAddressDTO getEmployeeAddressById(Long id){
        EmployeeAddress address = employeeAddressRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Employee address not found for employee address id: "+id));
        return mapToDto(address);
    }

    public List<EmployeeAddressDTO> getAddressByEmployeeId(Long id){
        Employee emp = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        List<EmployeeAddress> addresses = employeeAddressRepository.findByEmployeeId(id);
        return addresses.stream()
                .map(this::mapToDto)
                .toList();
    }


    public EmployeeAddressDTO addEmployeeAddress(Long employeeId , EmployeeAddress employeeAddress){
            Employee emp = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
            employeeAddress.setEmployee(emp);
            employeeAddress.setCreatedAt(LocalDateTime.now());
            employeeAddress.setUpdatedAt(LocalDateTime.now());

            return mapToDto(employeeAddressRepository.save(employeeAddress));
    }


    @Transactional
    public EmployeeAddressDTO updateEmployeeAddress(
            Long addressId,
            Long employeeId,
            EmployeeAddressDTO dto) {

        EmployeeAddress address = employeeAddressRepository.findById(addressId)
                .orElseThrow(() -> new DataNotFoundException("Address not found for address id: "+addressId));

        // Security check: ensure address belongs to this employee
        if (!address.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not eligible to modify this data. Contact your admin");
        }

        // Partial updates
        if (dto.getHouseNumber() != null)
            address.setHouseNumber(dto.getHouseNumber());

        if (dto.getLandmark() != null)
            address.setLandmark(dto.getLandmark());

        if (dto.getStreet() != null)
            address.setStreet(dto.getStreet());

        if (dto.getCity() != null)
            address.setCity(dto.getCity());

        if (dto.getPincode() != null)
            address.setPincode(dto.getPincode());

        if (dto.getState() != null)
            address.setState(dto.getState());

        if (dto.getCountry() != null)
            address.setCountry(dto.getCountry());

        if (dto.getIsPermanent() != null)
            address.setIsPermanent(dto.getIsPermanent());

        address.setUpdatedAt(LocalDateTime.now());

        return mapToDto(address);
    }


    public void deleteAddressById(Long id){
        employeeAddressRepository.deleteById(id);
    }

    public void deleteAddressByEmployeeId(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeeAddressRepository.deleteByEmployeeId(employeeId);
    }

    public EmployeeAddressDTO mapToDto(EmployeeAddress address){
        EmployeeAddressDTO dto = new EmployeeAddressDTO();
        dto.setAddressId(address.getAddressId());
        if(address.getEmployee().getEmployeeId() != null)
            dto.setEmployeeId(address.getEmployee().getEmployeeId());
        dto.setHouseNumber(address.getHouseNumber());
        dto.setStreet(address.getStreet());
        dto.setLandmark(address.getLandmark());
        dto.setCity(address.getCity());
        dto.setPincode(address.getPincode());
        dto.setState(address.getState());
        dto.setCountry(address.getCountry());
        dto.setIsPermanent(address.getIsPermanent());

        if(address.getCreatedAt() != null)
            dto.setCreatedAt(address.getCreatedAt());

        if(address.getUpdatedAt() != null)
            dto.setUpdatedAt(address.getUpdatedAt());

        return dto;
    }
}

