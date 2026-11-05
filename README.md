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