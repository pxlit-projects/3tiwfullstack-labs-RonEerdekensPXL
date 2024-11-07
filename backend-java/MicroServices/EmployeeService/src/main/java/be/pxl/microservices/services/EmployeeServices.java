package be.pxl.microservices.services;

import be.pxl.microservices.api.dto.request.EmployeeRequest;
import be.pxl.microservices.api.dto.request.NotificationRequest;
import be.pxl.microservices.api.dto.response.EmployeeResponse;
import be.pxl.microservices.client.NotificationClient;
import be.pxl.microservices.domain.Employee;
import be.pxl.microservices.exception.EmployeeNotFoundException;
import be.pxl.microservices.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServices implements IEmployeeServices {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServices.class);
    private final EmployeeRepository employeeRepository;
    private final NotificationClient notificationClient;

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .organizationId(employee.getOrganizationId())
                .departmentId(employee.getDepartmentId())
                .name(employee.getName())
                .age(employee.getAge())
                .position(employee.getPosition())
                .build();
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::mapToEmployeeResponse).toList();
    }
    public EmployeeResponse getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(this::mapToEmployeeResponse).orElseThrow(() -> new EmployeeNotFoundException("Employee with " + id + " not found"));
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
        log.info("Employee created with id: {}", employee.getId());

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("Employee Created")
                .from("EmployeeService").to("Ron").subject("Employee Created").build();

        notificationClient.sendNotification(notificationRequest);

        return mapToEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> getEmployeesByOrganizationId(Long organizationId) {
        return employeeRepository.findByOrganizationId(organizationId).stream().map(this::mapToEmployeeResponse).toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream().map(this::mapToEmployeeResponse).toList();
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
        log.info("Employee updated with id: {}", employee.getId());
        return mapToEmployeeResponse(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with " + id + " not found"));
        employeeRepository.deleteById(id);
        log.info("Employee deleted with id: {}", id);
    }


}
