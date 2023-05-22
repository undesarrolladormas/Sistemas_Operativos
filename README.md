# Proyecto Sistemas Operativos
"Simulador" de distintos componentes, realizado en Java

## Compilación
El proyecto requiere `maven` y el JDK 20 para poder construirse.

```bash
# Primero copiamos las dependencias necesarias 
# Esto solo se requiere una vez, a menos que las dependencias cambien
mvn dependency:copy-dependencies

# Ahora empaquetamos
mvn package
```

Tambien es posible realizar todo el proceso anterior en una sola linea de comando
```bash
mvn dependency:copy-dependencies package
# Notese que las dependencias se copiaran cada vez que se empaquete,
# por lo que solo recomiendo esta opcion si es la primera vez que se 
# construye el proyecto
```

El ejecutable del proyecto se encontrará en la carpeta `build` con el nombre `ProyectoSistemas-X.X.jar`.
La carpeta `lib` junto al ejecutable contiene las dependencias del proyecto.