package be.pxl.microservices.domain;

import be.pxl.microservices.api.request.DepartmentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long organizationId;
    private String name;
    @OneToMany
    private List<Employee> employees;
    private String position;


}
