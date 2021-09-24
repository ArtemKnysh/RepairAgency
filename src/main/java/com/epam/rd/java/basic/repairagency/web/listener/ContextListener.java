package com.epam.rd.java.basic.repairagency.web.listener;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.factory.CommandFactory;
import com.epam.rd.java.basic.repairagency.factory.ServiceFactory;
import com.epam.rd.java.basic.repairagency.factory.anotation.Inject;
import com.epam.rd.java.basic.repairagency.factory.anotation.Repository;
import com.epam.rd.java.basic.repairagency.factory.anotation.Service;
import com.epam.rd.java.basic.repairagency.repository.GenericRepository;
import com.epam.rd.java.basic.repairagency.service.GenericService;
import com.epam.rd.java.basic.repairagency.web.command.Command;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.UrlPatternAndMethod;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessMethods;
import com.epam.rd.java.basic.repairagency.web.command.annotation.ProcessUrlPatterns;
import com.epam.rd.java.basic.repairagency.web.config.SecurityConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        configSecurity(sce);
        configCommandFactory(sce);
        configServiceFactory(sce);
//        SecurityConfig.printAll();
    }

    private void configSecurity(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String appName = servletContext.getInitParameter("appName");
        SecurityConfig.clearUrls();
        Enumeration<String> parameterNames = servletContext.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            if (!name.toLowerCase().contains("url")) {
                continue;
            }
            String parameter = servletContext.getInitParameter(name);
            parameter = parameter.substring(appName.length());
            SecurityConfig.putUrl(name, parameter);
        }
    }

    private void configCommandFactory(ServletContextEvent sce) {
        try {
            ServletContext servletContext = sce.getServletContext();
            String commandsImplPackageName = servletContext.getInitParameter("commandsImplPackageName");
            Set<Class<?>> commandsSet = findAllClassesUsingClassLoader(commandsImplPackageName);
            Map<UrlPatternAndMethod, Command> commandsByUrlPatternAndMethod = new HashMap<>();
            for (Class<?> commandClass : commandsSet) {
                String[] urlPatterns = commandClass.getAnnotation(ProcessUrlPatterns.class).value();
                ProcessMethods processMethods = commandClass.getAnnotation(ProcessMethods.class);
                Command command = commandClass.asSubclass(Command.class).getConstructor().newInstance();
                for (Method method : processMethods.value()) {
                    for (String urlPattern : urlPatterns) {
                        commandsByUrlPatternAndMethod.put(new UrlPatternAndMethod(urlPattern, method), command);
                    }
                }
            }
            Constructor<CommandFactory> commandFactoryConstructor = CommandFactory.class.getDeclaredConstructor(Map.class);
            commandFactoryConstructor.setAccessible(true);
            CommandFactory commandFactory = commandFactoryConstructor.newInstance(commandsByUrlPatternAndMethod);
            sce.getServletContext().setAttribute(CommandFactory.getName(), commandFactory);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("CommandFactory configuration error", e);
        }
    }

    public Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(c -> c.isAnnotationPresent(ProcessUrlPatterns.class))
                .collect(Collectors.toSet());
    }

    private void configServiceFactory(ServletContextEvent sce) {
        try {
            ServletContext servletContext = sce.getServletContext();
            String rootPackageName = servletContext.getInitParameter("rootPackageName");

            Map<Class<?>, Object> components = new HashMap<>(findAllRepositories(rootPackageName));

            Map<Class<? extends GenericService<? extends AbstractEntity>>,
                    GenericService<? extends AbstractEntity>> servicesWithImpl = findAllServices(rootPackageName);
            components.putAll(servicesWithImpl);

            for (Object component : components.values()) {
                for (Field field : component.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        Object valueForInjection = components.get(field.getType());
                        field.setAccessible(true);
                        field.set(component, valueForInjection);
                    }
                }
            }
            Constructor<ServiceFactory> serviceFactoryConstructor =
                    ServiceFactory.class.getDeclaredConstructor(Map.class);
            serviceFactoryConstructor.setAccessible(true);
            ServiceFactory serviceFactory = serviceFactoryConstructor.newInstance(servicesWithImpl);
            sce.getServletContext().setAttribute(ServiceFactory.getName(), serviceFactory);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("ServiceFactory configuration error", e);
        }
    }

    private Map<Class<? extends GenericRepository<? extends AbstractEntity>>,
            GenericRepository<? extends AbstractEntity>> findAllRepositories(String rootPackageName)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<Class<? extends GenericRepository<? extends AbstractEntity>>,
                GenericRepository<? extends AbstractEntity>> repositoriesWithImpl = new HashMap<>();
        Set<Class<?>> repositoriesClasses = new Reflections(rootPackageName).getTypesAnnotatedWith(Repository.class);
        for (Class<?> repositoryClass : repositoriesClasses) {
            Class<? extends GenericRepository<? extends AbstractEntity>> value = repositoryClass.getAnnotation(Repository.class).value();
            Constructor<?> constructor = repositoryClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object repository = constructor.newInstance();
            repositoriesWithImpl.put(value, value.cast(repository));
        }
        return repositoriesWithImpl;
    }

    private Map<Class<? extends GenericService<? extends AbstractEntity>>,
            GenericService<? extends AbstractEntity>> findAllServices(String rootPackageName)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<Class<? extends GenericService<? extends AbstractEntity>>,
                GenericService<? extends AbstractEntity>> servicesWithImpl = new HashMap<>();
        Set<Class<?>> servicesClasses = new Reflections(rootPackageName).getTypesAnnotatedWith(Service.class);
        for (Class<?> serviceClass : servicesClasses) {
            Class<? extends GenericService<? extends AbstractEntity>> value = serviceClass.getAnnotation(Service.class).value();
            Constructor<?> constructor = serviceClass.asSubclass(value).getDeclaredConstructor();
            constructor.setAccessible(true);
            Object service = constructor.newInstance();
            servicesWithImpl.put(value, value.cast(service));
        }
        return servicesWithImpl;
    }

}
