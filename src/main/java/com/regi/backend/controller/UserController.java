package com.regi.backend.controller;

import com.regi.backend.dto.UserDTO;
import com.regi.backend.entity.User;
import com.regi.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO create(@RequestBody User user) {
        User createdUser = userService.create(user);
        return convertToDTO(createdUser);
    }

    @GetMapping
    public List<UserDTO> getListUsers() {
        List<User> users = userService.getListData();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getDetailUser(@PathVariable("id") Long id) {
        User user = userService.getDataDetail(id);
        return convertToDTO(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleted(id);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable("id") Long id, @RequestBody User user) {
        User updated = userService.update(id, user);
        return convertToDTO(updated);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
