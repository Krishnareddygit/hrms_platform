package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.DTO.AccountDTO;
import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.EmployeeManagement.Exception.DataNotFoundException;
import com.example.EmployeeManagement.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagement.Model.Account;
import com.example.EmployeeManagement.Model.Employee;
import com.example.EmployeeManagement.Repository.AccountRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountRepository accountRepository;
    private EmployeeRepository employeeRepository;

    // Get account by accountId
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Account not found with id: " + id));
        return mapToDto(account);
    }

    // Get account of employee
    public AccountDTO getAccountByEmployeeId(Long employeeId) {
        Account account = accountRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new DataNotFoundException("Account not found for employee: " + employeeId));
        return mapToDto(account);
    }

    // Add account (One-to-One)
    public AccountDTO addAccount(Long employeeId, Account account) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        // Prevent duplicate account
        if (accountRepository.findByEmployeeId(employeeId).isPresent()) {
            throw new DataNotFoundException("Employee already has an account");
        }

        account.setEmployee(employee);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());

        return mapToDto(accountRepository.save(account));
    }

    // Partial update
    @Transactional
    public AccountDTO updateAccount(Long accountId, Long employeeId, AccountDTO dto) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new DataNotFoundException("Account not found with id: " + accountId));

        // Security check
        if (!account.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new AccessDeniedException("Employee is not allowed to modify this account");
        }

        if (dto.getAccountNumber() != null)
            account.setAccountNumber(dto.getAccountNumber());

        if (dto.getBankName() != null)
            account.setBankName(dto.getBankName());

        if (dto.getBranchName() != null)
            account.setBranchName(dto.getBranchName());

        if (dto.getIfscCode() != null)
            account.setIfscCode(dto.getIfscCode());

        if (dto.getAccountType() != null)
            account.setAccountType(dto.getAccountType());

        account.setUpdatedAt(LocalDateTime.now());

        return mapToDto(account);
    }

    // Delete account of employee
    public void deleteByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        accountRepository.deleteByEmployeeId(employeeId);
    }

    // Mapper
    public AccountDTO mapToDto(Account account) {

        AccountDTO dto = new AccountDTO();
        dto.setAccountId(account.getAccountId());

        if (account.getEmployee() != null)
            dto.setEmployeeId(account.getEmployee().getEmployeeId());

        dto.setAccountNumber(account.getAccountNumber());
        dto.setBankName(account.getBankName());
        dto.setBranchName(account.getBranchName());
        dto.setIfscCode(account.getIfscCode());
        dto.setAccountType(account.getAccountType());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());

        return dto;
    }
}
