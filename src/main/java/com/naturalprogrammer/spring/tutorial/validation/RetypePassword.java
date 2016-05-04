package com.naturalprogrammer.spring.tutorial.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy=RetypePasswordValidator.class)
public @interface RetypePassword {
 
    String message() default "{passwordsDoNotMatch}";

    Class[] groups() default {};
    
    Class[] payload() default {};
}
