package com.example.fintrack.validacao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented 
@Constraint(validatedBy = ValorMonetarioValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValorMonetario {

    String message() default "Valores em dinheiro só podem ter um limite de 2 casas decimais";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
