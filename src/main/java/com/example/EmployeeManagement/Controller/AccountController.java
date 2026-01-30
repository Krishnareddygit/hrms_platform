package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.DTO.AccountDTO;
import com.example.EmployeeManagement.Model.Account;
import com.example.EmployeeManagement.Service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // Get account by ID
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDTO> getByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    // Get account of employee
    @GetMapping("/{employeeId}/account")
    public ResponseEntity<AccountDTO> getByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(accountService.getAccountByEmployeeId(employeeId));
    }

    // Add account
    @PostMapping("/{employeeId}/account")
    public ResponseEntity<AccountDTO> addAccount(
            @PathVariable Long employeeId,
            @RequestBody Account account) {

        return ResponseEntity.ok(accountService.addAccount(employeeId, account));
    }

    // Update account
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
    @DeleteMapping("/{employeeId}/account")
    public ResponseEntity<Void> deleteByEmployee(@PathVariable Long employeeId) {
        accountService.deleteByEmployeeId(employeeId);
        return ResponseEntity.noContent().build();
    }
}
