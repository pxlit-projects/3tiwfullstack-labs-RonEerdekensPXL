package be.pxl.microservices.services;

import be.pxl.microservices.api.request.DepartmentRequest;
import be.pxl.microservices.api.response.DepartmentResponse;
import be.pxl.microservices.api.response.DepartmentWithEmployeesResponse;
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

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream().map(DepartmentResponse::new).toList();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(DepartmentResponse::new).orElseThrow(() -> new DepartmentNotFoundException("Department with " + id + " not found"));
    }

    @Override
    public List<DepartmentResponse> getDepartmentsByOrganisationId(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId).stream().map(DepartmentResponse::new).toList();
    }

    @Override
    public List<DepartmentWithEmployeesResponse> getDepartmentsByOrganisationIdWithEmployees(Long organizationId) {
        return departmentRepository.findByOrganizationId(organizationId).stream().map(DepartmentWithEmployeesResponse::new).toList();

    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {
        Department department = new Department(departmentRequest);
        department = departmentRepository.save(department);
        return new DepartmentResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department with " + id + " not found"));
        department.setName(departmentRequest.getName());
        department.setPosition(departmentRequest.getPosition());
        department.setOrganizationId(departmentRequest.getOrganizationId());
        departmentRepository.save(department);
        return new DepartmentResponse(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department with " + id + " not found"));
        departmentRepository.deleteById(id);
    }
}
