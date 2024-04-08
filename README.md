# Ejercicio práctico Muruna
A continuación se detallan los procedimientos para ejecución y aspectos de implementación

## Bibliotecas, frameworks y herramientas utilizados:
	1. Spring Boot 2.2.5
	2. Maven 3.3.3
	3. Java 8
	4. JUnit 5
	5. Hibernate 5.4.12
	6. Eclipse 2020-06 (4.16.0)
 	7. Swagger 2.9.2

## Ejecución
	Tanto la ejecución del servicio, como de los tests se puede realizar desde línea de comandos (subcarpeta /ejercicio):
	1. Para contruir: mvn install
	2. Para ejecutar servicio: gradlew bootRun
	3. Para ejecutar las pruebas de integración: mvn test -Dtest=IntegrationTest
	4. Para ejecutar las pruebas unitarias: mvn test -Dtest=UnitTest
	5. Para ejecutar todas las pruebas unitarias: mvn test
	6. Para ejecutar desde línea de comando, en subcarpeta /ejercicio:
		6.1 Ejecutar comando sobre el jar generado en punto 1., ejemplo: 
			java -jar target\ejercicio-0.0.1-SNAPSHOT.jar
		6.2 Desde una aplicación para prueba de API (por ejemplo Postman) ejecutar Post al endpoint /api/v1/registro en puerto 8082, ingresando en cuerpo el mensaje json correspondiente, ejemplo de url:
			http://localhost:8082/api/v1/registro

## Consideraciones Generales
	1. Se utilizan tipo de datos UUID como id de registros
 	2. La expresión regular que valida password puede ser configurada en archivo application.properties, etiqueta "re.password" 
 	3. Se incluyen pruebas unitarias
	4. Se agregan pruebas de integración
	5. En subcarpeta /ejercicio/Diagrama se incluye diagrama de solución (Diagrama de Clases)
