package org.iushu.plugins.translation;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Created by iuShu on 19-7-12
 */
public class TranslationProjectService {

    private static TranslationProjectService instance;

    public TranslationProjectService(Project project) {}

    public static TranslationProjectService getInstance(@NotNull Project project) {
        if (instance == null)
            instance = ServiceManager.getService(project, TranslationProjectService.class);
        return instance;
    }
}
