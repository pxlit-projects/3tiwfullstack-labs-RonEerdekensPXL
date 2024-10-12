package be.pxl.microservices.client;

import be.pxl.microservices.api.dto.response.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "EMPLOYEE-SERVICE")
public interface EmployeeClient {

    @GetMapping("api/employee/department/{departmentId}")
    List<EmployeeResponse> getEmployeeByDepartmentId(@PathVariable Long departmentId);
}
