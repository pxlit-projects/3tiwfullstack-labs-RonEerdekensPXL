package be.pxl.microservices.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Long id;

    private Long organizationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;


}
