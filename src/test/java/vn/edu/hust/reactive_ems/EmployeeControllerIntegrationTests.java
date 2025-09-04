package vn.edu.hust.reactive_ems;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import vn.edu.hust.reactive_ems.dto.EmployeeDto;
import vn.edu.hust.reactive_ems.repository.EmployeeRepository;
import vn.edu.hust.reactive_ems.service.EmployeeService;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void before(){
        System.out.println("Before Each Test");
        employeeRepository.deleteAll().subscribe();
    }

    @Test
    public void testSaveEmployee(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Top1");
        employeeDto.setLastName("Raz");
        employeeDto.setEmail("top1raz@gmail.com");

        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void testGetSingleEmployee(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Top1");
        employeeDto.setLastName("Tulen");
        employeeDto.setEmail("top1tulen@gmail.com");

        EmployeeDto savedEmployee = employeeService.addEmployee(employeeDto).block();

        webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void testGetAllEmployees(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Top1");
        employeeDto.setLastName("Raz");
        employeeDto.setEmail("top1raz@gmail.com");
        employeeService.addEmployee(employeeDto).block();

        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("Top1");
        employeeDto1.setLastName("Tulen");
        employeeDto1.setEmail("top1tulen@gmail.com");
        employeeService.addEmployee(employeeDto1).block();

        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void testUpdateEmployee(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Top1");
        employeeDto.setLastName("Raz");
        employeeDto.setEmail("top1raz@gmail.com");
        EmployeeDto employeeDto1 = employeeService.addEmployee(employeeDto).block();

        EmployeeDto savedEmployee = new EmployeeDto();
        savedEmployee.setFirstName("Top1");
        savedEmployee.setLastName("Tulen");
        savedEmployee.setEmail("top1Tulen@gmail.com");

        webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", employeeDto1.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(savedEmployee), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(employeeDto1.getId())
                .jsonPath("$.firstName").isEqualTo(savedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(savedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
    }

    @Test
    public void testDeleteEmployee(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Top1");
        employeeDto.setLastName("Raz");
        employeeDto.setEmail("top1raz@gmail.com");
        EmployeeDto savedEmployee = employeeService.addEmployee(employeeDto).block();

        webTestClient.delete().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }

}
