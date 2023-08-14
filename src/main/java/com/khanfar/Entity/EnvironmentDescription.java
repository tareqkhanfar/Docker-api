package com.khanfar.Entity;

public class EnvironmentDescription {

    private String clientName ;


    public EnvironmentDescription() {
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
