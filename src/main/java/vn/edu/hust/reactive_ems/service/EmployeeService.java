package vn.edu.hust.reactive_ems.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.edu.hust.reactive_ems.dto.EmployeeDto;
import vn.edu.hust.reactive_ems.entity.Employee;
import vn.edu.hust.reactive_ems.mapper.EmployeeMapper;
import vn.edu.hust.reactive_ems.repository.EmployeeRepository;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository repository;

    public Mono<EmployeeDto> addEmployee(EmployeeDto employeeDto){
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> employeeMono = repository.save(employee);
        return employeeMono
                .map((employeeEntity) -> EmployeeMapper.mapToEmployeeDto(employeeEntity));
    }

    public Mono<EmployeeDto> getEmployee(String id){
        return repository.findById(id)
                .map((employee -> EmployeeMapper.mapToEmployeeDto(employee)));
    }

    public Flux<EmployeeDto> getAllEmployees(){
        return repository.findAll()
                .map((employee -> EmployeeMapper.mapToEmployeeDto(employee)))
                .switchIfEmpty(Flux.empty());
    }

    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String id){
        Mono<Employee> employeeMono = repository.findById(id)
                .flatMap((employee -> {
                    employee.setFirstName(employeeDto.getFirstName());
                    employee.setLastName(employeeDto.getLastName());
                    employee.setEmail(employeeDto.getEmail());

                    return repository.save(employee);
                }));
        return employeeMono.map((existedEmployee) -> EmployeeMapper.mapToEmployeeDto(existedEmployee));
    }

    public Mono<Void> deleteEmployee(String id){
        return repository.deleteById(id);
    }
}
