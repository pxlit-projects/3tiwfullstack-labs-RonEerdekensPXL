package be.pxl.microservices.api.response;

import be.pxl.microservices.domain.Employee;

public class EmployeeResponse {

    private Long id;
    private Long organizationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;

    public EmployeeResponse(Long id, Long organizationId, Long departmentId, String name, int age, String position) {
        this.id = id;
        this.organizationId = organizationId;
        this.departmentId = departmentId;
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.organizationId = employee.getOrganizationId();
        this.departmentId = employee.getDepartmentId();
        this.name = employee.getName();
        this.age = employee.getAge();
        this.position = employee.getPosition();
    }

    public EmployeeResponse() {

    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

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

    public void setFirstName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
