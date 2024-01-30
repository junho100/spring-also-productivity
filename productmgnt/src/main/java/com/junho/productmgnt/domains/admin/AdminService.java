package com.junho.productmgnt.domains.admin;

import static com.junho.productmgnt.common.exception.BaseExceptionStatus.ADMIN_ALREADY_EXISTS;
import static com.junho.productmgnt.common.exception.BaseExceptionStatus.ADMIN_NOT_FOUND;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.domains.admin.entity.Admin;
import com.junho.productmgnt.domains.admin.model.command.CreateAdminCommand;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public Long createAdmin(CreateAdminCommand createAdminCommand){
        Optional<Admin> admin = adminRepository.findByEmail(createAdminCommand.getEmail());
        if (admin.isPresent()) {
            throw new BaseException(ADMIN_ALREADY_EXISTS);
        }
        Admin createdAdmin = adminRepository.save(Admin.builder().email(createAdminCommand.getEmail()).passwordHash(createAdminCommand.getPassword()).build());
        return createdAdmin.getAdminId();
    }

    public Admin getAdminByEmail(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if(!admin.isPresent()){
            throw new BaseException(ADMIN_NOT_FOUND);
        }
        return admin.get();
    }
}
