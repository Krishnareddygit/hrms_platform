package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.EmployeeDTO;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeAccessService employeeAccessService;

    public List<EmployeeDTO> getAllEmployee(){
        employeeAccessService.checkHrOrAdmin();
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public EmployeeDTO getEmployeeById(Long id){

        employeeAccessService.checkOwnerOrHr(id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return mapToDto(employee);
    }

    public List<EmployeeDTO> getEmployeeByName(String name){
        return employeeRepository.searchByFullName(name)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public EmployeeDTO addEmployee(Employee employee){
        Employee employeeSaved =  employeeRepository.save(employee);
        return mapToDto(employeeSaved);
    }

    public EmployeeDTO updateEmployee(Long id, Employee updated){
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setCompanyEmail(updated.getCompanyEmail());
        existing.setDateOfJoining(updated.getDateOfJoining());
        existing.setStatus(updated.getStatus());
        existing.setEmployeeType(updated.getEmployeeType());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setCurrentBand(updated.getCurrentBand());
        existing.setCurrentExperience(updated.getCurrentExperience());
        existing.setDesignation(updated.getDesignation());
        existing.setCtc(updated.getCtc());

        Employee saved = employeeRepository.save(existing);
        return mapToDto(saved);
    }

    public void deleteEmployeeById(Long id){
        employeeRepository.deleteById(id);
    }


    public EmployeeDTO mapToDto(Employee employee){
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setCompanyEmail(employee.getCompanyEmail());
        dto.setDesignation(employee.getDesignation());
        dto.setStatus(employee.getStatus());
        dto.setCurrentBand(employee.getCurrentBand());

        if(employee.getJobDetails() != null)
            dto.setDepartment(employee.getJobDetails().getDepartmentName());
        if(employee.getManager()!=null)
            dto.setManagerName(employee.getManager().getFirstName()+" "+employee.getManager().getLastName());

        return dto;
    }

    public List<EmployeeDTO> getEmployeesUnderManager(Long managerId) {

        // HR can view anyone, EMPLOYEE only their own subordinates
        employeeAccessService.checkManagerAccess(managerId);

        return employeeRepository.findByManager_EmployeeId(managerId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public Employee toEntity(EmployeeDTO dto, Long hrUserId) {
        Employee e = new Employee();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setCompanyEmail(dto.getCompanyEmail());
        e.setDateOfJoining(dto.getDateOfJoining());
        e.setStatus(dto.getStatus());
        e.setEmployeeType(dto.getEmployeeType());
        e.setPhoneNumber(dto.getPhoneNumber());
        e.setCurrentBand(dto.getCurrentBand());
        e.setCurrentExperience(dto.getCurrentExperience());
        e.setDesignation(dto.getDesignation());
        e.setCtc(dto.getCtc());
        e.setCreatedByHrUserId(hrUserId);
        return e;
    }



}
