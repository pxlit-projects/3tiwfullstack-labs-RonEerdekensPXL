package be.pxl.microservices.api.request;

import be.pxl.microservices.domain.Organization;

public class OrganizationRequest {
    private String name;
    private  String address;
    public OrganizationRequest(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public OrganizationRequest(Organization organization) {
        this.name = organization.getName();
        this.address = organization.getAddress();
    }

    public OrganizationRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
