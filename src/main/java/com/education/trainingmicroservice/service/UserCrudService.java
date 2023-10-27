package com.education.trainingmicroservice.service;

import com.api.dto.User;
import com.education.trainingmicroservice.error.UserNotFoundException;
import com.education.trainingmicroservice.mapper.UserMapper;
import com.education.trainingmicroservice.model.UserEntity;
import com.education.trainingmicroservice.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCrudService {

    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;

    public Optional<com.api.dto.User> getUserById(Long userId) {
        return userEntityRepository.findById(userId).map(userMapper::entityToDto);
    }

    @Transactional
    public Optional<com.api.dto.User> createUser(User user) {
        UserEntity entity = userMapper.dtoToEntity(user);
        entity.setId(null);
        UserEntity savedEntity = userEntityRepository.save(entity);
        return Optional.of(userMapper.entityToDto(savedEntity));
    }

    @Transactional
    public Optional<User> updateUser(User user) {
        return userEntityRepository.findById(user.getId())
                .map(foundUserEntity -> {
                    userMapper.updateTargetFromSource(user, foundUserEntity);
                    return userMapper.entityToDto(foundUserEntity);
                });
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userEntityRepository.existsById(userId))
            throw new UserNotFoundException("User with id " + userId + " not found");
        userEntityRepository.deleteById(userId);
    }

}
