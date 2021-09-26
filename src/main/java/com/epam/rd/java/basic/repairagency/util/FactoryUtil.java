package com.epam.rd.java.basic.repairagency.util;

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
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FactoryUtil {

    public static CommandFactory buildCommandFactory(String commandsImplPackageName) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
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
        return commandFactoryConstructor.newInstance(commandsByUrlPatternAndMethod);
    }

    public static Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(c -> c.isAnnotationPresent(ProcessUrlPatterns.class))
                .collect(Collectors.toSet());
    }

    public static ServiceFactory buildServiceFactory(String rootPackageName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
        return serviceFactoryConstructor.newInstance(servicesWithImpl);
    }

    private static Map<Class<? extends GenericRepository<? extends AbstractEntity>>,
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

    private static Map<Class<? extends GenericService<? extends AbstractEntity>>,
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
