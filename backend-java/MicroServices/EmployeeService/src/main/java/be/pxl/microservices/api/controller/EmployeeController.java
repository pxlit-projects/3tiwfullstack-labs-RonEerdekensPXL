package be.pxl.microservices.api.controller;

import be.pxl.microservices.api.dto.request.EmployeeRequest;
import be.pxl.microservices.api.dto.response.EmployeeResponse;
import be.pxl.microservices.exception.EmployeeNotFoundException;
import be.pxl.microservices.services.IEmployeeServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private final IEmployeeServices employeeService;


    @GetMapping
    public ResponseEntity getAllEmployees() {
        log.info("Fetching all employees");
        return new ResponseEntity(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        log.info("Fetching employee with id: {}", id);
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);

    }
    @GetMapping("/organization/{organizationId}")
    public ResponseEntity getEmployeeByOrganizationId(@PathVariable Long organizationId) {
        log.info("Fetching employees for organization id: {}", organizationId);
        return new ResponseEntity(employeeService.getEmployeesByOrganizationId(organizationId),HttpStatus.OK);
    }
    @GetMapping("/department/{departmentId}")
    public ResponseEntity getEmployeeByDepartmentId(@PathVariable Long departmentId) {
        log.info("Fetching employees for department id: {}", departmentId);
        return new ResponseEntity(employeeService.getEmployeesByDepartmentId(departmentId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        log.info("Creating new employee with name: {}", employeeRequest.getName());
        return new ResponseEntity(employeeService.createEmployee(employeeRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        log.info("Updating employee with id: {}", id);
        EmployeeResponse employee = employeeService.updateEmployee(id, employeeRequest);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee with id: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
