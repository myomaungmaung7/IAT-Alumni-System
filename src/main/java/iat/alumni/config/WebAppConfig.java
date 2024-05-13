package iat.alumni.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import iat.alumni.interceptor.SecurityInterceptor;

@Component
public class WebAppConfig implements WebMvcConfigurer{
	  @Autowired
	  SecurityInterceptor securityInterceptor;
	  
	  @Override
	  public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(securityInterceptor);
	  }
	  @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        exposeDirectory("user-photos", registry);
	        exposeDirectory("event-photos", registry);
	        exposeDirectory("article-photos", registry);
	    }
	     
	    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
	    Path uploadDir = Paths.get(dirName);
	    String uploadPath = uploadDir.toFile().getAbsolutePath();
	         
	    if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
	         
	    registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
	}
}
