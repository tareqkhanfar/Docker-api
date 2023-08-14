package com.khanfar.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
@ApplicationScoped

public class MyConfiguration {

    public MyConfiguration(){

    }


    @ConfigProperty(name = "dockerServer.database.url")
    String databaseUrl;

    @ConfigProperty(name = "dockerServer.database.name")
    String databaseName;

    @ConfigProperty(name = "dockerServer.database.user")
    String databaseUser;

    @ConfigProperty(name = "dockerServer.database.password")
    String databasePassword;

    @ConfigProperty(name = "dockerServer.database.prefix")
    String databasePrefix;

    @ConfigProperty(name = "dockerServer.network.prefix")
    String networkPrefix;

    @ConfigProperty(name = "dockerServer.service.prefix")
    String servicePrefix;

    @ConfigProperty(name = "dockerServer.volume.prefix")
    String volumePrefix;

    @ConfigProperty(name = "dockerServer.container.path")
    String containerPath;


    @ConfigProperty(name = "dockerServer.host.path")
    String hostPath;

    @ConfigProperty(name = "dockerServer.database.path")
    String databasePath;

    @ConfigProperty(name = "dockerServer.database.image.name")
    String databaseImageName;

    @ConfigProperty(name = "dockerServer.service.image.name")
    String ServiceImageName;


    @ConfigProperty(name = "dockerServer.database.ExposedPort")
    Integer databaseExposedPort;

    @ConfigProperty(name = "dockerServer.service.port")
    Integer servicePort;

    public Integer getServicePort() {
        return servicePort;
    }

    public void setServicePort(Integer servicePort) {
        this.servicePort = servicePort;
    }

    public String getDatabaseImageName() {
        return databaseImageName;
    }

    public void setDatabaseImageName(String databaseImageName) {
        this.databaseImageName = databaseImageName;
    }

    public String getServiceImageName() {
        return ServiceImageName;
    }

    public void setServiceImageName(String serviceImageName) {
        ServiceImageName = serviceImageName;
    }

    public Integer getDatabaseExposedPort() {
        return databaseExposedPort;
    }

    public void setDatabaseExposedPort(Integer databaseExposedPort) {
        this.databaseExposedPort = databaseExposedPort;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public String getContainerPath() {
        return containerPath;
    }

    public void setContainerPath(String containerPath) {
        this.containerPath = containerPath;
    }

    public String getHostPath() {
        return hostPath;
    }

    public void setHostPath(String hostPath) {
        this.hostPath = hostPath;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabasePrefix() {
        return databasePrefix;
    }

    public void setDatabasePrefix(String databasePrefix) {
        this.databasePrefix = databasePrefix;
    }

    public String getNetworkPrefix() {
        return networkPrefix;
    }

    public void setNetworkPrefix(String networkPrefix) {
        this.networkPrefix = networkPrefix;
    }

    public String getServicePrefix() {
        return servicePrefix;
    }

    public void setServicePrefix(String servicePrefix) {
        this.servicePrefix = servicePrefix;
    }

    public String getVolumePrefix() {
        return volumePrefix;
    }

    public void setVolumePrefix(String volumePrefix) {
        this.volumePrefix = volumePrefix;
    }
}
