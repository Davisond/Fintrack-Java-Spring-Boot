# FinTrack

API de finanças pessoais em Java 21 + Spring Boot 3.

## Etapa 0: setup

**Como rodar:** `./mvnw spring-boot:run` (sobe na porta 8080).

**O que aprendi sobre o pom.xml:**
- `parent`: herda do Spring Boot as versões e configurações padrão, então não preciso declarar a versão de cada biblioteca.
- `dependencies`: as bibliotecas que o projeto usa (por enquanto, Spring Web e a de testes).
- `build/plugins`: ferramenta que empacota e roda a aplicação Spring Boot.
- `properties`: define a versão do Java (21).
- `groupId` / `artifactId`: identificam o projeto.

## etapa 1: Java puro

### Por que BigDecimal e não Double?
O double para dinheiro guarda números em binário e não resulta exatamente nos valores como 0.1,
No jshel 0.1 + 0.2 daria '0.3000...', se tratando de dinheiro geraria um acúmulo de centavos errados.
O BigDecimal guarda em decimal, a precisão fica correta, a unica desvantagem é usar add e subtract ao invés de + e -

## Diferença de Checked e unchecked
Checked: extend excepton, o compilador obriga quem chama o método a tratar com try/catch ou declarar throws
Unchecked: extends RuntimeException, A exceção sobe na pilha até ser tratada, é o caso de LancamentoNaoEncontradoException


