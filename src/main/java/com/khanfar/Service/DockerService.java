package com.khanfar.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.khanfar.Entity.EnvironmentDescription;
import com.khanfar.config.MyConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;


@ApplicationScoped
public class DockerService {

    @Inject
    MyConfiguration myConfiguration;

    DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    DockerClient dockerClient;
    public static int lastPort = 3999;

    public DockerService() {
        ApacheDockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .sslConfig(dockerClientConfig.getSSLConfig())

                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        dockerClient = DockerClientImpl.getInstance(dockerClientConfig, httpClient);
    }

    public void createClientEnvironment(EnvironmentDescription environmentDescription) throws InterruptedException, IOException {

        System.out.println("Database URL: " + myConfiguration.getDatabaseUrl());
        System.out.println("Database Name: " + myConfiguration.getDatabaseName());
        System.out.println("Database User: " + myConfiguration.getDatabaseUser());
        System.out.println("Database Password: " + myConfiguration.getDatabasePassword());
        System.out.println("Database Prefix: " + myConfiguration.getDatabasePrefix());
        System.out.println("Network Prefix: " + myConfiguration.getNetworkPrefix());
        System.out.println("Service Prefix: " + myConfiguration.getServicePrefix());
        System.out.println("Volume Prefix: " + myConfiguration.getVolumePrefix());
        System.out.println("Host Path: " + myConfiguration.getHostPath());
        System.out.println("Container Path: " + myConfiguration.getContainerPath());
        System.out.println("Database Exposed Port: " + myConfiguration.getDatabaseExposedPort());
        System.out.println("Database Path: " + myConfiguration.getDatabasePath());
        System.out.println("Service Image Name: " + myConfiguration.getServiceImageName());
        System.out.println("Database Image Name: " + myConfiguration.getDatabaseImageName());
        System.out.println("Service Port: " + myConfiguration.getServicePort());

      String hostPath = this.myConfiguration.getHostPath()+environmentDescription.getClientName()+"_Backup";

      String containerPath = myConfiguration.getContainerPath();
        Path path = Paths.get(hostPath);

        if (!Files.exists(Path.of(hostPath))) {
            Files.createDirectories(path);
        }
        dockerClient.createVolumeCmd().withName(environmentDescription.getClientName()+myConfiguration.getVolumePrefix()).exec();

        Bind volumeBind1 = new Bind(hostPath, new Volume(containerPath));
        Bind volumeBind2 = new Bind(environmentDescription.getClientName()+myConfiguration.getVolumePrefix(), new Volume(myConfiguration.getDatabasePath()));


        // Ports portBindings = new Ports();
       // portBindings.bind(ExposedPort.tcp(3306), Ports.Binding.empty());
        dockerClient.createNetworkCmd().withName(environmentDescription.getClientName()+myConfiguration.getNetworkPrefix()).exec();

        CreateContainerResponse dbContainer = dockerClient.createContainerCmd(myConfiguration.getDatabaseImageName())
                .withName(environmentDescription.getClientName() + myConfiguration.getDatabasePrefix())
              //  .withEnv("MYSQL_DATABASE="+myConfiguration.getDatabaseName(), "MYSQL_ROOT_PASSWORD="+myConfiguration.getDatabasePassword())
                 .withEnv("ORACLE_SID="+myConfiguration.getDatabaseName(), "ORACLE_PWD="+myConfiguration.getDatabasePassword() ,"ORACLE_PDB=ORCLPDB1")
                // .withPortBindings(portBindings)
               // .withBinds(volumeBind1 , volumeBind2 ).withMemory(512 * 1000 * 1000l)
                .withExposedPorts(new ExposedPort(myConfiguration.getDatabaseExposedPort()))
                .exec();

        dockerClient.connectToNetworkCmd()
                .withContainerId(dbContainer.getId())
                .withNetworkId(environmentDescription.getClientName()+myConfiguration.getNetworkPrefix())
                .exec();

        dockerClient.startContainerCmd(dbContainer.getId()).exec();

        int currentPort = ++lastPort;

        CreateContainerResponse serviceContainer = dockerClient.createContainerCmd(myConfiguration.getServiceImageName())
                .withName(environmentDescription.getClientName() + myConfiguration.getServicePrefix())
                .withEnv("DATABASE_HOST=" + environmentDescription.getClientName() + myConfiguration.getDatabasePrefix()) //
                .withPortBindings(PortBinding.parse(currentPort + ":"+myConfiguration.getServicePort()))
                .withBinds( volumeBind2 )

                .exec();

        dockerClient.startContainerCmd(serviceContainer.getId()).exec();
        //Thread.sleep(10000);
       // dockerClient.startContainerCmd(serviceContainer.getId()).exec();

        dockerClient.connectToNetworkCmd()
                .withContainerId(serviceContainer.getId())
                .withNetworkId(environmentDescription.getClientName()+myConfiguration.getNetworkPrefix())
                .exec();


    }

    public boolean stopContainer(EnvironmentDescription environmentDescription) {

        dockerClient.stopContainerCmd(environmentDescription.getContainerName()).exec();
        return true ;

    }

    public boolean startContainer(EnvironmentDescription environmentDescription) {
        dockerClient.startContainerCmd(environmentDescription.getContainerName()).exec();
        return true;
    }

    public boolean deleteContainer(EnvironmentDescription environmentDescription) {
        dockerClient.removeContainerCmd(environmentDescription.getContainerName()).exec();
        dockerClient.removeVolumeCmd(environmentDescription.getContainerName()+myConfiguration.getVolumePrefix()).exec();
        dockerClient.removeNetworkCmd(environmentDescription.getContainerName()+myConfiguration.getNetworkPrefix()).exec();
        return true ;
    }

}
