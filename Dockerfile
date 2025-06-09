FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

# Copia los archivos necesarios
COPY ./db/empleados.db /app/db/empleados.db
COPY ./out/production/Servidor/rmi /app/rmi
COPY ./out/production/Servidor/InterfaceRemote.jar /app/
COPY ./lib/sqlite-jdbc-3.50.1.0.jar /app/lib/
COPY server.policy /app/server.policy

WORKDIR /app

# Exponer el puerto del registro RMI y el puerto fijo del stub
EXPOSE 1099
EXPOSE 1100

# Ejecutar el servidor con los JARs necesarios y configurar el hostname RMI
CMD java -Djava.security.policy=server.policy \
         -Djava.rmi.server.hostname=${RMI_HOST} \
         -cp /app:/app/InterfaceRemote.jar:/app/lib/sqlite-jdbc-3.50.1.0.jar \
         rmi.servidor.test.TestServidor
