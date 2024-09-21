package be.pxl.microservices.api.request;

import be.pxl.microservices.domain.Department;
import be.pxl.microservices.domain.Employee;
import jakarta.persistence.OneToMany;

import java.util.List;

public class DepartmentRequest {

    private Long organizationId;
    private String name;
    private String position;

    public DepartmentRequest(Long organizationId, String name, String position) {
        this.organizationId = organizationId;
        this.name = name;
        this.position = position;
    }

    public DepartmentRequest(Department department) {
        this.organizationId = department.getOrganizationId();
        this.name = department.getName();
        this.position = department.getPosition();
    }

    public DepartmentRequest() {}


    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
