package ru.alexandernosevich.springdatahwproections.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alexandernosevich.springdatahwproections.entity.Employee;
import ru.alexandernosevich.springdatahwproections.service.EmployeeService;
import ru.alexandernosevich.springdatahwproections.dto.EmployeeProjection;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    //Запрос на создание сотрудника
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    //Запрос на получение всех сотрудников
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    //Запрос на получение информации о сотруднике
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeProjection getEmployeeById(@PathVariable Long id) {
        return employeeService.getFullNameOfEmployeeById(id);
    }
}
