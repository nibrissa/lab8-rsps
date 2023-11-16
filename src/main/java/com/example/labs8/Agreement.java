package com.example.labs8;

public class Agreement {
    String number,client,type,status,dateopen,dateclose;

    public Agreement(String number, String client, String type, String status, String dateopen, String dateclose) {
        this.number = number;
        this.client = client;
        this.type = type;
        this.status = status;
        this.dateopen = dateopen;
        this.dateclose = dateclose;
    }

    public Agreement() {}

    public String getNumber() {return number;}
    public String getClient() {return client;}
    public String getType() {return type;}
    public String getStatus() {return status;}
    public String getDateopen() {return dateopen;}
    public String getDateclose() {return dateclose;}
    public void setNumber(String number) {this.number = number;}
    public void setClient(String client) {this.client = client;}
    public void setType(String type) {this.type = type;}
    public void setStatus(String status) {this.status = status;}
    public void setDateopen(String dateopen) {this.dateopen = dateopen;}
    public void setDateclose(String dateclose) {this.dateclose = dateclose;}
}
