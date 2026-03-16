package ru.alexandernosevich.springdatahwproections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandernosevich.springdatahwproections.entity.Department;
import ru.alexandernosevich.springdatahwproections.entity.Employee;
import ru.alexandernosevich.springdatahwproections.repository.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    //Создать отдел
    public Department createDepartment(Department department) {
        if(departmentRepository.findById(department.getId()).isPresent()){
            throw new RuntimeException("Такой отдел уже есть");
        }
        return departmentRepository.save(department);
    }

    //Найти отдел по АйДи
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отдел не найден"));
    }

    //Получить список всех отделов
    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    //Удалить отдел
    public void deleteDepartmentById(Long id) {
        if(!departmentRepository.existsById(id)) {
            throw new RuntimeException("Отдел не найден");
        }
        departmentRepository.deleteById(id);
    }

    //Обновить данные по отделу через АйДи
    public Department updateDepartmentById(Long id, Department updatedDepartment) {
        Department existingDepartment = getDepartmentById(id);

        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setEmployeeList(updatedDepartment.getEmployeeList());

        return departmentRepository.save(existingDepartment);
    }
}
