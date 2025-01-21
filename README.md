# TaskManagementSystem
Сервис для CRUD операций доступных авторизованным пользователям с ролевой моделью.

♦ Project Stack:
* Java 17
* Spring Boot 3.4.1
* Spring Data JPA
* postgresql 16.3 - alpine
* Docker
* Lombok
* MapStruct
* Open API
* Swagger   

♦ Инструкция для развертывания проекта в docker:
* Собрать проект с помощью maven команд:
    * `clean`
    * `install`
* Собрать докер образ и запустить докер крнтенер используя команды:
    * `docker build -t student-service . && docker compose up`

♦ Для тестирования приложения написана коллекция тестов в Postman:
   * `Коллекция для тестирования task-management-system для Postman.json`

♦ Все конечные точки можно просмотреть в документации Swagger, для этого переходим:
  * `http://localhost:8080/swagger-ui/index.html`

