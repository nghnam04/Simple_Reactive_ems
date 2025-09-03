package vn.edu.hust.reactive_ems.controller;

import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.edu.hust.reactive_ems.dto.EmployeeDto;
import vn.edu.hust.reactive_ems.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.addEmployee(employeeDto);
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployeeById(@PathVariable String id){
        return employeeService.getEmployee(id);
    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
    public Mono<EmployeeDto> updateEmployee(@PathVariable String id, @RequestBody EmployeeDto employeeDto){
        return employeeService.updateEmployee(employeeDto, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteEmployee(@PathVariable String id){
        return employeeService.deleteEmployee(id);
    }
}
