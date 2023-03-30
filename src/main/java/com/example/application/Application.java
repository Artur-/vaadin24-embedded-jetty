package com.example.application;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.theme.Theme;

@Theme(value = "embedded-jetty")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) throws Exception {
        final WebAppContext context = createWebAppContext();
        Server server = new Server(9090);
        server.setHandler(context);
        server.start();
        server.join();

    }

    // copied from: https://github.com/mvysny/vaadin-boot/tree/main/vaadin-boot
    private static WebAppContext createWebAppContext() throws IOException {
        final WebAppContext context = new WebAppContext();
        context.setBaseResource(Resource.newResource("src/main/webapp"));
        context.addServlet(VaadinServlet.class, "/*");
        // this will properly scan the classpath for all @WebListeners,
        // including the most important
        // com.vaadin.flow.server.startup.ServletContextListeners.
        // See also https://mvysny.github.io/vaadin-lookup-vs-instantiator/
        context.setAttribute(
                "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*\\.jar|.*/classes/.*");
        context.setConfigurationDiscovered(true);
        context.getServletContext().setExtendedListenerTypes(true);

        return context;
    }

}
