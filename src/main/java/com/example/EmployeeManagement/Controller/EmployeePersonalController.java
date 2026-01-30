

package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeDTO;
import com.example.EmployeeManagement.DTO.EmployeePersonalDTO;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmployeePersonal;
import com.example.EmployeeManagement.Service.EmployeePersonalService;
import com.example.EmployeeManagement.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1/hrms/employees")
@AllArgsConstructor
public class EmployeePersonalController {
    private EmployeePersonalService employeePersonalService;

//    get all personal details of employees
    @GetMapping("/personal-details")
    public ResponseEntity<List<EmployeePersonalDTO>> getAllEmployeePersonal(){
        List<EmployeePersonalDTO> empDto = employeePersonalService.getAllEmployeesPersonal();
        return ResponseEntity.ok(empDto);
    }

//get each personal details by personal details id
    @GetMapping("/personal-details/{id}")
    public ResponseEntity<EmployeePersonalDTO> getEmployeesPersonalById(@PathVariable("id") Long id){
        EmployeePersonalDTO empDto = employeePersonalService.getEmployeePersonalById(id);
        return ResponseEntity.ok(empDto);
    }

// get employee personal details by employee id
    @GetMapping("/{id}/personal")
    public ResponseEntity<EmployeePersonalDTO> getEmployeesPersonalByEmployeeId(@PathVariable("id") Long id){
        EmployeePersonalDTO empDto = employeePersonalService.getEmployeePersonalByEmployeeId(id);
        return ResponseEntity.ok(empDto);
    }

//    add employee personal details using employee
    @PostMapping("/{employeeId}/add-personal")
    public ResponseEntity<EmployeePersonalDTO> addEmployeePersonalDetails(@PathVariable("employeeId") Long employeeId , @RequestBody EmployeePersonal employeePersonal){
        EmployeePersonalDTO empDto = employeePersonalService.addEmployeePersonalDetails(employeeId,employeePersonal);
        return ResponseEntity.ok(empDto);
    }

    @DeleteMapping("/personal-details/{id}")
    public ResponseEntity<String> deleteEmployeePersonalById(@PathVariable("id") Long id){
        employeePersonalService.deleteEmployeePersonalById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}

