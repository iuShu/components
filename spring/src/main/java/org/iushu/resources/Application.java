package org.iushu.resources;

import org.iushu.resources.beans.Network;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Arrays;

/**
 * @see org.springframework.core.io.Resource
 * @see org.springframework.core.io.ResourceLoader
 * @see org.springframework.core.io.DefaultResourceLoader
 * @author iuShu
 * @since 1/22/21
 */
public class Application {

    /**
     * @see org.springframework.core.io.FileUrlResource
     */
    public static void fileResource() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("file:///home/iushu/employer_1125_225.jpg");
        print(resource);
    }

    /**
     * @see org.springframework.core.io.ClassPathResource
     */
    public static void classpathResource() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:org/iushu/ioc/spring-ioc.xml");
        print(resource);
    }

    /**
     * @see org.springframework.core.io.UrlResource
     */
    public static void httpResource() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("https://static.runoob.com/assets/upvotejs/dist/upvotejs/upvotejs.jquery.js");
        print(resource);
    }

    /**
     * @see org.springframework.core.io.support.ResourcePatternResolver
     * @see org.springframework.core.io.support.PathMatchingResourcePatternResolver resolve resource's path that contains wildcards
     * @see org.springframework.util.PathMatcher
     */
    public static void wildcardsResource() {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext();
            Resource[] resource = context.getResources("classpath*:org/iushu/*/*.xml");
            print(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see org.springframework.core.io.ResourceEditor#setAsText(String)
     */
    public static void beanDependency() {
        ApplicationContext context = new ClassPathXmlApplicationContext("org/iushu/resources/spring-resources.xml");
        Network network = context.getBean(Network.class);
        System.out.println(network);
    }

    private static void print(Resource... resources) {
        for (Resource resource : resources) {
            System.out.println("type: " + resource.getClass().getName());
            System.out.println("exists: " + resource.exists());
            System.out.println("name: " + resource.getFilename());
            System.out.println("desc: " + resource.getDescription());   // equals to 'resource.toString()'
            try {
                System.out.println("uri: " + resource.getURI());
                System.out.println("url: " + resource.getURL());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
//        fileResource();
        classpathResource();
//        httpResource();
//        wildcardsResource();
//        beanDependency();
    }

}
