package be.pxl.microservices.services;

import be.pxl.microservices.api.request.EmployeeRequest;
import be.pxl.microservices.api.response.EmployeeResponse;
import be.pxl.microservices.domain.Employee;

import java.util.List;

public interface IEmployeeServices {

    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse createEmployee(EmployeeRequest employee);

    List<EmployeeResponse> getEmployeesByOrganizationId(Long organizationId);

    List<EmployeeResponse> getEmployeesByDepartmentId(Long departmentId);


    EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest);

    void deleteEmployee(Long id);
}
