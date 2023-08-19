package com.khanfar.Service;

import com.khanfar.DTO.EnvironmentDTO;
import com.khanfar.Entity.EnvironmentDescription;
import com.khanfar.Entity.User_EnvEntity;
import com.khanfar.Repository.EnvironmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.cfg.Environment;

import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class EnvironmentService {

    @Inject
    EnvironmentRepository environmentRepository ;


    @Transactional
    public List<EnvironmentDTO> fetchAllContainers() {

        List<EnvironmentDescription> list = environmentRepository.findAll().stream().toList() ;
        List<EnvironmentDTO> list2 = new LinkedList<>();

        for (EnvironmentDescription environmentDescription : list ) {
            list2.add(convertToDTO(environmentDescription));
           // list.remove(environmentDescription);
        }
      return list2;
    }


    @Transactional
    public EnvironmentDTO fetchEnvironmentByName(String envName) {

        EnvironmentDTO environmentDTO = convertToDTO(environmentRepository.fetchEnvironmentByName(envName)) ;
        return environmentDTO;
    }


@Transactional
    public Object insert(EnvironmentDTO environmentDTO) {

        EnvironmentDescription environmentDescription = convertToEntity(environmentDTO) ;

        environmentRepository.persist(environmentDescription);
        return environmentDTO ;

    }


    private EnvironmentDTO convertToDTO(EnvironmentDescription fetchEnvironmentByName) {

        EnvironmentDTO environmentDTO = new EnvironmentDTO() ;
        environmentDTO.setContainerName(fetchEnvironmentByName.getContainerName());
        return environmentDTO ;
    }
    private EnvironmentDescription convertToEntity(EnvironmentDTO environmentDTO) {

        EnvironmentDescription description = new EnvironmentDescription() ;
        description.setContainerName(environmentDTO.getContainerName());
        return description ;
    }
}
