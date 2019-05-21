package cl.aws.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/bower_components/**").addResourceLocations("classpath:/bower_components/"); 
        registry.addResourceHandler("/dist/**").addResourceLocations("classpath:/dist/");
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/admin/");
        registry.addResourceHandler("/db-demo/**").addResourceLocations("classpath:/db-demo/");
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("classpath:/plugins/"); 
    }
}