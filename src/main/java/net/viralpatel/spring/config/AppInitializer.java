package net.viralpatel.spring.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    protected Class<?>[] getRootConfigClasses() {
        //return null;
        return new Class[]{ AppConfig.class };
    }

    protected Class<?>[] getServletConfigClasses() {
        return  null;
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
