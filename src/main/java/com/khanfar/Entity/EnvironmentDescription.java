package com.khanfar.Entity;

public class EnvironmentDescription {

    private String clientName ;
    private String containerName;



    public EnvironmentDescription() {
    }


    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }



    public EnvironmentDescription(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
