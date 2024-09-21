package be.pxl.microservices.services;

import be.pxl.microservices.api.request.DepartmentRequest;
import be.pxl.microservices.api.response.DepartmentResponse;
import be.pxl.microservices.api.response.DepartmentWithEmployeesResponse;

import java.util.List;

public interface IDepartmentServices {
    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> getDepartmentsByOrganisationId(Long organizationId);

    List<DepartmentWithEmployeesResponse> getDepartmentsByOrganisationIdWithEmployees(Long organizationId);

    DepartmentResponse createDepartment(DepartmentRequest departmentRequest);

    DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest);

    void deleteDepartment(Long id);
}
