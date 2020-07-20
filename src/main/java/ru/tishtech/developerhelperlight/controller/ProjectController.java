package ru.tishtech.developerhelperlight.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tishtech.developerhelperlight.constant.FileTypes;
import ru.tishtech.developerhelperlight.model.Project;
import ru.tishtech.developerhelperlight.model.Variable;
import ru.tishtech.developerhelperlight.service.generator.GeneratorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ProjectController {

    @GetMapping("/project")
    public String projectPage(Model model) {
        model.addAttribute("project", new Project());
        return "project";
    }

    @PostMapping(value = "/project/generate", params = {"addVariable"})
    public String addVariable(Project project) {
        project.getVariables().add(new Variable());
        return "project";
    }

    @PostMapping(value = "/project/generate", params = {"removeVariable"})
    public String removeVariable(@RequestParam String removeVariable, Project project) {
        int variableId = Integer.parseInt(removeVariable);
        project.getVariables().remove(variableId);
        return "project";
    }

    @PostMapping("/project/generate")
    public String projectGenerate(Project project, HttpServletRequest request, Model model) {
        String projectPath = request.getServletContext().getRealPath("/");
        GeneratorService.generateFiles(project.getName(), project.getGroupId(), project.getModel(),
                project.getVariables(), projectPath);
        model.addAttribute("project", project);
        model.addAttribute("projectPath", projectPath);
        return "done";
    }

    @GetMapping(value = "/project/download", produces = "application/zip")
    public @ResponseBody
    byte[] projectDownload(Project project, @RequestParam String projectPath,
                           HttpServletResponse response) throws IOException {
            InputStream inputStream = new FileInputStream(projectPath + project.getName() + FileTypes.ZIP_TYPE);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + project.getName() + FileTypes.ZIP_TYPE);
            return IOUtils.toByteArray(inputStream);
    }
}
