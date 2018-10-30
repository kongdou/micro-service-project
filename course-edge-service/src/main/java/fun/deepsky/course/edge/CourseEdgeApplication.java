package fun.deepsky.course.edge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import fun.deepsky.course.edge.filter.CourseFilter;

@SpringBootApplication
public class CourseEdgeApplication
{
    public static void main( String[] args )
    {
       SpringApplication.run(CourseEdgeApplication.class, args);
    }
    
    /**
     * 注册Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean<CourseFilter> filterRegistrationBean() {
    	FilterRegistrationBean<CourseFilter> bean = new FilterRegistrationBean<CourseFilter>();
    	CourseFilter courseFilter = new CourseFilter();
    	bean.setFilter(courseFilter);
    	
    	List<String> urlPatterns = new ArrayList<>();
    	urlPatterns.add("/*");
    	bean.setUrlPatterns(urlPatterns);
    	return bean;
    }
}
