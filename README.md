# reservation-system
<p align="left">
<img src="https://img.shields.io/badge/spring_boot-v2.6.6-green?style=-the-badge&logo=springboot"  alt="spring-boot"/>
<img src="https://img.shields.io/badge/Gradle-v7.4.1-yellow?style=-the-badge&logo=gradle"  alt="spring-boot"/>

<img src="https://img.shields.io/badge/jpa-v2.6.6-blue?style=-the-badge"  alt="spring-boot"/>

  <img src="https://img.shields.io/badge/Java-11-orange?style=-the-badge&logo=Java&logoColor=white" alt="Java"/>

## Overview
[WIP]
build 전 database를 띄워야합니다.
```
docker run --name mysql8 -e MYSQL_DATABASE=reservation_system -e MYSQL_DATABASE=reservation_system -e MYSQL_ROOT_PASSWORD=mysql123 -e MYSQL_ROOT_HOST='%' -p 3306:3306 --platform linux/amd64 -d mysql:8
```


## Server Architecture

![Server Architecture](https://user-images.githubusercontent.com/24830023/177009623-9c149431-7aba-4a10-b8e7-fd70521acddb.png)


## ERD

![reservationSystem(updated 22 04 20) drawio (1)](https://user-images.githubusercontent.com/24830023/177010088-8ef563f6-ca36-4ab1-8695-a60c635149a3.png)
