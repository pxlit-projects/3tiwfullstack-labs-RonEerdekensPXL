package be.pxl.microservices.domain;

import be.pxl.microservices.api.request.OrganizationRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Organization")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private  String address;
    @OneToMany
    private List<Employee> employees;
    @OneToMany
    private List<Department> departments;

}
