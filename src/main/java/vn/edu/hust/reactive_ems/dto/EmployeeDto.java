package vn.edu.hust.reactive_ems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;

@Getter
@Scope
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
