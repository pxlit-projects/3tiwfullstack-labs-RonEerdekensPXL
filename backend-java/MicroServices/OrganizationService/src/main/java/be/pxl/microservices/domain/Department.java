package be.pxl.microservices.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {


    private Long id;

    private Long organizationId;
    private String name;

    @Transient // vluchtig slaat niet op
    private List<Employee> employees;
    private String position;


}
