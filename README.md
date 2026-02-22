# VideoAppBlocker
VideoAppBlocker es una aplicación Android diseñada para ayudar a los usuarios a controlar el tiempo que pasan en aplicaciones de adictivas, ofreciendo herramientas de bloqueo, recordatorios y modos de restricción para fomentar un uso más saludable del móvil.

## 📝 Características

- Bloqueo de aplicaciones: Permite restringir el acceso a aplicaciones seleccionadas (sin límite en el nº de apps).

- Modo estricto (opcional) con temporizador: Configura periodos de bloqueo y permite solo el acceso limitado a apps.

- Permisos de accesibilidad: Solicita los permisos necesarios para monitorear y restringir aplicaciones.

- Interfaz intuitiva: Pantallas sencillas para activar/desactivar permisos y configurar bloqueos.

- Notificaciones de recordatorio: Alertas cuando se intenta abrir una app bloqueada.

- Video opcional: Si quieres, puedes añadir un pequeño video/gif recordándote por qué no debes entrar a la app bloqueada.

## 📲 Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/tuusuario/VideoAppBlocker.git
```
2. Abre el proyecto en Android Studio.

3. Conecta un dispositivo o usa un emulador Android.

4. Compila y ejecuta la app:
   - Build > Make Project
   - Run > Run 'app'
---
***O también puedes***  
1. Descargar la última versión del apk release.
2. Instalar el apk en su dispositivo


## 🔧 Uso
1. Al abrir la app, concede los permisos solicitados.
2. Selecciona las aplicaciones que deseas bloquear.
3. Configura el modo de bloqueo (normal o estricto).
4. Activa el temporizador si deseas establecer periodos específicos de restricción.
5. ¡Listo! La app empezará a bloquear el acceso a las apps seleccionadas.

***Opcional***: puedes seleccionar un vídeo para poder recordarte, si abres la app que no debes, por qué debes seguir sin entrar.

## 🛠️ Tecnologías

- **Kotlin** – Lenguaje principal.

- **Jetpack Compose** – Interfaz de usuario declarativa.

- **ViewModel** – Manejo del estado de las pantallas.

- **DataStore / SharedPreferences** – Almacenamiento de configuraciones y temporizadores.

## 📂 Estructura del proyecto

- ui/screens – Pantallas principales de la app.

- ui/components – Componentes reutilizables de UI.

- viewmodel – Lógica de negocio y estado de la app.

- utils – Funciones de ayuda y utilidades.

## 🔑 Contribuciones

Las contribuciones son bienvenidas. Puedes:

1. Hacer un fork del repositorio.

2. Crear tu branch (***git checkout -b feature/nueva-funcionalidad***).

3. Hacer commit de tus cambios (***git commit -m 'Agrega nueva funcionalidad'***).

4. Hacer push al branch (***git push origin feature/nueva-funcionalidad***).

5. Abrir un **Pull Request**.