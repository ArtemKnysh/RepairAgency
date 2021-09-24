package com.epam.rd.java.basic.repairagency.web.command.annotation;

import com.epam.rd.java.basic.repairagency.web.command.Method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessMethods {

    Method[] value();
}
