package com.education.trainingmicroservice.mapper;

import com.api.dto.User;
import com.education.trainingmicroservice.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    User entityToDto(UserEntity userEntity);

    UserEntity dtoToEntity(User user);

    void updateTargetFromSource(User user, @MappingTarget UserEntity targetUserEntity);
}
