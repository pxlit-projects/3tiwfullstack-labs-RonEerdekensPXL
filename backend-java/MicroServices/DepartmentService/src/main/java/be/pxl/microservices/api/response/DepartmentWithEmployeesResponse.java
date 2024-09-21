package be.pxl.microservices.api.response;

import be.pxl.microservices.domain.Department;
import be.pxl.microservices.domain.Employee;

import java.util.List;

public class DepartmentWithEmployeesResponse {
    private Long id;

    private Long organizationId;
    private String name;
    private List<Employee> employees;
    private String position;

    public DepartmentWithEmployeesResponse(Long id, Long organizationId, String name, List<Employee> employees, String position) {
        this.id = id;
        this.organizationId = organizationId;
        this.name = name;
        this.employees = employees;
        this.position = position;
    }

    public DepartmentWithEmployeesResponse(Department department) {
        this.id = department.getId();
        this.organizationId = department.getOrganizationId();
        this.name = department.getName();
        this.employees = department.getEmployees();
        this.position = department.getPosition();
    }

    public DepartmentWithEmployeesResponse() {}

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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
