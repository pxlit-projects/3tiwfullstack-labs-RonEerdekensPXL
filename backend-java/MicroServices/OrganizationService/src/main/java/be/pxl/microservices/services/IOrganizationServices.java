package be.pxl.microservices.services;

import be.pxl.microservices.api.request.OrganizationRequest;
import be.pxl.microservices.api.response.OrganizationResponse;
import be.pxl.microservices.api.response.OrganizationWithDepartmentsAndEmployeesResponse;
import be.pxl.microservices.api.response.OrganizationWithDepartmentsResponse;
import be.pxl.microservices.api.response.OrganizationWithEmployeesResponse;

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
