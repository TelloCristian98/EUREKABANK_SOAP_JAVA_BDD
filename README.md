# Guía Corta para Correr el Servidor EUREKABANK SOAP desde Otra Computadora en Windows con NetBeans

## Requisitos de Instalación

1. **Java Development Kit (JDK) 8.0**:
   - Descarga desde [Oracle](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html) o OpenJDK 8.
   - Instala y configura `JAVA_HOME` (e.g., `C:\Program Files\Java\jdk1.8.0_351`) y añade `%JAVA_HOME%\bin` a `Path`.
   - Verifica con `java -version`.

2. **NetBeans 8.2**:
   - Descarga desde [NetBeans Archive](https://netbeans.apache.org/download/archive/8.2/index.html) (versión con Java EE).
   - Instala y selecciona el JDK 8 como plataforma predeterminada.

3. **GlassFish 4.1**:
   - Incluido con NetBeans, o descarga desde [GlassFish](https://javaee.github.io/glassfish/download).
   - Configura en NetBeans: `Tools` > `Servers` > Añade GlassFish y apunta a la carpeta (e.g., `C:\glassfish4`).

4. **MySQL 8.0**:
   - Descarga desde [MySQL](https://dev.mysql.com/downloads/mysql/).
   - Instala y configura (e.g., usuario `root`, contraseña `root123`).

5. **MySQL JDBC Driver**:
   - Descarga `mysql-connector-j-9.3.0.jar` desde [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).

## Pasos para Abrir y Correr el Proyecto

1. **Descomprimir el Proyecto**:
   - Extrae el archivo comprimido (`EUREKA_BANK_SOAP_JAVA.zip`) en una carpeta (e.g., `C:\Users\NuevoUsuario\Projects`).

2. **Abrir en NetBeans**:
   - Abre NetBeans, ve a `File` > `Open Project`.
   - Selecciona la carpeta descomprimida (`EUREKA_BANK_SOAP_JAVA`) y haz clic en `Open Project`.

3. **Configurar Librerías**:
   - Haz clic derecho en `Libraries` > `Add JAR/Folder`.
   - Añade `mysql-connector-j-9.3.0.jar` y confirma que esté listado.

4. **Configurar Servidor**:
   - Ve a `Tools` > `Servers`, asegúrate de que GlassFish 4.1 esté configurado.
   - En `Project Properties` (`Right-click Project` > `Properties` > `Run`), selecciona GlassFish 4.1 como servidor.

5. **Configurar Base de Datos**:
   - Asegúrate de que MySQL esté corriendo (`localhost:3306`).
   - Crea la base de datos `eurekabank` y las tablas con el script SQL proporcionado (en MySQL Workbench).
   - Actualiza `AccesoDB.java` con las credenciales de MySQL si son diferentes (e.g., `root`, `root123`).

6. **Compilar y Desplegar**:
   - Haz clic derecho en el proyecto > `Clean and Build`.
   - Luego, `Deploy` para iniciar el servidor.
   - Verifica en el `Output` que no haya errores y que el endpoint esté en `http://localhost:8080/EUREKA_BANK_SOAP_JAVA/WSEureka`.

7. **Prueba**:
   - Usa SoapUI para enviar solicitudes (e.g., `login` con `MONSTER`/`monster`) y confirma las respuestas.

## Notas

- Asegúrate de que el firewall permita el puerto 8080 y 3306.
- Si hay errores, revisa los logs en `glassfish4/glassfish/domains/domain1/logs/server.log`.
