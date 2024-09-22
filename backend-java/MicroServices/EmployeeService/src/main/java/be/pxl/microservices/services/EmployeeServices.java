package be.pxl.microservices.services;

import be.pxl.microservices.api.request.EmployeeRequest;
import be.pxl.microservices.api.response.EmployeeResponse;
import be.pxl.microservices.domain.Employee;
import be.pxl.microservices.exception.EmployeeNotFoundException;
import be.pxl.microservices.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServices implements IEmployeeServices {


    private final EmployeeRepository employeeRepository;


    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream().map(EmployeeResponse::new).toList();
    }
    public EmployeeResponse getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(EmployeeResponse::new).orElseThrow(() -> new EmployeeNotFoundException("Employee with " + id + " not found"));
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .age(employeeRequest.getAge())
                .name(employeeRequest.getName())
                .position(employeeRequest.getPosition())
                .departmentId(employeeRequest.getDepartmentId())
                .organizationId(employeeRequest.getOrganizationId())
                .build();
        employee = employeeRepository.save(employee);
        return new EmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getEmployeesByOrganizationId(Long organizationId) {
        return employeeRepository.findByOrganizationId(organizationId).stream().map(EmployeeResponse::new).toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream().map(EmployeeResponse::new).toList();
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with " + id + " not found"));
        employee.setAge(employeeRequest.getAge());
        employee.setName(employeeRequest.getName());
        employee.setPosition(employeeRequest.getPosition());
        employee.setDepartmentId(employeeRequest.getDepartmentId());
        employee.setOrganizationId(employeeRequest.getOrganizationId());
        employeeRepository.save(employee);
        return new EmployeeResponse(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with " + id + " not found"));
        employeeRepository.deleteById(id);
    }


}
