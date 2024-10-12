package be.pxl.microservices.client;

import be.pxl.microservices.api.dto.response.EmployeeResponse;
import be.pxl.microservices.domain.Employee;
import jakarta.ws.rs.Path;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "EMPLOYEE-SERVICE")
public interface EmployeeClient {
    @GetMapping("api/employee/organization/{organizationId}")
    List<EmployeeResponse> getEmployeeByOrganizationId(@PathVariable Long organizationId);
}
