package com.khanfar.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class User_EnvDTO {
    private String user_name ;
    private Set<EnvironmentDTO> environmentDTOS ;


}
