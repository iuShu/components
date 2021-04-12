package org.iushu.project;

import org.iushu.project.service.DefaultDepartmentService;
import org.iushu.project.service.DepartmentService;
import org.iushu.project.service.StaffService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author iuShu
 * @since 4/8/21
 */
@Configuration
public class ProjectConfiguration {

    @Bean
    @ConditionalOnMissingBean(StaffService.class)
    public DepartmentService departmentService() {
        return new DefaultDepartmentService();
    }

}
