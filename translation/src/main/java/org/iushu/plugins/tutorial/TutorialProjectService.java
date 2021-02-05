package org.iushu.plugins.tutorial;

/**
 * Created by iuShu on 19-7-12
 */
public class TutorialProjectService {

    private static final int MAX_PROJECT_COUNT = 2;

    private int projectCount = 0;

    public TutorialProjectService() {}

    public int getProjectCount() {
        return projectCount;
    }

    public int increase() {
        return ++projectCount > MAX_PROJECT_COUNT ? -1 : projectCount;
    }

    public int decrease() {
        return projectCount == 0 ? 0 : --projectCount;
    }

}
