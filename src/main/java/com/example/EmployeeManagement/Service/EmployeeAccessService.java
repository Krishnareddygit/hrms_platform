package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.Exception.AccessDeniedException;
import com.example.security.constants.RoleConstants;
import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeAccessService {

    private final UserRepository userRepository;


    private User getLoggedInUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    private boolean isHrOrAdmin(User user) {
        return user.getRoles().stream().anyMatch(role ->
                role.getName().equals(RoleConstants.ROLE_HR_OPERATIONS) ||
                        role.getName().equals(RoleConstants.ROLE_ADMIN)
        );
    }


    public void checkOwnerOrHr(Long requestedEmployeeId) {

        User user = getLoggedInUser();

        boolean isOwner =
                user.getEmployeeId() != null &&
                        user.getEmployeeId().equals(requestedEmployeeId);

        if (!isOwner && !isHrOrAdmin(user)) {
            throw new AccessDeniedException(
                    "You are not allowed to access this employee data");
        }
    }


    public void checkHrOrAdmin() {

        User user = getLoggedInUser();

        if (!isHrOrAdmin(user)) {
            throw new AccessDeniedException(
                    "You are not allowed to perform this action");
        }
    }

    private boolean isEmployee(User user) {
        return user.getRoles().stream().anyMatch(role ->
                role.getName().equals(RoleConstants.ROLE_EMPLOYEE)
        );
    }


    public void checkManagerAccess(Long managerId) {

        User user = getLoggedInUser();

        // HR / ADMIN → full access
        if (isHrOrAdmin(user)) {
            return;
        }

        // EMPLOYEE → only their own subordinates
        if (isEmployee(user)) {

            if (user.getEmployeeId() == null ||
                    !user.getEmployeeId().equals(managerId)) {

                throw new AccessDeniedException(
                        "You are not allowed to view employees under this manager"
                );
            }
            return;
        }
        throw new AccessDeniedException("Access denied");
    }
}
