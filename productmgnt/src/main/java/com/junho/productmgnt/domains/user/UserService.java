package com.junho.productmgnt.domains.user;

import com.junho.productmgnt.domains.user.entity.User;
import com.junho.productmgnt.domains.user.model.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public Long createUser(CreateUserCommand createUserCommand) {
        User user = User.builder()
            .username(createUserCommand.getUsername())
            .email(createUserCommand.getEmail())
            .passwordHash(createUserCommand.getPasswordHash())
            .build();

        User savedUser = userRepository.save(user);

        return savedUser.getUserId();
    }
}
