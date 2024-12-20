package be.pxl.microservices.services;

import be.pxl.microservices.api.controller.DepartmentController;
import be.pxl.microservices.api.dto.request.DepartmentRequest;
import be.pxl.microservices.api.dto.response.DepartmentResponse;
import be.pxl.microservices.api.dto.response.DepartmentWithEmployeesResponse;
import be.pxl.microservices.api.dto.response.EmployeeResponse;
import be.pxl.microservices.client.EmployeeClient;
import be.pxl.microservices.domain.Department;
import be.pxl.microservices.domain.Employee;
import be.pxl.microservices.exception.DepartmentNotFoundException;
import be.pxl.microservices.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServices implements IDepartmentServices{

    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentRepository departmentRepository;
    private final EmployeeClient employeeClient;

    private DepartmentResponse mapToDepartmentResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                .build();
    }
    private DepartmentWithEmployeesResponse mapToDepartmentWithEmployeesResponse(Department department) {
        return DepartmentWithEmployeesResponse.builder()
                .id(department.getId())
                .organizationId(department.getOrganizationId())
                .name(department.getName())
                .position(department.getPosition())
                // Null check on getEmployees()
                .employees(Optional.ofNullable(department.getEmployees())
                        .orElse(Collections.emptyList()) // Return an empty list if null
                        .stream()
                        .map(this::mapToEmployeeResponse)
                        .toList())
                .build();
    }
    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .age(employee.getAge())
                .position(employee.getPosition())
                .departmentId(employee.getDepartmentId())
                .organizationId(employee.getOrganizationId())
                .build();
    }


    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll().stream().map(this::mapToDepartmentResponse).toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(this::mapToDepartmentResponse).orElseThrow(() -> new DepartmentNotFoundException("Department with " + id + " not found"));
    }

    @Override
    public List<DepartmentResponse> getDepartmentsByOrganisationId(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId).stream().map(this::mapToDepartmentResponse).toList();
    }

    @Override
    public List<DepartmentWithEmployeesResponse> getDepartmentsByOrganisationIdWithEmployees(Long organizationId) {

        List<DepartmentWithEmployeesResponse> departments = departmentRepository.findByOrganizationId(organizationId).stream().map(this::mapToDepartmentWithEmployeesResponse).toList();
        departments.forEach(department -> department.setEmployees(employeeClient.getEmployeeByDepartmentId(department.getId())));

        return departments;

    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .name(departmentRequest.getName())
                .organizationId(departmentRequest.getOrganizationId())
                .position(departmentRequest.getPosition()).build();

        department = departmentRepository.save(department);
        log.info("Department with id: {} created", department.getId());
        return mapToDepartmentResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department with " + id + " not found"));
        department.setName(departmentRequest.getName());
        department.setPosition(departmentRequest.getPosition());
        department.setOrganizationId(departmentRequest.getOrganizationId());
        departmentRepository.save(department);
        log.info("Department with id: {} updated", id);
        return mapToDepartmentResponse(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department with " + id + " not found"));
        departmentRepository.deleteById(id);
        log.info("Department with id: {} deleted", id);
    }
}
