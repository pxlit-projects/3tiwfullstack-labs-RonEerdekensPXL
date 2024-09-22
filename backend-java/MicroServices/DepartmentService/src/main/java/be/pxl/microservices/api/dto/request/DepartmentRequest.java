package be.pxl.microservices.api.dto.request;

import be.pxl.microservices.domain.Department;
import be.pxl.microservices.domain.Employee;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {

    private Long organizationId;
    private String name;
    private String position;


}
