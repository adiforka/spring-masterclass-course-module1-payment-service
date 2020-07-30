package com.adison.shop;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

    private final String TEMP_FOLDER = "./";
    private final int MAX_UPLOAD_SIZE = 10 * 1024 * 1024;


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        var ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(MvcConfiguration.class);
        var dispatcherServlet = new DispatcherServlet(ctx);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        //map all incoming requests to the servlet, for it to pass them on
        dispatcher.addMapping("/");
        //load on ctx start up, otherwise lazily initialized on first request (this servlet loads first).
        //the method itself enforces servlet load on startup
        dispatcher.setLoadOnStartup(1);
        //for file upload
        var multipartConfigElement = new MultipartConfigElement(TEMP_FOLDER, MAX_UPLOAD_SIZE,
                MAX_UPLOAD_SIZE * 2,
                MAX_UPLOAD_SIZE / 2);
        dispatcher.setMultipartConfig(multipartConfigElement);
    }
}
