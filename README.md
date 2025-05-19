# Proyecto: Proyect Analicer con Spring Boot, Análisis de Texto en Python y AWS

## Descripción del Proyecto

Este proyecto es un programa que se encarga de analizar y entregar el analisis de los proyectos ejecutados con extensión importante: **analiza automáticamente textos ingresados por los usuarios** utilizando un modelo en Python que evalúa propuestas técnicas en informática. Se ha implementado con **Spring Boot** para el backend y **JavaScript** para el frontend. El análisis de texto se realiza invocando un script Python (`main.py`) desde Java, permitiendo una integración eficiente entre tecnologías.

---

## 1. Código Fuente

[🔗 [Repositorio en GitHub](URL_DEL_REPOSITORIO)](https://github.com/jhonSsosa/Experimento_Arep.git)

Para clonar y ejecutar el proyecto localmente:

```sh
git clone https://github.com/jhonSsosa/Experimento_Arep.git
cd Experimento_Arep
git checkout main
mvn clean package
mvn spring-boot:run
```

> Asegúrate de tener configurado `main.py` y el entorno Python con las dependencias necesarias (por ejemplo, `textblob`, `langdetect`, etc.).

---

## 2. Arquitectura del Proyecto

### 2.1. Arquitectura General

El sistema sigue una arquitectura **híbrida**:

- **Spring Boot (Java)**: Expone la API REST, gestiona la lógica de negocio y orquesta la ejecución del análisis Python.
- **Python**: Evalúa los textos mediante un script `main.py` que procesa el contenido y devuelve un JSON estructurado por `stdout`.
- **JavaScript (Frontend)**: Interactúa con la API y visualiza resultados.

### 2.2. Flujo de Análisis del Texto

1. El usuario crea una publicación con un texto largo.
2. Spring Boot ejecuta `main.py` mediante `ProcessBuilder`, enviando el texto como argumento o vía `stdin`.
3. El script analiza el texto y retorna un JSON con campos como `label`, `confidence`, y `secciones_detectadas`.
4. El backend interpreta el JSON y responde al frontend.

### 2.3. Clases y Archivos Relevantes

- **`TextAnalysisService`** (`com.example.Text.service`): Llama al script Python y parsea su resultado.
- **`TextController`**: Recibe la solicitud, crea el post y agrega la evaluación del texto.
- **`main.py`**: Analiza textos de proyectos técnicos en informática y retorna un JSON interpretado por Java.

---

## 3. Pruebas del Proyecto

### 3.1. Pruebas Manuales

1. Inicia el backend:

```sh
mvn spring-boot:run
```

2. Crea una publicación con un texto técnico para análisis:

```sh
curl -X POST "http://localhost:8080/twitter/createPost" -H "Content-Type: application/json" -d '{
  "username": "usuario1",
  "text": "Sistema de Gestión de Incidencias para Soporte Técnico Universitario. ..."
}'
```

3. Verifica los posts creados:

```sh
curl -X GET "http://localhost:8080/twitter/getPosts"
```

4. (Opcional) Ejecuta el script manualmente desde consola:

```sh
python3 main.py "Sistema de Gestión de Incidencias para Soporte Técnico Universitario. El proyecto busca desarrollar una plataforma web para la gestión de incidencias técnicas en el entorno universitario. Esta solución permitirá registrar, asignar, hacer seguimiento y resolver solicitudes técnicas por parte de estudiantes, profesores y personal administrativo. Se enfoca en mejorar los tiempos de respuesta y la trazabilidad de los tickets. El público objetivo incluye departamentos de TI universitarios. La tecnología principal utilizada será una arquitectura de microservicios con Spring Boot y React. Actualmente, no existe un sistema centralizado para gestionar solicitudes de soporte técnico, lo que genera pérdida de tiempo, falta de trazabilidad y baja satisfacción de los usuarios. Diseñar e implementar una plataforma web eficiente para la gestión y seguimiento de incidencias técnicas en entornos universitarios. Levantar los requerimientos funcionales y técnicos. Diseñar la arquitectura del sistema usando microservicios. Desarrollar los módulos de registro, asignación, seguimiento y cierre de tickets. Implementar métricas básicas de tiempo de resolución. El uso de microservicios permitirá escalar componentes de forma independiente. Además, React facilitará el desarrollo de una interfaz responsiva y moderna. Este sistema cubrirá funcionalidades desde el registro hasta el cierre de incidencias. No incluye funcionalidades para mantenimiento preventivo ni gestión de activos de TI. Se empleará una arquitectura de microservicios con backend en Spring Boot, frontend en React, y comunicación mediante REST APIs. 1 Desarrollador Fullstack, 1 Analista de Requerimientos, 1 Tester QA. Tiempo promedio de atención menor a 24 horas. Disponibilidad del sistema superior al 99%. Cumplimiento del 90% de requerimientos funcionales."
```

> Si el script usa `stdin`, también puedes probarlo con:
```sh
echo "Sistema de Gestión de Incidencias para Soporte Técnico Universitario. El proyecto busca desarrollar una plataforma web para la gestión de incidencias técnicas en el entorno universitario. Esta solución permitirá registrar, asignar, hacer seguimiento y resolver solicitudes técnicas por parte de estudiantes, profesores y personal administrativo. Se enfoca en mejorar los tiempos de respuesta y la trazabilidad de los tickets. El público objetivo incluye departamentos de TI universitarios. La tecnología principal utilizada será una arquitectura de microservicios con Spring Boot y React. Actualmente, no existe un sistema centralizado para gestionar solicitudes de soporte técnico, lo que genera pérdida de tiempo, falta de trazabilidad y baja satisfacción de los usuarios. Diseñar e implementar una plataforma web eficiente para la gestión y seguimiento de incidencias técnicas en entornos universitarios. Levantar los requerimientos funcionales y técnicos. Diseñar la arquitectura del sistema usando microservicios. Desarrollar los módulos de registro, asignación, seguimiento y cierre de tickets. Implementar métricas básicas de tiempo de resolución. El uso de microservicios permitirá escalar componentes de forma independiente. Además, React facilitará el desarrollo de una interfaz responsiva y moderna. Este sistema cubrirá funcionalidades desde el registro hasta el cierre de incidencias. No incluye funcionalidades para mantenimiento preventivo ni gestión de activos de TI. Se empleará una arquitectura de microservicios con backend en Spring Boot, frontend en React, y comunicación mediante REST APIs. 1 Desarrollador Fullstack, 1 Analista de Requerimientos, 1 Tester QA. Tiempo promedio de atención menor a 24 horas. Disponibilidad del sistema superior al 99%. Cumplimiento del 90% de requerimientos funcionales." | python main.py
```

---

## 4. Video de la Aplicación Funcionando
https://drive.google.com/file/d/1gxj78RuF6zkwLVQohsHo1Hvq7CRYsAGl/view?usp=sharing
---

## 5. Dependencias

- Java 17+
- Maven
- Python 3.9+
- Paquetes Python: `textblob`, `langdetect`, `json`, etc.

---
