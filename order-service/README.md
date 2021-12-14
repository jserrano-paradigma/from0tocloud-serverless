# order-service

En este repositorio se encuentra la modificación del microservicio orders de la aplicación de ejemplo cloud-friendly. 
El aplicativo completo y cómo configurarlo puede encontrarse en este [repositorio](https://git.paradigmadigital.com/from0tocloud/step3)  

## Pasos
1. Configurar los pasos indicados en nuestro **servicio Serverless** de ejemplo de este [repositorio](https://git.paradigmadigital.com/from0tocloud/serverless/serverless-aws).
2. Bajarse todos los repositorios y configuración de la **aplicación cloud-friendly** de este [repositorio](https://git.paradigmadigital.com/from0tocloud/step3).  
3. **Sustituir el microservicio orders-service** descargado en el paso 2 por este repositorio.
4. **Configurar las variables** en el fichero src/main/resources/aplication.properties con los valores generados tras el despliegue de la aplicación serverless del paso 1:
    *  spring.service.orders.url - La url del API gateway.
    *  spring.service.orders.apiKey - La api-key de acceso.
    *  spring.service.orders.cognito.clientId - El client-id de la aplicación consumidora generada en Cognito.
    *  spring.service.orders.cognito.clientSecret - El client-secret de la aplicación consumidora generada en Cognito.

## Autor
👤 **Noelia Martín Hernández** 
🏢 **PARADIGMA DIGITAL**