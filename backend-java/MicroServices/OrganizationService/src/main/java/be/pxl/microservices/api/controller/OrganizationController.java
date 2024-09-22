package be.pxl.microservices.api.controller;

import be.pxl.microservices.api.dto.request.OrganizationRequest;
import be.pxl.microservices.api.dto.response.OrganizationResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsAndEmployeesResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithDepartmentsResponse;
import be.pxl.microservices.api.dto.response.OrganizationWithEmployeesResponse;
import be.pxl.microservices.exception.OrganizationNotFoundException;
import be.pxl.microservices.services.IOrganizationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {

    private final IOrganizationServices organizationServices;

    @GetMapping
    public ResponseEntity getAllOrganizations() {
        return new ResponseEntity(organizationServices.getAllOrganizations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> findOrganizationById(@PathVariable Long id) {
        try {
            OrganizationResponse organizationResponse = organizationServices.getOrganizationById(id);
            return new ResponseEntity(organizationResponse, HttpStatus.OK);
        }catch (OrganizationNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}/with-departments")
    public ResponseEntity<OrganizationWithDepartmentsResponse> findOrganizationByIdWithDepartments(@PathVariable Long id) {
        try {
            OrganizationWithDepartmentsResponse organizationResponse = organizationServices.getOrganizationByIdWithDepartments(id);
            return new ResponseEntity(organizationResponse, HttpStatus.OK);
        }catch (OrganizationNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}/with-departments-and-employees")
    public ResponseEntity<OrganizationWithDepartmentsAndEmployeesResponse> findOrganizationByIdWithDepartmentsAndEmployees(@PathVariable Long id) {
        try {
            OrganizationWithDepartmentsAndEmployeesResponse organizationResponse = organizationServices.getOrganizationByIdWithDepartmentsAndEmployees(id);
            return new ResponseEntity(organizationResponse, HttpStatus.OK);
        }catch (OrganizationNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/with-employees")
    public ResponseEntity<OrganizationWithEmployeesResponse> findOrganizationByIdWithEmployees(@PathVariable Long id) {
        try {
            OrganizationWithEmployeesResponse organizationResponse = organizationServices.getOrganizationByIdWithEmployees(id);
            return new ResponseEntity(organizationResponse, HttpStatus.OK);
        }catch (OrganizationNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity createOrganization(@RequestBody OrganizationRequest organizationRequest) {
        return new ResponseEntity(organizationServices.createOrganization(organizationRequest), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity updateOrganization(@PathVariable Long id, @RequestBody OrganizationRequest organizationRequest) {
        try {
            OrganizationResponse organizationResponse = organizationServices.updateOrganization(id,organizationRequest);
            return new ResponseEntity(organizationResponse, HttpStatus.OK);
        }catch (OrganizationNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrganization(@PathVariable Long id) {
        try {
            organizationServices.deleteDepartment(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (OrganizationNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
