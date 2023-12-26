package com.junho.productmgnt.domains.user;

import com.junho.productmgnt.domains.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
