package be.pxl.microservices.api.controller;

import be.pxl.microservices.api.dto.request.OrganizationRequest;
import be.pxl.microservices.api.dto.response.OrganizationResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsAndEmployeesResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithEmployeesResponse;
import be.pxl.microservices.exception.OrganizationNotFoundException;
import be.pxl.microservices.services.IOrganizationServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);
    private final IOrganizationServices organizationServices;

    @GetMapping
    public ResponseEntity getAllOrganizations() {
        log.info("Fetching all organizations");
        return new ResponseEntity(organizationServices.getAllOrganizations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> findOrganizationById(@PathVariable Long id) {
        log.info("Fetching organization with id: {}", id);
        OrganizationResponse organizationResponse = organizationServices.getOrganizationById(id);
        return new ResponseEntity(organizationResponse, HttpStatus.OK);

    }

    @GetMapping("/{id}/with-departments")
    public ResponseEntity<OrganizationWithDepartmentsResponse> findOrganizationByIdWithDepartments(@PathVariable Long id) {
        log.info("Fetching organization with id include departments: {}", id);
        OrganizationWithDepartmentsResponse organizationResponse = organizationServices.getOrganizationByIdWithDepartments(id);
        return new ResponseEntity(organizationResponse, HttpStatus.OK);

    }

    @GetMapping("/{id}/with-departments-and-employees")
    public ResponseEntity<OrganizationWithDepartmentsAndEmployeesResponse> findOrganizationByIdWithDepartmentsAndEmployees(@PathVariable Long id) {
        log.info("Fetching organization with id include departments and employees: {}", id);
        OrganizationWithDepartmentsAndEmployeesResponse organizationResponse = organizationServices.getOrganizationByIdWithDepartmentsAndEmployees(id);
        return new ResponseEntity(organizationResponse, HttpStatus.OK);

    }

    @GetMapping("/{id}/with-employees")
    public ResponseEntity<OrganizationWithEmployeesResponse> findOrganizationByIdWithEmployees(@PathVariable Long id) {
        log.info("Fetching organization with id include employees: {}", id);
        OrganizationWithEmployeesResponse organizationResponse = organizationServices.getOrganizationByIdWithEmployees(id);
        return new ResponseEntity(organizationResponse, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity createOrganization(@RequestBody OrganizationRequest organizationRequest) {
        log.info("Creating new organization with name: {}", organizationRequest.getName());
        return new ResponseEntity(organizationServices.createOrganization(organizationRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateOrganization(@PathVariable Long id, @RequestBody OrganizationRequest organizationRequest) {
        log.info("Updating organization with id: {}", id);
        OrganizationResponse organizationResponse = organizationServices.updateOrganization(id, organizationRequest);
        return new ResponseEntity(organizationResponse, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrganization(@PathVariable Long id) {
        log.info("Deleting organization with id: {}", id);
        organizationServices.deleteDepartment(id);
        return new ResponseEntity(HttpStatus.OK);

    }

}
