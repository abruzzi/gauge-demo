package com.thoughtworks.jigsaw;

import java.util.List;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAllAssignable() {
        return employeeRepository.findAllAssignable();
    }

    public List<Employee> findBySkill(String skill) {
        return employeeRepository.findBySkill(skill);
    }
}
