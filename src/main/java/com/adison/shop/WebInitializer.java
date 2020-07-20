package com.adison.shop;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(MvcConfiguration.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        //map all incoming requests to the servlet, for it to pass them on
        dispatcher.addMapping("/");
        //load on ctx start up, otherwise lazily initialized on first request (this servlet loads first).
        //the method itself enforces servlet load on startup
        dispatcher.setLoadOnStartup(1);
    }
}
