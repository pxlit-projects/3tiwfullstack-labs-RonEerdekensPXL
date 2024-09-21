package be.pxl.microservices.api.controller;

import be.pxl.microservices.api.request.DepartmentRequest;
import be.pxl.microservices.api.request.EmployeeRequest;
import be.pxl.microservices.api.response.DepartmentResponse;
import be.pxl.microservices.api.response.EmployeeResponse;
import be.pxl.microservices.domain.Department;
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
        try {
            DepartmentResponse department = departmentService.getDepartmentById(id);
            return new ResponseEntity(department, HttpStatus.OK);
        }catch (DepartmentNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        try {
            DepartmentResponse department = departmentService.updateDepartment(id, departmentRequest);
            return ResponseEntity.ok(department);
        }catch (EmployeeNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok().build();
        }catch (DepartmentNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
