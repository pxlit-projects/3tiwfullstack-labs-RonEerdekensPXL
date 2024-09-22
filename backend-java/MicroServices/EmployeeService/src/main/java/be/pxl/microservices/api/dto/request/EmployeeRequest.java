package be.pxl.microservices.api.dto.request;

import be.pxl.microservices.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private Long organizationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;

}
