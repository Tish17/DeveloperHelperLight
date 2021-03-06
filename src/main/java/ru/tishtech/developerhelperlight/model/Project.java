package ru.tishtech.developerhelperlight.model;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String name;

    private String groupId;

    private String model;

    private List<Variable> variables = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId.toLowerCase();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }
}
