package org.iushu.tutorial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * @author iuShu
 * @since 8/3/21
 */
@Configuration
public class TutorialConfiguration {

    @Bean
    @Profile("consumer")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
