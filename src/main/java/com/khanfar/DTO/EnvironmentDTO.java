package com.khanfar.DTO;


import lombok.Data;

@Data
public class EnvironmentDTO {


    private String envID ;
    private String labelName;
    private Long cpuCore ;
    private Long memorySize ;

}
