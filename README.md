# Balance

1. Необходимо запустить postgres и redis.

Все можно запустить через docker compose:

```bash
docker-compose up -d
```

2. Настройки прописаны в `application.properties`

БД:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5465/balance
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
```

Redis:

```properties
spring.data.redis.host=localhost
spring.data.redis.port=6380
```

3. Запуск приложения

```bash
mvn spring-boot:run
```

4. Swagger можно открыть по стандартному пути: `http://localhost:8080/swagger-ui/index.html/`

# Swagger 

1. Авторизация в системе

Делаем запрос на `/api/auth/login`

Аккаунты:
```
Пользователь Andrew. Пароль: 123. Почта и телефон:
- q@q.q
- w@w.w 
- e@e.e
- 79220000000
- 79220000001
- 79220000002

Пользователь Ann. Пароль: 123. Почта и телефон:
- r@r.r
- t@t.t
- 79220000003
- 79220000004

Пользователь Ivan. Пароль: 123. Почта и телефон:
- y@y.y
- 79220000005
```

2. Нажми на кнопку "Authorize"
3. Вставь токен
4. Можно делать любые запросы

# Дополнительные инструменты и замечания

- Liquibase

Добавил инструмент для миграции. С помощью него внес в бд базовый набор данных.

- Отошел от схемы в описании задания. 

Добавил в схему поле с inital_value для проверки условия на 207%. Такой подход
позволяет менять с течением времени верхную границу баланса. Рассматривал 
альтернативные варианты.

- Валидация телефона и почты сделана очень просто. В реальном проекте я бы 
делал по-другому. Валидацию номеров и почты можно было сделать на аннотациях. 
Сделал в коде, потому что мне показалось, что так будет проще.
- Для реализации маппера можно было бы использовать MapStruct.
- Через аннотации можно было бы написать документацию для Swagger'а.
- В тестах можно сделать DSL для базовых операций (например, для входа в систему
и получения токена: `String jwt = DSL.authorize(user)`)
