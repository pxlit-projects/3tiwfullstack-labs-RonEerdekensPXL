package be.pxl.microservices.services;

import be.pxl.microservices.api.dto.request.OrganizationRequest;
import be.pxl.microservices.api.dto.response.OrganizationResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsAndEmployeesResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithEmployeesResponse;

import java.util.List;

public interface IOrganizationServices {
    List<OrganizationResponse> getAllOrganizations();

    OrganizationResponse getOrganizationById(Long id);

    OrganizationWithDepartmentsResponse getOrganizationByIdWithDepartments(Long id);

    OrganizationWithDepartmentsAndEmployeesResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id);

    OrganizationWithEmployeesResponse getOrganizationByIdWithEmployees(Long id);

    OrganizationResponse createOrganization(OrganizationRequest organizationRequest);

    OrganizationResponse updateOrganization(Long id, OrganizationRequest organizationRequest);

    void deleteDepartment(Long id);
}
