package com.blog.api.services;

import com.blog.api.entities.User;
import com.blog.api.payloads.UserDto;
import com.blog.api.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
        //        return null;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user  = this.userRepo.findById(userId).orElseThrow();
        user.setPassword(userDto.getPassword());
        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setAbout(user.getAbout());

        return this.userToDto(user);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow();
        return this.userToDto(user);

    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos=users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow();
        this.userRepo.delete(user);
    }



    public User dtoToUser(UserDto userDto){
        User user = new User();
        user.setId((Integer) userDto.getId());
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
    public UserDto userToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId((Integer) user.getId());
        userDto.setName(user.getName());
        userDto.setAbout(user.getAbout());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
