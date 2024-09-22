package be.pxl.microservices.api.controller;

import be.pxl.microservices.api.dto.request.EmployeeRequest;
import be.pxl.microservices.api.dto.response.EmployeeResponse;
import be.pxl.microservices.exception.EmployeeNotFoundException;
import be.pxl.microservices.services.IEmployeeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeServices employeeService;


    @GetMapping
    public ResponseEntity getAllEmployees() {
        return new ResponseEntity(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {

        try {
            EmployeeResponse employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        }catch (EmployeeNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

    }
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity getEmployeeByOrganizationId(@PathVariable Long organizationId) {
        return new ResponseEntity(employeeService.getEmployeesByOrganizationId(organizationId),HttpStatus.OK);
    }
    @GetMapping("/department/{departmentId}")
    public ResponseEntity getEmployeeByDepartmentId(@PathVariable Long departmentId) {
        return new ResponseEntity(employeeService.getEmployeesByDepartmentId(departmentId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return new ResponseEntity(employeeService.createEmployee(employeeRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        try {
            EmployeeResponse employee = employeeService.updateEmployee(id, employeeRequest);
            return ResponseEntity.ok(employee);
        }catch (EmployeeNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().build();
        }catch (EmployeeNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
