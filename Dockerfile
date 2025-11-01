# Multi-stage build para otimizar o tamanho da imagem
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte e compila a aplicação
COPY src ./src
RUN mvn clean package -DskipTests

# Imagem final com apenas o runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Cria usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring

# Cria diretório para uploads ANTES de mudar para usuário não-root
RUN mkdir -p /app/uploads && chown -R spring:spring /app/uploads

# Copia o JAR da aplicação do stage de build
COPY --from=build /app/target/*.jar app.jar

# Muda para usuário não-root após criar diretórios e copiar arquivos
USER spring:spring

# Expõe a porta da aplicação
EXPOSE 8080

# Define o ponto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]

