package com.khanfar.DTO;

import lombok.Data;

import java.util.Date;


@Data
public class User_EnvDTO {
    private Long user_id ;
    private String env_id ;
    private Date date ;

}
