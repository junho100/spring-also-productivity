package com.junho.productmgnt.domains.admin;

import static com.junho.productmgnt.common.exception.BaseExceptionStatus.ADMIN_NOT_FOUND;
import static com.junho.productmgnt.common.exception.BaseExceptionStatus.AUTH_ADMIN_NOT_FOUND;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.domains.admin.entity.Admin;
import com.junho.productmgnt.domains.auth.CustomAdminDetails;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAdminDetailsService implements UserDetailsService{
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isEmpty()) {
            throw new UsernameNotFoundException("admin not found.");
        }
        return CustomAdminDetails.create(admin.get());
    }
}
