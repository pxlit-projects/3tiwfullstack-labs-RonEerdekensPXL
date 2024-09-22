package be.pxl.microservices.services;

import be.pxl.microservices.api.dto.request.OrganizationRequest;
import be.pxl.microservices.api.dto.response.OrganizationResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsAndEmployeesResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithEmployeesResponse;
import be.pxl.microservices.domain.Organization;
import be.pxl.microservices.exception.OrganizationNotFoundException;
import be.pxl.microservices.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServices implements IOrganizationServices {

    private final OrganizationRepository organizationRepository;

    private OrganizationResponse mapToOrganizationResponse(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .build();
    }

    private OrganizationWithDepartmentsAndEmployeesResponse mapToOrganizationWithDepartmentsAndEmployees(Organization organization) {
        return OrganizationWithDepartmentsAndEmployeesResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .departments(organization.getDepartments())
                .employees(organization.getEmployees())
                .build();
    }
    private OrganizationWithDepartmentsResponse mapToOrganizationWithDepartments(Organization organization) {
        return OrganizationWithDepartmentsResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .departments(organization.getDepartments())
                .build();
    }
    private OrganizationWithEmployeesResponse mapToOrganizationWithEmployees(Organization organization) {
        return OrganizationWithEmployeesResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .address(organization.getAddress())
                .employees(organization.getEmployees())
                .build();
    }

    @Override
    public List<OrganizationResponse> getAllOrganizations() {
        return organizationRepository.findAll().stream().map(this::mapToOrganizationResponse).toList();
    }

    @Override
    public OrganizationResponse getOrganizationById(Long id) {

        return organizationRepository.findById(id).map(this::mapToOrganizationResponse).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
    }

    @Override
    public OrganizationWithDepartmentsResponse getOrganizationByIdWithDepartments(Long id) {

        return organizationRepository.findById(id).map(this::mapToOrganizationWithDepartments).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
    }

    @Override
    public OrganizationWithDepartmentsAndEmployeesResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id) {
        return organizationRepository.findById(id).map(this::mapToOrganizationWithDepartmentsAndEmployees).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
    }

    @Override
    public OrganizationWithEmployeesResponse getOrganizationByIdWithEmployees(Long id) {

        return organizationRepository.findById(id).map(this::mapToOrganizationWithEmployees).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));

    }

    @Override
    public OrganizationResponse createOrganization(OrganizationRequest organizationRequest) {

        Organization organization = Organization.builder()
                .name(organizationRequest.getName())
                .address(organizationRequest.getAddress())
                .build();
        organization = organizationRepository.save(organization);
        return mapToOrganizationResponse(organization);
    }

    @Override
    public OrganizationResponse updateOrganization(Long id, OrganizationRequest organizationRequest) {

        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
        organization.setName(organizationRequest.getName());
        organization.setAddress(organizationRequest.getAddress());
        organization = organizationRepository.save(organization);
        return mapToOrganizationResponse(organization);
    }

    @Override
    public void deleteDepartment(Long id) {
        organizationRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
        organizationRepository.deleteById(id);
    }
}
