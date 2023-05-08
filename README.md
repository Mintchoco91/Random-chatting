# Random-chatting
Random-chatting-prototype
- Native Android Application


# Spec

 - Android
 
   - DEV language : Android Java
   - SDK Version: 32
   - Image Save Storage : Firebase Cloud Storage
   - Repository : https://github.com/Mintchoco91/Random_Chatting (현재 Repository)

 - Rest API Server
 
   - Hosting : AWS
   - DEV language : PHP 7.4.3
   - Database : JawsDB Maria
   - Repository : https://github.com/Mintchoco91/random-chatting-server

 - Socket Server 
 
   - Hosting : AWS
   - DEV language : Nodejs 16
   - Socket.IO Server Version : 4.5.2
   - Socket.IO Client Version : 2.1.0
   - Socket.IO Compatibility Info : https://socketio.github.io/socket.io-client-java/installation.html
   - Repository : https://github.com/Mintchoco91/random-chatting-chat-server

# Docker Repository

- Apache2 + PHP 
  - https://hub.docker.com/repository/docker/boy0221/apache_php/general
  
- MariaDb
  - https://hub.docker.com/repository/docker/boy0221/mariadb/general
  
- Socket Server 
  - https://hub.docker.com/repository/docker/boy0221/random-chatting-chat-server/general
 
- PHP API Server 코드 및 Android 코드는 git으로 관리. 


# Run (Directory /home 기준)

    $ cd /home

    $ docker build -t boy0221/apache_php:0.1 .
    $ docker run -d -p 80:80 -v /home:/var/www/html boy0221/apache_php:0.1

* http://52.197.9.121/ 접속 확인
#
    $ docker build -t random-chatting-chat-server:0.1 .
    $ docker run -d -p 3000:3000 random-chatting-chat-server:0.1
* http://52.197.9.121:3000/ 접속 확인

#
    $ git pull https://github.com/Mintchoco91/random-chatting-server.git

