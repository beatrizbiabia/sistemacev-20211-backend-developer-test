package com.survivorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;



/**
 * Classe Principal
 * 
 * Created by Beatriz on 11/01/21.
 */
@SpringBootApplication
public class ZssnapiApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		//System.setProperty("server.servlet.context-path", "/zssnapi2");
		SpringApplication.run(ZssnapiApplication.class, args);
	}

	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(applicationClass);
	    }
	 
	    private static Class<ZssnapiApplication> applicationClass = ZssnapiApplication.class;
}
