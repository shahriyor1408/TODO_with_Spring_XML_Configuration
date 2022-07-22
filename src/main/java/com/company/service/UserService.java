package com.company.service;

import com.company.domains.Session;
import com.company.domains.Users;
import com.company.enums.UserRole;
import com.company.repository.UserRepository;
import com.company.ui.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class UserService {
    private UserRepository userRepository;

    public Response login(String username, String password) {
        Optional<Users> optionalUser = userRepository.getByUsername(username);
        if (optionalUser.isEmpty()) {
            return new Response<>("User not found!", false);
        }

        Users user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            return new Response<>("Password does not match!", false);
        }
        Session.setSessionUser(user);
        return new Response<>(user);
    }

    public Response register(String username, String password) {

        if (username == null || password == null) {
            return new Response<>("bad credentials!", false);
        }

        Optional<Users> optionalUsers = userRepository.getByUsername(username);
        if (optionalUsers.isPresent()) {
            return new Response<>("User already exist!", false);
        }
        Users users = Users.builder()
                .password(password)
                .username(username)
                .role(UserRole.USER)
                .build();
        Long id = userRepository.save(users);
        return new Response<>(id);
    }

    public Response logout() {
        Session.sessionUser = null;
        return new Response<>("Ok", true);
    }

    public Response showAllUsers() {
        List<Users> users = userRepository.getAll();
        return new Response<>(users);
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {

    }
}
