package be.pxl.microservices.api.response;

import be.pxl.microservices.domain.Department;
import be.pxl.microservices.domain.Employee;
import jakarta.persistence.OneToMany;

import java.util.List;

public class DepartmentResponse {

    private Long id;

    private Long organizationId;
    private String name;
    private String position;

    public DepartmentResponse(Long id, Long organizationId, String name, String position) {
        this.id = id;
        this.organizationId = organizationId;
        this.name = name;
        this.position = position;
    }

    public DepartmentResponse(Department department) {
        this.id = department.getId();
        this.organizationId = department.getOrganizationId();
        this.name = department.getName();
        this.position = department.getPosition();
    }

    public DepartmentResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
