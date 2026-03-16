package ru.alexandernosevich.springdatahwproections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandernosevich.springdatahwproections.entity.Employee;
import ru.alexandernosevich.springdatahwproections.repository.EmployeeRepository;
import ru.alexandernosevich.springdatahwproections.dto.EmployeeProjection;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    //Найти полное имя сотрудника по АйДи
    public EmployeeProjection getFullNameOfEmployeeById(Long id) {
        return employeeRepository.getFullName(id)
                .orElseThrow(() -> new RuntimeException("Поиск не дал результатов"));
    }

    //Найти сотрудника по АЙДи
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Сотрудник не найден"));
    }

    //Добавить сотрудника
    public Employee createEmployee(Employee employee) {
        if(employeeRepository.findById(employee.getId()).isPresent()) {
            throw new RuntimeException("Сотрудник уже есть в базе");
        }
        return employeeRepository.save(employee);
    }

    //Удалить сотрудника
    public void deleteEmployeeById(Long id){
        if(!employeeRepository.existsById(id)) {
            throw new RuntimeException("Сотрудник не найден");
        }
        employeeRepository.deleteById(id);
    }

    //Получить список всех сотрудников
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    //Обновить данные сотрудника
    public Employee updateEmployeeById(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);

        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setSalary((updatedEmployee.getSalary()));
        existingEmployee.setPosition(updatedEmployee.getPosition());
        existingEmployee.setLastName(updatedEmployee.getLastName());

        return employeeRepository.save(existingEmployee);
    }
}
