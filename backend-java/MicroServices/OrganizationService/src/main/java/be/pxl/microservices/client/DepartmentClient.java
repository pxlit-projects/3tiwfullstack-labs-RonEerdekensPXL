package be.pxl.microservices.client;

import be.pxl.microservices.api.dto.response.DepartmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface DepartmentClient {

    @GetMapping("api/department/organization/{organizationId}")
    List<DepartmentResponse> getDepartmentByOrganizationId(@PathVariable Long organizationId);
}
