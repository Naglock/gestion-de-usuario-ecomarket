# Usar una imagen base oficial de OpenJDK 17 para ejecutar la app
FROM eclipse-temurin:17-jdk-jammy

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el jar generado (asegúrate de que el nombre coincide con tu jar)
COPY target/user-management-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto donde corre tu app (ejemplo 8080)
EXPOSE 8080

# Comando para ejecutar tu app Java
ENTRYPOINT ["java", "-jar", "app.jar"]