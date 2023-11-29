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

## Captura de Swagger
Se puede ubicar en la ruta http://localhost:8080/doc/swagger-ui/index.html#

<img width="1489" alt="image" src="https://github.com/ceodev18/prueba-tecnica-auth/assets/20958764/6ae313bb-2080-4605-a4eb-7d2ffe11684c">



