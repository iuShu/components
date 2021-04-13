package org.iushu.project;

import org.iushu.project.bean.DataSourceProperties;
import org.iushu.project.bean.FakeDataSource;
import org.iushu.project.service.DefaultDepartmentService;
import org.iushu.project.service.DepartmentService;
import org.iushu.project.service.StaffService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author iuShu
 * @since 4/8/21
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
public class ProjectConfiguration {

    @Bean
    @ConditionalOnMissingBean(StaffService.class)
    public DepartmentService departmentService() {
        return new DefaultDepartmentService();
    }

    @Bean
    public ApplicationRunner customRunner() {
        return args -> System.out.println("[runner] " + args.toString());
    }

    @Bean
    public FakeDataSource dataSource(DataSourceProperties properties) {
        return new FakeDataSource(properties);
    }

}
