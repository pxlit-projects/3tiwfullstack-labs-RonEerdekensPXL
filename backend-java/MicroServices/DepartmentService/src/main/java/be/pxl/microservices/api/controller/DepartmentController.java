package be.pxl.microservices.api.controller;

import be.pxl.microservices.api.dto.request.DepartmentRequest;
import be.pxl.microservices.api.dto.response.DepartmentResponse;
import be.pxl.microservices.exception.DepartmentNotFoundException;
import be.pxl.microservices.exception.EmployeeNotFoundException;
import be.pxl.microservices.services.IDepartmentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final IDepartmentServices departmentService;

    @GetMapping
    public ResponseEntity getAllDepartments() {
        return new ResponseEntity(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {

        DepartmentResponse department = departmentService.getDepartmentById(id);
        return new ResponseEntity(department, HttpStatus.OK);

    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity findDepartmentByOrganizationId(@PathVariable Long organizationId) {
        return new ResponseEntity(departmentService.getDepartmentsByOrganisationId(organizationId), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}/with-employees")
    public ResponseEntity findDepartmentByOrganizationIdWithEmployees(@PathVariable Long organizationId) {
        return new ResponseEntity(departmentService.getDepartmentsByOrganisationIdWithEmployees(organizationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createDepartment(@RequestBody DepartmentRequest departmentRequest) {
        return new ResponseEntity(departmentService.createDepartment(departmentRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequest departmentRequest) {

        DepartmentResponse department = departmentService.updateDepartment(id, departmentRequest);
        return ResponseEntity.ok(department);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Long id) {

        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();

    }

}
