# Demo Spring Boot + Angular

Aplicación web de ejemplo formada por un backend REST con Spring Boot y un frontend con Angular. El proyecto permite gestionar usuarios y sus posts, y sirve como base práctica para trabajar con una arquitectura separada entre API, base de datos y cliente web.

## Tabla de contenidos

- [Descripción general](#descripción-general)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Requisitos previos](#requisitos-previos)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Base de datos](#base-de-datos)
- [Endpoints principales](#endpoints-principales)
- [Frontend](#frontend)
- [Pruebas](#pruebas)
- [Aspectos importantes del proyecto](#aspectos-importantes-del-proyecto)
- [Posibles mejoras futuras](#posibles-mejoras-futuras)

## Descripción general

El objetivo del proyecto es practicar el desarrollo de una aplicación completa con:

- Una API REST desarrollada con Spring Boot.
- Persistencia en MySQL mediante Spring Data JPA.
- Separación entre entidades, DTOs, servicios, repositorios, mappers y controladores.
- Validación de datos de entrada.
- Manejo centralizado de errores.
- Frontend Angular consumiendo la API mediante servicios HTTP.
- Configuración CORS para permitir la comunicación entre Angular y Spring Boot en local.

La aplicación trabaja principalmente con dos recursos:

- **Usuarios**: creación, listado, detalle, edición, borrado, conteo, paginación y filtrado.
- **Posts**: creación de posts asociados a usuarios, listado y detalle.

## Tecnologías utilizadas

### Backend

- Java 25
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- Spring Security
- MySQL
- MapStruct 1.6.3
- Gradle
- JUnit Platform

### Frontend

- Angular 22
- TypeScript 6
- RxJS
- Angular Router
- Angular Forms
- Angular HttpClient
- Vitest
- npm

### Infraestructura local

- Docker y Docker Compose para levantar MySQL de forma sencilla.

## Estructura del proyecto

```text
.
├── backend/
│   ├── src/main/java/com/pruebas/demo/
│   │   ├── config/          # Configuración de seguridad y CORS
│   │   ├── controller/      # Endpoints REST
│   │   ├── dto/             # Objetos de entrada y salida de la API
│   │   ├── entity/          # Entidades JPA
│   │   ├── exception/       # Excepciones y manejador global de errores
│   │   ├── mapper/          # Conversión entre entidades y DTOs
│   │   ├── repository/      # Acceso a base de datos
│   │   ├── service/         # Lógica de negocio
│   │   └── specification/   # Filtros dinámicos con JPA Specifications
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── build.gradle
│   └── docker-compose.yml
│
└── frontend/
    ├── src/app/
    │   ├── model/           # Modelos TypeScript
    │   ├── pages/           # Pantallas de la aplicación
    │   ├── services/        # Servicios HTTP hacia el backend
    │   └── app.routes.ts    # Rutas del frontend
    ├── package.json
    └── angular.json
```

## Requisitos previos

Antes de ejecutar el proyecto conviene tener instalado:

- Java JDK 25.
- Node.js y npm compatibles con Angular 22.
- Docker Desktop, si se quiere levantar MySQL mediante Docker.
- Git, para clonar y versionar el proyecto.
- Un IDE recomendado, por ejemplo IntelliJ IDEA para el backend y Visual Studio Code para el frontend.

Puedes comprobar las versiones instaladas con:

```bash
java --version
node --version
npm --version
docker --version
```

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone 'https://github.com/JotillaFT/Springboot.git'
cd demo
```

### 2. Levantar la base de datos MySQL

Desde la carpeta del backend:

```bash
cd backend
docker compose up -d
```

Esto crea un contenedor MySQL con:

- Base de datos: `springboot_demo`
- Usuario: `root`
- Contraseña: `root`
- Puerto local: `3306`

### 3. Crear las tablas necesarias

El backend está configurado con:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Esto significa que Hibernate comprueba que las tablas existen y coinciden con las entidades, pero no las crea automáticamente. Para preparar la base de datos, ejecuta este SQL en MySQL:

```sql
CREATE TABLE usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255),
  edad INT
);

CREATE TABLE post (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  contenido VARCHAR(255) NOT NULL,
  usuario_id INT NOT NULL,
  CONSTRAINT fk_post_usuario
    FOREIGN KEY (usuario_id)
    REFERENCES usuario(id)
);
```

Opcionalmente, puedes insertar datos de prueba:

```sql
INSERT INTO usuario (nombre, edad) VALUES
('Ana', 28),
('Luis', 34),
('Marta', 22);

INSERT INTO post (titulo, contenido, usuario_id) VALUES
('Primer post', 'Contenido de ejemplo', 1),
('Otro post', 'Más contenido de prueba', 1);
```

### 4. Ejecutar el backend

Desde la carpeta `backend`:

```bash
./gradlew bootRun
```

En Windows PowerShell:

```powershell
.\gradlew.bat bootRun
```

La API quedará disponible en:

```text
http://localhost:8080
```

### 5. Instalar y ejecutar el frontend

En otra terminal, desde la carpeta `frontend`:

```bash
npm install
npm start
```

La aplicación Angular quedará disponible en:

```text
http://localhost:4200
```

## Base de datos

La conexión está definida en `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_demo
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

Para desarrollo local, Docker Compose facilita el arranque de MySQL. En un entorno real, las credenciales deberían configurarse mediante variables de entorno o secretos, no directamente en el archivo de propiedades.

## Endpoints principales

### Usuarios

| Método | Ruta | Descripción |
| --- | --- | --- |
| `GET` | `/usuarios` | Lista todos los usuarios |
| `GET` | `/usuarios?pagina=0&size=10&sort=id&direccion=asc` | Lista usuarios con paginación y ordenación |
| `POST` | `/usuarios` | Crea un usuario |
| `GET` | `/usuarios/{id}` | Obtiene el detalle de un usuario |
| `PUT` | `/usuarios/{id}` | Actualiza un usuario |
| `DELETE` | `/usuarios/{id}` | Borra un usuario |
| `GET` | `/usuarios/count` | Devuelve el número total de usuarios |
| `GET` | `/usuarios/{id}/posts` | Obtiene un usuario junto con sus posts |
| `GET` | `/usuarios/filtro` | Filtra usuarios por nombre, edad mínima, edad máxima, página, tamaño y orden |

Ejemplo de filtro:

```text
GET /usuarios/filtro?nombre=ana&minEdad=18&maxEdad=65&page=0&size=10&orden=id&direccion=asc
```

### Posts

| Método | Ruta | Descripción |
| --- | --- | --- |
| `GET` | `/posts` | Lista todos los posts |
| `GET` | `/posts/{id}` | Obtiene el detalle de un post |
| `POST` | `/usuarios/{id}/posts` | Crea un post asociado a un usuario |

## Frontend

El frontend está organizado por páginas y servicios.

Rutas principales:

| Ruta | Pantalla |
| --- | --- |
| `/` | Home |
| `/usuarios` | Listado de usuarios |
| `/usuarios/nuevo` | Crear usuario |
| `/usuarios/:id` | Detalle de usuario |
| `/usuarios/:id/editar` | Editar usuario |
| `/usuarios/:id/borrar` | Borrar usuario |
| `/usuarios/:id/posts/nuevo` | Crear post para un usuario |

Los servicios de Angular centralizan las llamadas al backend:

- `UsuarioService`: operaciones relacionadas con usuarios.
- `PostService`: creación de posts asociados a usuarios.

Ambos servicios apuntan actualmente a:

```ts
http://localhost:8080
```

## Pruebas

### Backend

Desde `backend`:

```bash
./gradlew test
```

En Windows PowerShell:

```powershell
.\gradlew.bat test
```

### Frontend

Desde `frontend`:

```bash
npm test
```

También puedes generar una build de producción con:

```bash
npm run build
```

## Aspectos importantes del proyecto

### Arquitectura por capas

El backend sigue una separación clara de responsabilidades:

- Los **controladores** reciben peticiones HTTP y devuelven respuestas.
- Los **servicios** contienen la lógica de negocio.
- Los **repositorios** encapsulan el acceso a la base de datos.
- Las **entidades** representan las tablas.
- Los **DTOs** definen qué datos entran y salen por la API.
- Los **mappers** transforman entidades en DTOs.

Esta estructura ayuda a que el proyecto sea más mantenible conforme crece.

### Uso de DTOs

El proyecto diferencia entre entidades internas y objetos expuestos por la API. Esto evita devolver directamente todo el modelo de base de datos y permite controlar mejor la información que recibe el frontend.

### Validación de entrada

Los DTOs de creación usan anotaciones como `@NotBlank`, `@NotNull`, `@Min` y `@Max`. Estas validaciones se activan con `@Valid` en los controladores.

### Manejo global de errores

`GlobalExceptionHandler` centraliza respuestas de error, especialmente:

- Errores de validación.
- Usuarios no encontrados.

Esto evita repetir la misma lógica de error en cada endpoint.

### Filtros dinámicos

`UsuarioSpecification` permite construir consultas dinámicas según los parámetros recibidos. Por ejemplo, se puede filtrar solo por nombre, solo por edad o por varios criterios combinados.

### Paginación y ordenación

El endpoint paginado devuelve un `PageResponse` propio con contenido y metadatos:

- Página actual.
- Tamaño de página.
- Total de elementos.
- Total de páginas.

Este patrón es útil para conectar el backend con tablas o listados paginados en frontend.

### Relación entre usuarios y posts

La relación principal del dominio es:

- Un usuario puede tener muchos posts.
- Cada post pertenece a un único usuario.

En JPA esto se representa con `@OneToMany` en `Usuario` y `@ManyToOne` en `Post`.

### Seguridad y CORS

Spring Security está incluido, pero actualmente todas las rutas están permitidas para facilitar el desarrollo y aprendizaje. CORS está configurado para aceptar peticiones desde Angular en `http://localhost:4200`.

## Posibles mejoras futuras

- Añadir autenticación real con login y roles.
- Mover credenciales de base de datos a variables de entorno.
- Añadir migraciones con Flyway o Liquibase.
- Crear una configuración separada para desarrollo, pruebas y producción.
- Completar operaciones CRUD para posts en la API y en el frontend.
- Añadir más pruebas unitarias e integración.
- Añadir paginación y filtros también en la interfaz Angular.
- Mejorar la gestión de errores visuales en el frontend.
- Preparar despliegue con Docker para backend y frontend.
