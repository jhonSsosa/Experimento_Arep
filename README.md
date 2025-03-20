# Proyecto: Twitter Clone con Spring Boot y AWS

## Integrantes

- Paula Natalia Paez Vega
- Miguel Camilo Tellez Avila
- John Sebastian Sosa

## Descripción del Proyecto

Este proyecto es un clon básico de Twitter que permite a los usuarios crear publicaciones de hasta 140 caracteres y visualizarlas en un stream público. Se implementa utilizando Spring Boot para el backend y JavaScript para el frontend. El frontend se despliega en Amazon S3 para garantizar su disponibilidad en internet.

---

## 1. Código Fuente

El código fuente del proyecto está disponible en GitHub:

🔗 [Repositorio en GitHub](URL_DEL_REPOSITORIO)

Para clonar el repositorio y ejecutarlo localmente:

```sh
git clone URL_DEL_REPOSITORIO
cd twitter-clone
git checkout nombre-de-la-rama
mvn clean package
java -jar target/twitter-0.0.1-SNAPSHOT.jar
```

---

## 2. Arquitectura del Proyecto

### 2.1. Arquitectura General

El proyecto sigue un diseño monolítico en Spring Boot con un frontend estático en AWS S3. La aplicación se divide en:

- **Backend (Spring Boot)**: Maneja la lógica de negocio y proporciona una API REST.
- **Frontend (JavaScript)**: Consume la API para mostrar y crear posts.
- **AWS S3**: Aloja el frontend y lo hace accesible públicamente.

### 2.2. Modelo de Datos

- `User`: Representa a un usuario del sistema.
- `Post`: Representa una publicación de hasta 140 caracteres.
- `Stream`: Almacena todos los posts creados.

Diagrama de entidades:

```
+--------+       +------+       +---------+
|  User  | <---> | Post | <---> | Stream  |
+--------+       +------+       +---------+
```

### 2.3. Clases Principales

- **`TwitterApplication`**: Clase principal que inicia la aplicación Spring Boot.
- **`TwitterController`**: Controlador que maneja las solicitudes HTTP para crear y obtener posts.
- **`Post`**: Representa un post individual.
- **`User`**: Representa a un usuario de la plataforma.
- **`Stream`**: Contiene la lógica para gestionar el flujo de publicaciones.

---

## 3. Pruebas del Proyecto

### 3.1. Pruebas Manuales

1. Levantar el backend con `java -jar target/twitter-0.0.1-SNAPSHOT.jar`
2. Probar la API con Postman o cURL:
   ```sh
   curl -X POST "http://localhost:8080/twitter/createPost" -H "Content-Type: application/json" -d '{"username":"usuario1","text":"Hola Twitter!"}'
   ```
3. Verificar los posts:
   ```sh
   curl -X GET "http://localhost:8080/twitter/getPosts"
   ```

---

## 4. Video de la Aplicación Funcionando

📹 [Ver Video de la Aplicación](URL_DEL_VIDEO)

---

## 5. Despliegue en AWS

### 5.1. Despliegue del Frontend en AWS S3
1. Crear un bucket S3 y habilitar alojamiento web.
2. Subir los archivos:

3. Acceder a la URL pública del bucket.

---

## 6. Próximos Pasos

- **Desplegar el backend en AWS Lambda con API Gateway.**
- **Separar en microservicios con AWS Lambda.**
- **Agregar autenticación con JWT y AWS Cognito.**

