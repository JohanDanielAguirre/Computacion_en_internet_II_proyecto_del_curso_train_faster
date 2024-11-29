package org.example.trainfaster.configs;

import org.mapstruct.factory.Mappers;
import org.example.trainfaster.mapper.UsersMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public UsersMapper userMapper() {
        return Mappers.getMapper(UsersMapper.class);
    }

}
