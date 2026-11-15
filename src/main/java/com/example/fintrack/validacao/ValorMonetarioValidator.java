package com.example.fintrack.validacao;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValorMonetarioValidator implements ConstraintValidator<ValorMonetario, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal valor, ConstraintValidatorContext context) {
        if (valor == null){
            return true; //Não faz sentido? não, mas é pq tem o @NotNull p isso
        }
        return valor.stripTrailingZeros().scale() <= 2;
    }
}
