package org.iushu.plugins.calendar;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created by iuShu on 19-7-15
 */
public class CalendarWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        CalendarWindow calendarWindow = new CalendarWindow(toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(calendarWindow.getToolContent(), "CCCCCCalendar", false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.show(null);
    }
}
