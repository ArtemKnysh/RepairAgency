package com.epam.rd.java.basic.repairagency.factory;

import com.epam.rd.java.basic.repairagency.entity.AbstractEntity;
import com.epam.rd.java.basic.repairagency.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public final class ServiceFactory {

    private static final Logger log = LogManager.getLogger(ServiceFactory.class);
    private final Map<Class<? extends GenericService<? extends AbstractEntity>>,
            GenericService<? extends AbstractEntity>> services;

    public ServiceFactory(Map<Class<? extends GenericService<? extends AbstractEntity>>,
            GenericService<? extends AbstractEntity>> services) {
        this.services = services;
    }

    public static String getName() {
        return "ServiceFactory";
    }

    public void printAll() {
        services.forEach((serviceClass, service) ->
                log.info("{} ==> {}", serviceClass, service));
    }

    public GenericService<? extends AbstractEntity> getService(Class<? extends GenericService<? extends AbstractEntity>> genericServiceClass) {
        return services.get(genericServiceClass);
    }

}
