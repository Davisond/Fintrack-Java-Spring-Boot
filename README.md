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

### Genéricos : Resultado<T>
`Resultado<T>` guarda um valor ou uma lista de erros, de modo que, a validação devolta todos os erros juntos, sem lançar exceção, e o compilador confere o tipo do valor.

### equals, hashCode e toString
Um record gera equals, hashCode e toString comparando todos os campos, em uma classe normal sem estes métodos o java comppara pela referência (==), mas dois objetos com mesmo conteúdo não são iguais.
Se dois objetos são iguais precisam ter o mesmo hashCode, se não o HashMap e HashSet dá problema. Prova: IgualdadeTeste.

### Como rodar o Cli
Pelo terminal:

    ./mvnw -q compile
    java -cp target/classes com.example.fintrack.cli.app.Cli

Comandos: `add`, `list`, `remove <id>`, `resumo`, `sair`.