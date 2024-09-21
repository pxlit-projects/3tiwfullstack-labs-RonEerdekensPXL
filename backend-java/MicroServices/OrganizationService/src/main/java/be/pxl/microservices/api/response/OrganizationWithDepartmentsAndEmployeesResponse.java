package be.pxl.microservices.api.response;

import be.pxl.microservices.domain.Department;
import be.pxl.microservices.domain.Employee;
import be.pxl.microservices.domain.Organization;

import java.util.List;

public class OrganizationWithDepartmentsAndEmployeesResponse {
    private Long id;

    private String name;
    private  String address;
    private List<Department> departments;
    private List<Employee> employees;

    public OrganizationWithDepartmentsAndEmployeesResponse(Long id, String name, String address, List<Department> departments, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.departments = departments;
        this.employees = employees;

    }

    public OrganizationWithDepartmentsAndEmployeesResponse(Organization organization) {
        this.id = organization.getId();
        this.name = organization.getName();
        this.address = organization.getAddress();
        this.departments = organization.getDepartments();
        this.employees = organization.getEmployees();
    }

    public OrganizationWithDepartmentsAndEmployeesResponse() {}

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
