package be.pxl.microservices.api.response;

import be.pxl.microservices.domain.Department;
import be.pxl.microservices.domain.Employee;
import be.pxl.microservices.domain.Organization;
import jakarta.persistence.OneToMany;

import java.util.List;

public class OrganizationResponse {
    private Long id;

    private String name;
    private  String address;

    public OrganizationResponse(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;

    }

    public OrganizationResponse(Organization organization) {
        this.id = organization.getId();
        this.name = organization.getName();
        this.address = organization.getAddress();
    }

    public OrganizationResponse() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
