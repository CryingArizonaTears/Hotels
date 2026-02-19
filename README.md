Порт 8092

Repository тесты написаны с использованием testcontainers (postgres), так что необходимо иметь установленный докер (либо скипнуть эти тесты).

3 профиля. Менять в application.properties либо в параметрах запуска:
mvn spring-boot:run -Dspring-boot.run.profiles=h2
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
mvn spring-boot:run -Dspring-boot.run.profiles=mysql

h2: логин sa, пароля нет.

Перед запуском конфигурации с postgres/mysql, запустить соответствующие бдшки с параметрами:
postgres: порт 5432. пользователь и пароль postgres.
mysql: порт 3333. пользователь user, пароль root.

Либо просто запустить докеркомпуз с необходимой бдшкой.
