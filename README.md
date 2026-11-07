# Spring Boot : Application PetClinic

## Features  

### 1.  Unit Test  --> Tag v1.0.0

mvn test -Dspring.profiles.active=h2

---

## Pruebas unitarias por entidad

Se implementaron los servicios faltantes siguiendo el patrón de `PetService` y sus
pruebas con JUnit 5 + Spring Boot (`@SpringBootTest`).

| Caso | Servicio | Clase de prueba | Pruebas |
|------|----------|-----------------|---------|
| 1 | `VetService` | `VetServiceTest` | 11 |
| 2 | `OwnerService` | `OwnerServiceTest` | 9 |
| 3 | `SpecialtyService` | `SpecialtyServiceTest` | 9 |
| 4 | `VisitService` | `VisitServiceTest` | 12 |
| 5 | `PetTypeService` (`types`) | `PetTypeServiceTest` | 9 |
| 6 | `VetSpecialtyService` (`vet_specialties`) | `VetSpecialtyServiceTest` | 12 |

### Ejecutar las pruebas

Requiere Java 21:

```bash
export JAVA_HOME=/home/jean/.local/share/mise/installs/java/21.0.13+9.0.LTS
export PATH=$JAVA_HOME/bin:$PATH
./mvnw clean test
```

Resultado esperado:

```
Tests run: 77, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### Evidencias

- Salida de consola de `./mvnw clean test`.
- Reportes por clase en `target/surefire-reports/*.txt`.
- Historial Git: `git log --all --graph --oneline`.

### Integrantes y ramas

> Completar con los nombres reales del equipo.

| Integrante | Rama | Pruebas realizadas |
|------------|------|--------------------|
| (nombre) | `feature/vet-service-tests` | VetServiceTest |
| (nombre) | `feature/owner-service-tests` | OwnerServiceTest |
| (nombre) | `feature/specialty-service-tests` | SpecialtyServiceTest |
| (nombre) | `feature/visit-service-tests` | VisitServiceTest |
| (nombre) | `feature/type-service-tests` | PetTypeServiceTest |
| (nombre) | `feature/vet-specialty-service-tests` | VetSpecialtyServiceTest |
