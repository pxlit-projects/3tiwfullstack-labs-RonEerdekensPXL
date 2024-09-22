package be.pxl.microservices.services;

import be.pxl.microservices.api.dto.request.DepartmentRequest;
import be.pxl.microservices.api.dto.response.DepartmentResponse;
import be.pxl.microservices.api.dto.response.DepartmentWithEmployeesResponse;
import be.pxl.microservices.domain.Department;
import be.pxl.microservices.exception.DepartmentNotFoundException;
import be.pxl.microservices.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServices implements IDepartmentServices{

    private final DepartmentRepository departmentRepository;

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
                .employees(department.getEmployees())
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
        return departmentRepository.findByOrganizationId(organizationId).stream().map(this::mapToDepartmentWithEmployeesResponse).toList();

    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .name(departmentRequest.getName())
                .organizationId(departmentRequest.getOrganizationId())
                .position(departmentRequest.getPosition()).build();

        department = departmentRepository.save(department);
        return mapToDepartmentResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department with " + id + " not found"));
        department.setName(departmentRequest.getName());
        department.setPosition(departmentRequest.getPosition());
        department.setOrganizationId(departmentRequest.getOrganizationId());
        departmentRepository.save(department);
        return mapToDepartmentResponse(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department with " + id + " not found"));
        departmentRepository.deleteById(id);
    }
}
