package be.pxl.microservices.services;

import be.pxl.microservices.api.request.OrganizationRequest;
import be.pxl.microservices.api.response.OrganizationResponse;
import be.pxl.microservices.api.response.OrganizationWithDepartmentsAndEmployeesResponse;
import be.pxl.microservices.api.response.OrganizationWithDepartmentsResponse;
import be.pxl.microservices.api.response.OrganizationWithEmployeesResponse;
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

    @Override
    public List<OrganizationResponse> getAllOrganizations() {
        return organizationRepository.findAll().stream().map(OrganizationResponse::new).toList();
    }

    @Override
    public OrganizationResponse getOrganizationById(Long id) {

        return organizationRepository.findById(id).map(OrganizationResponse::new).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
    }

    @Override
    public OrganizationWithDepartmentsResponse getOrganizationByIdWithDepartments(Long id) {

        return organizationRepository.findById(id).map(OrganizationWithDepartmentsResponse::new).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
    }

    @Override
    public OrganizationWithDepartmentsAndEmployeesResponse getOrganizationByIdWithDepartmentsAndEmployees(Long id) {
        return organizationRepository.findById(id).map(OrganizationWithDepartmentsAndEmployeesResponse::new).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
    }

    @Override
    public OrganizationWithEmployeesResponse getOrganizationByIdWithEmployees(Long id) {

        return organizationRepository.findById(id).map(OrganizationWithEmployeesResponse::new).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));

    }

    @Override
    public OrganizationResponse createOrganization(OrganizationRequest organizationRequest) {

        Organization organization = Organization.builder()
                .name(organizationRequest.getName())
                .address(organizationRequest.getAddress())
                .build();
        organization = organizationRepository.save(organization);
        return new OrganizationResponse(organization);
    }

    @Override
    public OrganizationResponse updateOrganization(Long id, OrganizationRequest organizationRequest) {

        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
        organization.setName(organizationRequest.getName());
        organization.setAddress(organizationRequest.getAddress());
        organization = organizationRepository.save(organization);
        return new OrganizationResponse(organization);
    }

    @Override
    public void deleteDepartment(Long id) {
        organizationRepository.findById(id).orElseThrow(() -> new OrganizationNotFoundException("Organization with " + id + " not found"));
        organizationRepository.deleteById(id);
    }
}
