package com.regi.backend.service;

import com.regi.backend.entity.User;
import com.regi.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public List<User> getListData() {
        return userRepository.findAll();
    }

    public User getDataDetail(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public void deleted(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }

    public User update(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User with id " + id + " not found");
        }
    }

    public boolean validateLogin(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        return user.map(value -> value.getPassword().equals(password)).orElse(false);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

}
