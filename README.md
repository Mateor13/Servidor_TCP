> docker build -t rmi-server .


> docker run -e RMI_HOST=192.168.100.245 -p 1099:1099 -p 1100:1100 rmi-server
