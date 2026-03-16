package ru.alexandernosevich.springdatahwproections.repository;

import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandernosevich.springdatahwproections.dto.EmployeeProjection;
import ru.alexandernosevich.springdatahwproections.entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //Получить полное имя
    Optional<EmployeeProjection> getFullName(Long id);



}
