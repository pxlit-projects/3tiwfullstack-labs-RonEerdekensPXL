package be.pxl.microservices.api.controller;

import be.pxl.microservices.api.dto.request.DepartmentRequest;
import be.pxl.microservices.api.dto.response.DepartmentResponse;
import be.pxl.microservices.exception.DepartmentNotFoundException;
import be.pxl.microservices.exception.EmployeeNotFoundException;
import be.pxl.microservices.services.IDepartmentServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {
    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);

    private final IDepartmentServices departmentService;

    @GetMapping
    public ResponseEntity getAllDepartments() {
        log.info("Fetching all departments");
        return new ResponseEntity(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {
        log.info("Fetching department with id: {}", id);
        DepartmentResponse department = departmentService.getDepartmentById(id);
        return new ResponseEntity(department, HttpStatus.OK);

    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity findDepartmentByOrganizationId(@PathVariable Long organizationId) {
        log.info("Fetching departments for organization id: {}", organizationId);
        return new ResponseEntity(departmentService.getDepartmentsByOrganisationId(organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/with-employees")
    public ResponseEntity findDepartmentByOrganizationIdWithEmployees(@PathVariable Long organizationId) {
        log.info("Fetching departments with employees for organization id: {}", organizationId);
        return new ResponseEntity(departmentService.getDepartmentsByOrganisationIdWithEmployees(organizationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createDepartment(@RequestBody DepartmentRequest departmentRequest) {
        log.info("Creating new department with name: {}", departmentRequest.getName());
        return new ResponseEntity(departmentService.createDepartment(departmentRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequest departmentRequest) {
        log.info("Updating department with id: {}", id);
        DepartmentResponse department = departmentService.updateDepartment(id, departmentRequest);
        return ResponseEntity.ok(department);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Long id) {
        log.info("Deleting department with id: {}", id);
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();

    }

}
