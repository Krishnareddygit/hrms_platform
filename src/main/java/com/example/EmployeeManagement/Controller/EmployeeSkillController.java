package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.EmployeeSkillDTO;
import com.example.EmployeeManagement.Model.EmployeeSkill;
import com.example.EmployeeManagement.Service.EmployeeSkillService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeSkillController {

    private final EmployeeSkillService employeeSkillService;

    // Get all skills (all employees)
    @GetMapping("/skills")
    public ResponseEntity<List<EmployeeSkillDTO>> getAllSkills() {
        return ResponseEntity.ok(employeeSkillService.getAllSkills());
    }

    // Get skill by skillId
    @GetMapping("/skills/{skillId}")
    public ResponseEntity<EmployeeSkillDTO> getBySkillId(@PathVariable Long skillId) {
        return ResponseEntity.ok(employeeSkillService.getSkillById(skillId));
    }

    // Get all skills of an employee
    @GetMapping("/{employeeId}/skills")
    public ResponseEntity<List<EmployeeSkillDTO>> getByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeSkillService.getSkillsByEmployeeId(employeeId));
    }

    // Add skill to employee
    @PostMapping("/{employeeId}/add-skills")
    public ResponseEntity<EmployeeSkillDTO> addSkill(
            @PathVariable Long employeeId,
            @RequestBody EmployeeSkill skill) {

        return ResponseEntity.ok(employeeSkillService.addSkill(employeeId, skill));
    }

    // Update skill (partial update)
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
    @DeleteMapping("/skills/{skillId}")
    public ResponseEntity<Void> deleteBySkillId(@PathVariable Long skillId) {
        employeeSkillService.deleteBySkillId(skillId);
        return ResponseEntity.noContent().build();
    }

    // Delete all skills of an employee
    @DeleteMapping("/{employeeId}/skills")
    public ResponseEntity<Void> deleteByEmployeeId(@PathVariable Long employeeId) {
        employeeSkillService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
