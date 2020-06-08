# Guitar repair service

## Installation and running using Docker
**Requirements**
```
JDK 1.8
Apache Tomcat
Apache Maven
Docker
```
**Running the project**
```
Clone project to your local repository
From project root folder run - "docker build -t guitar-service . "
Run - "docker run -dp 3308:3306 --rm --name guitar-service -e MYSQL_ROOT_PASSWORD=123456 guitar-service"
From project root folder run - "mvn spring-boot:run"

Use http://localhost:8088/ to view website

To stop springboot app use CTRL+C
To stop and delete mysql container run - "docker stop guitar-service"
```


Live preview http://guitar-repair.us-east-2.elasticbeanstalk.com/

Option №3  
```
Repair agency system. The user can create a request for product repair. 
The manager can accept the request indicating the price, or reject the request, indicating the reason. 
The master can complete the order accepted by the Manager. 
The user can leave feedback on completed order.
```

Вариант №3  
```
Система Ремонтное Агенство. Пользователь может создать заявку на ремонт изделия. 
Менеджер может принять заявку указав цену, либо отклонить заявку, указав причину. 
Мастер может выполнить принятую Менеджером заявку. 
Пользователь может оставить Отзыв о выполненных работах. 
```
## Installation and running
**Requirements**
```
JDK 1.8
Apache Tomcat
Apache Maven
MySQL
```

**Running the project**
```
Clone project to your local repository

Run scripts from /resources/db/ folder to create database and tables (dbCreation.sql) 
and to insert data (dbInsertion.sql)

Update DB login and password in /resources/application.properties

From project root folder run - mvn spring-boot:run

Use http://localhost:8088/ to view website
```
**Default users**
```
ADMIN otec@gmail.com - 12345678
MASTER top@gmail.com - 12345678
CLIENT neil@gmail.com - 12345678
```
## Tests coverage
![image](http://i.piccy.info/i9/27034e108c847143c0e2e7331e7908f2/1581960642/49007/1360567/tests.jpg
)
## Main Page
![image](http://i.piccy.info/i9/94945f3b7a6579486aad3bbc2a7ee99b/1580829639/53445/1360567/guitar.jpg)
