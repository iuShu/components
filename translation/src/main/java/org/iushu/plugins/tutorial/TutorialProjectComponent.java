package org.iushu.plugins.tutorial;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * Created by iuShu on 19-7-12
 */
public class TutorialProjectComponent implements ProjectComponent {

    private Project project;
    private TutorialProjectService service;

    @Override
    public void projectOpened() {
        ProjectManager manager = ProjectManager.getInstance();
        Project[] projects = manager.getOpenProjects();
        project = projects[projects.length - 1];
        if (getService().increase() == -1)
            tooMuchProject();
        System.out.println("[Tutorial] opened: " + project.getName() + ", count: " + getService().getProjectCount());
    }

    @Override
    public void projectClosed() {
        getService().decrease();
        System.out.println("[Tutorial] closed: " + project.getName() + ", count: " + getService().getProjectCount());
    }

    @Override
    public void initComponent() {
        System.out.println("[Tutorial] initializing Component");
    }

    @Override
    public void disposeComponent() {
        System.out.println("[Tutorial] disposing Component");
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "TutorialProjectComponent";
    }

    private TutorialProjectService getService() {
        if (service == null)
            service = ServiceManager.getService(TutorialProjectService.class);
        return service;
    }

    // close the most recently opened project
    private void tooMuchProject() {
        ProjectManager manager = ProjectManager.getInstance();
        Project[] projects = manager.getOpenProjects();
        Project recently = projects[projects.length - 1];
        String projectName = recently.getName();
        manager.closeProject(recently);
        Messages.showMessageDialog("[Tutorial] too much project, close: " + projectName, "Tutorial", Messages.getInformationIcon());
    }

}
