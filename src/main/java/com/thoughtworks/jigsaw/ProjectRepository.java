package com.thoughtworks.jigsaw;

import java.util.List;

public interface ProjectRepository {
    List<Project> findBySkillsAndRole(List<String> skills, String role);
    boolean save(Project project);
    Project findByName(String project);
}
