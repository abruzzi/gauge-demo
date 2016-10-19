package com.thoughtworks.jigsaw.sbe;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.jigsaw.InMemoryProjectRepository;
import com.thoughtworks.jigsaw.Project;
import com.thoughtworks.jigsaw.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ProjectStepImplementation {
    private String role;
    private List<String> skills;
    private ProjectRepository projectRepository;

    @Step("作为 <role> 我是一名具有下面技能的 <table>")
    public void iAmARole(String role, Table skills) {
        this.role = role;
        this.skills = skills.getTableRows().stream().map(x -> x.getCell("name")).collect(toList());
    }

    @Step("上次办公室更新的时候听说目前我们有这些项目 <table>")
    public void currentWeHaveProjects(Table table) {
        ArrayList<ProjectObject> projectObjects = new ArrayList<>();
        for(TableRow row: table.getTableRows()) {
            String name = row.getCell("name");
            String techStack = row
                    .getCell("techStack");
            String openRoles = row.getCell("openRoles");

            projectObjects.add(new ProjectObject(name, techStack, openRoles));
        }
        List<Project> collect = projectObjects.stream().map(ProjectObject::toProject).collect(toList());
        projectRepository = new InMemoryProjectRepository(collect);
    }

    @Step("那么可供我选择的项目有下面这些 <table>")
    public void whatICouldChooseAre(Table projects) {
        List<String> results = projectRepository.findBySkillsAndRole(skills, role).stream().map(Project::getName).collect(toList());

        List<TableRow> tableRows = projects.getTableRows();
        List<String> names = tableRows.stream().map(row -> row.getCell("name")).collect(toList());

        assertThat(names, equalTo(results));
    }
}
