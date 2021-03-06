package com.thoughtworks.jigsaw.sbe;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.jigsaw.Employee;
import com.thoughtworks.jigsaw.EmployeeRepository;
import com.thoughtworks.jigsaw.EmployeeService;
import com.thoughtworks.jigsaw.InMemoryEmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class StaffingStepImplementation {
    List<EmployeeObject> employees = new ArrayList<EmployeeObject>();
    EmployeeRepository employeeRepository;
    EmployeeService employeeService;

    @Step("如果目前西安办公室有这样一些同事 <table>")
    public void background(Table table) {
        for (TableRow row : table.getTableRows()) {
            String name = row.getCell("name");
            String currentProject = row.getCell("currentProject");
            String role = row.getCell("role");

            EmployeeObject employeeObject = new EmployeeObject(name, currentProject, role);
            if(row.getCell("skills") != null) {
                employeeObject.setSkills(row.getCell("skills"));
            }
            employees.add(employeeObject);
        }

        employeeRepository = new InMemoryEmployeeRepository(employees.stream().map(EmployeeObject::toEmployee).collect(toList()));
        employeeService = new EmployeeService(employeeRepository);
    }

    @Step("那么下面这几个人是可以出台的 <table>")
    public void theFollowingPSCouldBeAssignable(Table employees) {
        List<String> allAssignable = employeeService.findAllAssignable().stream().map(Employee::getName).collect(toList());
        List<TableRow> tableRows = employees.getTableRows();
        List<String> names = tableRows.stream().map(row -> row.getCell("name")).collect(toList());

        assertThat(names, equalTo(allAssignable));
    }

    @Step("那么下面这几个人可以上 <skill> 技术栈的项目 <table>")
    public void theFollowingPSCouldBeAssignableToProject(String skill, Table employees) {
        List<String> allAssignable = employeeService.findBySkill(skill).stream().map(Employee::getName).collect(toList());

        List<TableRow> tableRows = employees.getTableRows();
        List<String> names = tableRows.stream().map(row -> row.getCell("name")).collect(toList());

        assertThat(names, equalTo(allAssignable));
    }
}
