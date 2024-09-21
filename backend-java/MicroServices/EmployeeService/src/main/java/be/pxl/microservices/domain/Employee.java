package be.pxl.microservices.domain;

import be.pxl.microservices.api.request.EmployeeRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long organizationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;


    public Employee(EmployeeRequest employeeRequest) {
        this.organizationId = employeeRequest.getOrganizationId();
        this.departmentId = employeeRequest.getDepartmentId();
        this.name = employeeRequest.getName();
        this.age = employeeRequest.getAge();
        this.position = employeeRequest.getPosition();
    }
}
