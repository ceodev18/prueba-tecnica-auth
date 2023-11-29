## Pasos para ejecutar el app despues de git clone

```bash
# eliminará directorio "target"
$ mvn clean

# Compilará el código fuente de tu proyecto
$ mvn compile

# Este comando compila y empaqueta tu aplicación en un archivo JAR
$ mvn package

# Validar dependencias estén resueltas correctamente
$ mvn dependency:resolve

# Ejecutar pruebas
$ mvn test

# Correr aplicación
$ mvn spring-boot:run

#java version
17 - compatible con las ultimas versiones de springboot

## Libraries used
 h2 - base de datos en memoria
 lombok - para omitir getters, setters y otros
 junit4 - para pruebas
 swagger - interfaz de usuario para probar el endpoint

##Swagger UI
* [Swagger UI](http://localhost:8080/doc/swagger-ui/index.html#)





