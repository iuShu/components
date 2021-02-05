package org.iushu.plugins.tutorial;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Created by iuShu on 19-7-12
 */
public class TutorialAction extends AnAction {

    public TutorialAction() {
        super("Hello Plugins .. from constructor");
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Messages.showMessageDialog(project, "Tutorial Action is Working .. from Action", "Greeting .. from Action", Messages.getInformationIcon());
    }
}
