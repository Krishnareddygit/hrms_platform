package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeSkillDTO;
import com.example.EmployeeManagement.Model.EmployeeSkill;
import com.example.EmployeeManagement.Service.EmployeeSkillService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeSkillController {

    private final EmployeeSkillService employeeSkillService;

    // Get all skills (all employees)
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/skills")
    public ResponseEntity<List<EmployeeSkillDTO>> getAllSkills() {
        return ResponseEntity.ok(employeeSkillService.getAllSkills());
    }

    // Get skill by skillId
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @GetMapping("/skills/{skillId}")
    public ResponseEntity<EmployeeSkillDTO> getBySkillId(@PathVariable Long skillId) {
        return ResponseEntity.ok(employeeSkillService.getSkillById(skillId));
    }

    // Get all skills of an employee
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','TALENT_ACQUISITION','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @GetMapping("/{employeeId}/skills")
    public ResponseEntity<List<EmployeeSkillDTO>> getByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeSkillService.getSkillsByEmployeeId(employeeId));
    }

    // Add skill to employee
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','TALENT_ACQUISITION','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PostMapping("/{employeeId}/add-skills")
    public ResponseEntity<EmployeeSkillDTO> addSkill(
            @PathVariable Long employeeId,
            @RequestBody EmployeeSkill skill) {

        return ResponseEntity.ok(employeeSkillService.addSkill(employeeId, skill));
    }

    // Update skill (partial update)
    @PreAuthorize("""
        hasAnyRole('HR_OPERATIONS','TALENT_ACQUISITION','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PutMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<EmployeeSkillDTO> updateSkill(
            @PathVariable Long employeeId,
            @PathVariable Long skillId,
            @RequestBody EmployeeSkillDTO dto) {

        return ResponseEntity.ok(
                employeeSkillService.updateSkill(skillId, employeeId, dto)
        );
    }

    // Delete one skill
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/skills/{skillId}")
    public ResponseEntity<Void> deleteBySkillId(@PathVariable Long skillId) {
        employeeSkillService.deleteBySkillId(skillId);
        return ResponseEntity.noContent().build();
    }

    // Delete all skills of an employee
    @PreAuthorize("hasAnyRole('HR_OPERATIONS','ADMIN')")
    @DeleteMapping("/{employeeId}/skills")
    public ResponseEntity<Void> deleteByEmployeeId(@PathVariable Long employeeId) {
        employeeSkillService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
