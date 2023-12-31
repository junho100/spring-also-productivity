package com.junho.productmgnt.domains.user;

import com.junho.productmgnt.common.exception.BaseException;
import com.junho.productmgnt.common.exception.BaseExceptionStatus;
import com.junho.productmgnt.domains.user.entity.User;
import com.junho.productmgnt.domains.user.model.command.CreateUserCommand;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    public Long createUser(CreateUserCommand createUserCommand) {
        Optional<User> isUserExists = userRepository.findByEmail(createUserCommand.getEmail());
        if (isUserExists.isPresent()) {
            throw new BaseException(BaseExceptionStatus.USER_EXISTS);
        }

        User user = User.builder()
            .username(createUserCommand.getUsername())
            .email(createUserCommand.getEmail())
            .passwordHash(createUserCommand.getPasswordHash())
            .build();

        User savedUser = userRepository.save(user);

        return savedUser.getUserId();
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new BaseException(BaseExceptionStatus.USER_NOT_FOUND);
        }

        return user.get();
    }
}
