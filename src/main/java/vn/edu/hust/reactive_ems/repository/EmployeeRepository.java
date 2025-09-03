package vn.edu.hust.reactive_ems.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import vn.edu.hust.reactive_ems.entity.Employee;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {
}
