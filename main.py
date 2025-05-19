import sys
import json
import re
from sentence_transformers import SentenceTransformer, util

# Modelo de embeddings
modelo = SentenceTransformer('all-MiniLM-L6-v2')

# Secciones clave con descripciones semánticas
secciones_esperadas = {
    "título del proyecto": "Nombre corto y claro que represente la solución propuesta",
    "resumen ejecutivo": "Resumen breve del proyecto con objetivos, solución y beneficios",
    "planteamiento del problema": "Descripción clara del problema que se desea resolver",
    "objetivo general": "Meta principal que el proyecto desea alcanzar",
    "objetivos específicos": "Metas concretas que permiten cumplir el objetivo general",
    "justificación técnica": "Razones técnicas que validan el desarrollo del proyecto",
    "alcance del proyecto": "Qué incluye y qué no incluye el proyecto técnicamente",
    "arquitectura del sistema": "Descripción de los componentes y su organización técnica",
    "recursos técnicos requeridos": "Herramientas, software y hardware necesarios",
    "equipo de trabajo": "Roles técnicos asignados y personas involucradas",
    "cronograma de actividades técnicas": "Calendario de fases y tareas técnicas del proyecto",
    "indicadores de éxito técnico": "Métricas que permitirán evaluar si el proyecto es exitoso"
}

tecnologias_relevantes = [
    "spring boot", "react", "angular", "vue", "django", "flask", "node.js",
    "microservicios", "monolítica", "serverless", "mysql", "postgresql",
    "rest api", "graphql", "docker", "kubernetes"
]

palabras_impacto = [
    "escalable", "innovador", "automatización", "accesible", "eficiente",
    "impacto", "usuarios", "productividad", "resiliente", "trazabilidad",
    "reducción de costos", "disponibilidad", "mejora continua"
]

def evaluar_viabilidad(texto_lower):
    puntuacion = 0
    faltantes_impacto = []
    for palabra in palabras_impacto:
        if palabra in texto_lower:
            puntuacion += 1
        else:
            faltantes_impacto.append(palabra)

    if puntuacion >= 8:
        return "alta", "El proyecto menciona múltiples aspectos de impacto, eficiencia y escalabilidad, indicando una alta viabilidad técnica."
    elif puntuacion >= 4:
        return "media", f"El proyecto cubre algunos aspectos relevantes, pero aún puede mejorar su propuesta de valor técnico. Palabras clave faltantes: {', '.join(faltantes_impacto)}."
    else:
        return "baja", f"Faltan elementos fundamentales de impacto y sostenibilidad técnica. Palabras clave faltantes: {', '.join(faltantes_impacto)}."

def detectar_secciones_semanticas(texto):
    parrafos = re.split(r'\n+|(?<=\.)\s+', texto)
    parrafos = [p.strip() for p in parrafos if len(p.strip()) > 20]

    presentes = set()
    emb_parrafos = modelo.encode(parrafos, convert_to_tensor=True)
    emb_secciones = modelo.encode(list(secciones_esperadas.values()), convert_to_tensor=True)

    for idx_sec, nombre_seccion in enumerate(secciones_esperadas.keys()):
        similitudes = util.cos_sim(emb_secciones[idx_sec], emb_parrafos)
        if max(similitudes[0]) > 0.49:
            presentes.add(nombre_seccion)

    faltantes = set(secciones_esperadas.keys()) - presentes
    return list(presentes), list(faltantes)

def analizar_texto(texto):
    texto_lower = texto.lower()
    presentes, faltantes = detectar_secciones_semanticas(texto)
    tecnologias_detectadas = [t for t in tecnologias_relevantes if t in texto_lower]

    cobertura = len(presentes) / len(secciones_esperadas)
    if cobertura >= 0.8:
        madurez = "alta"
        madurez_explicacion = "El proyecto incluye casi todas las secciones clave técnicas requeridas, mostrando una planificación integral."
    elif cobertura >= 0.5:
        madurez = "media"
        madurez_explicacion = "El proyecto cubre varias secciones importantes, pero aún faltan aspectos necesarios para una visión técnica completa."
        # Incluir las sugerencias faltantes directamente en la explicación de la madurez
        if "resumen ejecutivo" not in presentes:
            madurez_explicacion += " Es recomendable desarrollar un resumen ejecutivo más claro y estratégico."
        if "objetivos específicos" not in presentes:
            madurez_explicacion += " También sería útil incluir objetivos específicos cuantificables."
        if "indicadores de éxito técnico" not in presentes:
            madurez_explicacion += " Se sugiere definir indicadores concretos de éxito."
    else:
        madurez = "baja"
        madurez_explicacion = "El proyecto tiene muchas omisiones en su estructura técnica, lo que sugiere inmadurez en la formulación."
        # Incluir las sugerencias faltantes directamente en la explicación de la madurez
        if "resumen ejecutivo" not in presentes:
            madurez_explicacion += " Faltando un resumen ejecutivo que detalle claramente la solución propuesta y sus beneficios."
        if "planteamiento del problema" not in presentes:
            madurez_explicacion += " Es crucial incluir un planteamiento del problema bien definido."
        if "justificación técnica" not in presentes:
            madurez_explicacion += " La justificación técnica debe ser ampliada para explicar cómo las soluciones elegidas resolverán el problema."
        if "arquitectura del sistema" not in presentes:
            madurez_explicacion += " Es necesario definir la arquitectura del sistema para explicar cómo interactúan los componentes técnicos."
        if "equipo de trabajo" not in presentes:
            madurez_explicacion += " Se debe incluir un desglose del equipo de trabajo y las responsabilidades técnicas de cada miembro."
        if "recursos técnicos requeridos" not in presentes:
            madurez_explicacion += " Es importante detallar los recursos técnicos que serán necesarios para el proyecto."
        if "cronograma de actividades técnicas" not in presentes:
            madurez_explicacion += " Debe incluirse un cronograma detallado de las actividades técnicas y los plazos."
        if "indicadores de éxito técnico" not in presentes:
            madurez_explicacion += " Se deben definir métricas claras para evaluar el éxito técnico del proyecto."

    viabilidad, viabilidad_explicacion = evaluar_viabilidad(texto_lower)

    conclusion = "El proyecto presenta un perfil técnico "
    if madurez == "alta" and viabilidad == "alta":
        conclusion += "sólido y con alta probabilidad de aprobación."
    elif madurez == "media" or viabilidad == "media":
        conclusion += "prometedor, pero necesita refuerzo técnico y claridad en beneficios."
    else:
        conclusion += "débil. Requiere mejoras importantes para ser considerado viable."

    resultado = {
        "secciones_detectadas": presentes,
        "secciones_faltantes": faltantes,
        "tecnologias_detectadas": tecnologias_detectadas,
        "madurez_tecnica": {
            "calificacion": madurez,
            "explicacion": madurez_explicacion
        },
        "viabilidad_aparente": {
            "calificacion": viabilidad,
            "explicacion": viabilidad_explicacion
        },
        "conclusion": conclusion
    }

    return resultado

# Entrada por terminal
if __name__ == "__main__":
    if len(sys.argv) > 1:
        texto = sys.argv[1]
        resultado = analizar_texto(texto)
        print(json.dumps(resultado))
    else:
        print(json.dumps({"error": "No se recibió texto como argumento"}, ensure_ascii=False))
