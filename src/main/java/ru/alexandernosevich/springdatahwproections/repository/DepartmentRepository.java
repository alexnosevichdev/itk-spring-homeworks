package ru.alexandernosevich.springdatahwproections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandernosevich.springdatahwproections.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
