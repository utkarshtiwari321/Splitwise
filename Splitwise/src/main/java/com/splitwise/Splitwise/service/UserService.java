package com.splitwise.Splitwise.service;

import com.splitwise.Splitwise.dto.*;
import com.splitwise.Splitwise.exception.*;
import com.splitwise.Splitwise.model.*;
import com.splitwise.Splitwise.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        User savedUser = userRepository.save(user);

        UserDTO savedUserDTO = new UserDTO();
        savedUserDTO.setId(savedUser.getId());
        savedUserDTO.setName(savedUser.getName());
        savedUserDTO.setEmail(savedUser.getEmail());
        savedUserDTO.setPhoneNumber(savedUser.getPhoneNumber());

        return savedUserDTO;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        return userDTO;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(user.getId());
                    userDTO.setName(user.getName());
                    userDTO.setEmail(user.getEmail());
                    userDTO.setPhoneNumber(user.getPhoneNumber());
                    return userDTO;
                })
                .collect(Collectors.toList());
    }
}