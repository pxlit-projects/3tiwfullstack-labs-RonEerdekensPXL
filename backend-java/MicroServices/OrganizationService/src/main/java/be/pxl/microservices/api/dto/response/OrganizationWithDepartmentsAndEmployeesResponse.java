package be.pxl.microservices.api.dto.response;

import be.pxl.microservices.domain.Department;
import be.pxl.microservices.domain.Employee;
import be.pxl.microservices.domain.Organization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationWithDepartmentsAndEmployeesResponse {
    private Long id;

    private String name;
    private  String address;
    private List<DepartmentResponse> departments;
    private List<EmployeeResponse> employees;


}
