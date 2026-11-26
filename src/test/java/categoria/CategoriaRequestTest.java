package categoria;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.fintrack.categoria.CategoriaRequest;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

class CategoriaRequestTest {

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void requestValidoNaoTemViolacoes() {
        assertTrue(validator.validate(new CategoriaRequest("Mercado", "#FF8800")).isEmpty());
    }

    @Test
    void requestVazioViolaOsDoisCampos() {
        Set<String> campos = validator.validate(new CategoriaRequest(null, null)).stream()
                .map(v -> v.getPropertyPath().toString())
                .collect(Collectors.toSet());

        assertEquals(Set.of("nome", "cor"), campos);
    }

    @ParameterizedTest
    @ValueSource(strings = { "FF8800", "#FF88", "#GGGGGG", "vermelho", "#FF88000" })
    void rejeitaCorForaDoFormato(String cor) {
        assertEquals(1, validator.validate(new CategoriaRequest("Mercado", cor)).size());
    }
}