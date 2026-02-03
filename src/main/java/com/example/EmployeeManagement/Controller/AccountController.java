package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.AccountDTO;
import com.example.EmployeeManagement.Model.Account;
import com.example.EmployeeManagement.Service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // Get account by ID
    @PreAuthorize("hasAnyRole('HR_PAYROLL','ADMIN')")
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDTO> getByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    // Get account of employee
    @PreAuthorize("""
        hasAnyRole('HR_PAYROLL','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @GetMapping("/{employeeId}/account")
    public ResponseEntity<AccountDTO> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(accountService.getAccountByEmployeeId(employeeId));
    }

    // Add account
    @PreAuthorize("""
        hasAnyRole('HR_PAYROLL','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PostMapping("/{employeeId}/account")
    public ResponseEntity<AccountDTO> addAccount(
            @PathVariable Long employeeId,
            @RequestBody Account account) {

        return ResponseEntity.ok(accountService.addAccount(employeeId, account));
    }

    // Update account
    @PreAuthorize("""
        hasAnyRole('HR_PAYROLL','ADMIN')
        or (hasRole('EMPLOYEE') and @securityUtil.isSelf(#employeeId))
    """)
    @PutMapping("/{employeeId}/account/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(
            @PathVariable Long employeeId,
            @PathVariable Long accountId,
            @RequestBody AccountDTO dto) {

        return ResponseEntity.ok(
                accountService.updateAccount(accountId, employeeId, dto)
        );
    }

    // Delete account
    @PreAuthorize("hasAnyRole('HR_PAYROLL','ADMIN')")
    @DeleteMapping("/{employeeId}/account")
    public ResponseEntity<Void> deleteByEmployee(@PathVariable Long employeeId) {
        accountService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
