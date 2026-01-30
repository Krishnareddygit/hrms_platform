package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.EmployeePersonalDTO;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeePersonalExistsException;
import com.example.EmployeeManagement.Exception.EmployeePersonalNotFoundException;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmployeePersonal;
import com.example.EmployeeManagement.Repository.EmployeePersonalRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeePersonalService {

    private EmployeePersonalRepository employeePersonalRepository;

    private EmployeeRepository employeeRepository;

    public List<EmployeePersonalDTO> getAllEmployeesPersonal(){
        List<EmployeePersonal> employeePersonals = employeePersonalRepository.findAll();
        return employeePersonals.stream()
                .map(this::mapToDto)
                .toList();
    }

    //    gets employee personal by employee personal id
    public EmployeePersonalDTO getEmployeePersonalById(Long id){
        EmployeePersonal employeePersonal = employeePersonalRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return mapToDto(employeePersonal);
    }

    //    gets employee personal by employee id
    public EmployeePersonalDTO getEmployeePersonalByEmployeeId(Long id){

        Employee employeeDTO = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        EmployeePersonal employeePersonal = employeePersonalRepository.getByEmployeeId(id)
                .orElseThrow(() -> new EmployeePersonalNotFoundException(id));
//        if(employeePersonal.getPersonalId() == null){
//            throw new EmployeeNotFoundException(id);
//        }
        return mapToDto(employeePersonal);
    }

    public EmployeePersonalDTO addEmployeePersonalDetails(Long employeeId , EmployeePersonal employeePersonal){

        if(employeePersonalRepository.getByEmployeeId(employeeId).isPresent()){
            throw new EmployeePersonalExistsException(employeeId);
        }
        Employee employee = employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeePersonal.setEmployee(employee);

        return mapToDto(employeePersonalRepository.save(employeePersonal));
    }

    //    deletes employee personal by employee personal id
    public void deleteEmployeePersonalById(Long id){
        employeePersonalRepository.deleteById(id);

    }

    //    deletes employee personal by employee personal id
    public void deleteEmployeeByEmployeeId(Long id){
        employeePersonalRepository.deleteByEmployeeId(id);
    }
    public EmployeePersonalDTO mapToDto(EmployeePersonal employeePersonal){
        EmployeePersonalDTO dto = new EmployeePersonalDTO();
        dto.setPersonalId(employeePersonal.getPersonalId());
        if(employeePersonal.getEmployee().getEmployeeId() != null)
            dto.setEmployeeId(employeePersonal.getEmployee().getEmployeeId());
        dto.setDob(employeePersonal.getDob());
        dto.setGender(employeePersonal.getGender());
        dto.setBloodGroup(employeePersonal.getBloodGroup());
        dto.setNationality(employeePersonal.getNationality());
        dto.setMaritalStatus(employeePersonal.getMaritalStatus());  // Either single or married

        if ((employeePersonal.getMaritalStatus()).equalsIgnoreCase("married")) {
            dto.setSpouseName(employeePersonal.getSpouseName());
        } else {
            dto.setFatherName(employeePersonal.getFatherName());
        }
        dto.setPersonalMail(employeePersonal.getPersonalMail());
        dto.setAlternatePhoneNumber(employeePersonal.getAlternatePhoneNumber());

        return dto;
    }
}
