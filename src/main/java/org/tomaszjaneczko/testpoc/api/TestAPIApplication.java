package org.tomaszjaneczko.testpoc.api;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.tomaszjaneczko.testpoc.api.businesses.BusinessesResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class TestAPIApplication extends Application<TestAPIConfiguration> {

    public static final String ASSETS_SERVLET_NAME = "assets-servlet";

    public static void main(String[] args) throws Exception {
        new TestAPIApplication().run(args);
    }

    @Override
    public String getName() {
        return "test-api";
    }

    @Override
    public void initialize(Bootstrap<TestAPIConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/static/", "/", "index.html", ASSETS_SERVLET_NAME));
    }

    @Override
    public void run(TestAPIConfiguration configuration, Environment environment) {
        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new BusinessesResource());

        final FilterRegistration.Dynamic filterBuilder
                = environment.servlets().addFilter("static-filter", AssetsServingFilter.class);

        filterBuilder.addMappingForServletNames(EnumSet.allOf(DispatcherType.class), true, ASSETS_SERVLET_NAME);
    }
}
