package com.khanfar.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.khanfar.DTO.EnvironmentDTO;
import com.khanfar.Entity.EnvironmentDescription;
import com.khanfar.config.MyConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@ApplicationScoped
public class DockerService {

    @Inject
    MyConfiguration myConfiguration;
    @Inject
     NotificationService notificationService ;

    DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    DockerClient dockerClient;

    private ExecutorService executorService = Executors.newFixedThreadPool(1) ;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    public static int lastPort ;

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

    public void createClientEnvironment(EnvironmentDTO environmentDescription) throws InterruptedException, IOException {
        lastPort = myConfiguration.getLastPort();

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

        HostConfig hostConfig = new HostConfig()
                .withCpuCount(environmentDescription.getCpuCore())
                .withMemory(environmentDescription.getMemorySize())
                ;


        String hostPath = this.myConfiguration.getHostPath()+environmentDescription.getLabelName()+"_Backup";

      String containerPath = myConfiguration.getContainerPath();
        Path path = Paths.get(hostPath);

        if (!Files.exists(Path.of(hostPath))) {
            Files.createDirectories(path);
        }
        dockerClient.createVolumeCmd().withName(environmentDescription.getLabelName()+myConfiguration.getVolumePrefix()).exec();


        Bind volumeBind1 = new Bind(hostPath, new Volume(containerPath));
        Bind volumeBind2 = new Bind(environmentDescription.getLabelName()+myConfiguration.getVolumePrefix(), new Volume(myConfiguration.getDatabasePath()));


        // Ports portBindings = new Ports();
       // portBindings.bind(ExposedPort.tcp(3306), Ports.Binding.empty());
        dockerClient.createNetworkCmd().withName(environmentDescription.getLabelName()+myConfiguration.getNetworkPrefix()).exec();

        CreateContainerResponse dbContainer = dockerClient.createContainerCmd(myConfiguration.getDatabaseImageName())
                .withName(environmentDescription.getLabelName() + myConfiguration.getDatabasePrefix())
              //  .withEnv("MYSQL_DATABASE="+myConfiguration.getDatabaseName(), "MYSQL_ROOT_PASSWORD="+myConfiguration.getDatabasePassword())
                 .withEnv("ORACLE_SID="+myConfiguration.getDatabaseName(), "ORACLE_PWD="+myConfiguration.getDatabasePassword() ,"ORACLE_PDB=ORCLPDB1")
                // .withPortBindings(portBindings)
               .withBinds(volumeBind1 , volumeBind2 )
                .withHostConfig(hostConfig)
                //.withMemory(512 * 1000 * 1000l)
                .withExposedPorts(new ExposedPort(myConfiguration.getDatabaseExposedPort()))
                .exec();

        dockerClient.connectToNetworkCmd()
                .withContainerId(dbContainer.getId())
                .withNetworkId(environmentDescription.getLabelName()+myConfiguration.getNetworkPrefix())
                .exec();

        dockerClient.startContainerCmd(dbContainer.getId()).exec();

        int currentPort = ++lastPort;

        myConfiguration.setLastPort(lastPort);

        CreateContainerResponse serviceContainer = dockerClient.createContainerCmd(myConfiguration.getServiceImageName())
                .withName(environmentDescription.getLabelName() + myConfiguration.getServicePrefix())
                .withEnv("DATABASE_HOST=" + environmentDescription.getLabelName() + myConfiguration.getDatabasePrefix()) //
                .withPortBindings(PortBinding.parse(currentPort + ":"+myConfiguration.getServicePort()))
                .withBinds( volumeBind2 )
                .withHostConfig(hostConfig)


                .exec();

        dockerClient.startContainerCmd(serviceContainer.getId()).exec();
        //Thread.sleep(10000);
       // dockerClient.startContainerCmd(serviceContainer.getId()).exec();

        dockerClient.connectToNetworkCmd()
                .withContainerId(serviceContainer.getId())
                .withNetworkId(environmentDescription.getLabelName()+myConfiguration.getNetworkPrefix())
                .exec();

        scheduledExecutorService.schedule(() -> sendNotificationAfterSetUp(environmentDescription), 15, TimeUnit.MINUTES);


    }

    public boolean stopContainer(EnvironmentDTO environmentDescription) {

        dockerClient.stopContainerCmd(environmentDescription.getEnvID()).exec();
        return true ;

    }

    public boolean startContainer(EnvironmentDTO environmentDescription) {
        dockerClient.startContainerCmd(environmentDescription.getEnvID()).exec();
        return true;
    }

    public boolean deleteContainer(EnvironmentDTO environmentDescription) {
        dockerClient.removeContainerCmd(environmentDescription.getEnvID()).exec();
        dockerClient.removeVolumeCmd(environmentDescription.getEnvID()+myConfiguration.getVolumePrefix()).exec();
        dockerClient.removeNetworkCmd(environmentDescription.getEnvID()+myConfiguration.getNetworkPrefix()).exec();
        return true ;
    }

    public Container getContainerByName(String containerName) {
        List<Container> containerList = dockerClient.listContainersCmd().exec();
        for (Container container : containerList) {
            if (container.getId().equalsIgnoreCase(containerName)){
                return container;
            }
        }
        return null;
    }

    public Object getAllContainers() {
        return     dockerClient.listContainersCmd().exec();

    }

    public Object getStatus(String containerName) {
        List<Container> containerList = dockerClient.listContainersCmd().exec();
        for (Container container : containerList) {
            if (container.getId().equalsIgnoreCase(containerName)){
                return container.getStatus();
            }
        }
        return null;
    }

    public Object getState(String containerName) {
        List<Container> containerList = dockerClient.listContainersCmd().exec();
        for (Container container : containerList) {
            if (container.getId().equalsIgnoreCase(containerName)){
                return container.getState();
            }
        }
        return null;
    }

    public Object getAllNetworks() {
        return dockerClient.listNetworksCmd().exec();
    }

    public Object getAllVolumes() {
        return dockerClient.listVolumesCmd().exec();
    }

    private void sendNotificationAfterSetUp( EnvironmentDTO environmentDescription) {
        executorService.submit(() -> {

        });
    }

    public void notifyContainerEvent(EnvironmentDescription environmentDescription) {
        String apiUrl ="77http://hotsms.ps/sendbulksms.php?user_name=RamaLogisic&user_pass=test&sender=SMS&mobile=97259000000&type=0&text=Welcome . your envirment "+environmentDescription.getLabelName()+"its created and its done ";
        notificationService.sendNotification(apiUrl);

        // ... Rest of the method ...
    }

    public void shutdown() {
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }
}
