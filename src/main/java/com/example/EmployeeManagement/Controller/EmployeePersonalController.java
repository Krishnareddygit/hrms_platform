

package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeDTO;
import com.example.EmployeeManagement.DTO.EmployeePersonalDTO;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Model.EmployeePersonal;
import com.example.EmployeeManagement.Service.EmployeePersonalService;
import com.example.EmployeeManagement.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1/hrms/employees")
@AllArgsConstructor
public class EmployeePersonalController {
    private EmployeePersonalService employeePersonalService;

//    get all personal details of employees
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
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
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#id))
    """)
    @GetMapping("/{id}/personal")
    public ResponseEntity<EmployeePersonalDTO> getEmployeesPersonalByEmployeeId(@PathVariable("id") Long id){
        EmployeePersonalDTO empDto = employeePersonalService.getEmployeePersonalByEmployeeId(id);
        return ResponseEntity.ok(empDto);
    }

//    add employee personal details using employee
    @PreAuthorize("""
        hasRole('HR_OPERATIONS')
        or hasRole('ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PostMapping("/{employeeId}/add-personal")
    public ResponseEntity<EmployeePersonalDTO> addEmployeePersonalDetails(@PathVariable("employeeId") Long employeeId , @RequestBody EmployeePersonal employeePersonal){
        EmployeePersonalDTO empDto = employeePersonalService.addEmployeePersonalDetails(employeeId,employeePersonal);
        return ResponseEntity.ok(empDto);
    }

    @PreAuthorize("""
    hasRole('HR_OPERATIONS')
    or hasRole('ADMIN')
    or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
""")
    @PutMapping("/update/{employeeId}")
    public EmployeePersonalDTO updateEmployeePersonal(
            @PathVariable Long employeeId,
            @RequestBody EmployeePersonalDTO dto
    ) {
        return employeePersonalService.updateEmployeePersonal(employeeId, dto);
    }


    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/personal-details/{id}")
    public ResponseEntity<String> deleteEmployeePersonalById(@PathVariable("id") Long id){
        employeePersonalService.deleteEmployeePersonalById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}

