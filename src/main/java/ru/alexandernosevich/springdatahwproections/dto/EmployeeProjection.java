package ru.alexandernosevich.springdatahwproections.dto;

public interface EmployeeProjection {

    String getPosition();
    String getDepartmentName();
    String getFirstName();
    String getLastName();

    default String getFullName() {
        return getFirstName() + ' ' + getLastName();
    }
}
