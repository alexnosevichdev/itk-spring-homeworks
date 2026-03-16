package ru.alexandernosevich.springdatahwproections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alexandernosevich.springdatahwproections.entity.Department;
import ru.alexandernosevich.springdatahwproections.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    //Запрос на получение список всех отделов
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getAll() {
        return departmentService.getAllDepartments();
    }

    //Запрос на создание отдела
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    //Запрос на поиск конкретного отдела
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    //Запрос на удаление отдела
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
    }

    //ЗАпрос на обновление данных отдела
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department updateDepartment(@PathVariable Long id,
                                       @RequestBody Department department) {
        return departmentService.updateDepartmentById(id, department);
    }
}


