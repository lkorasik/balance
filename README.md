
1. Запусти контейнер с БД
```bash
docker-compose up -d
```

```bash
docker run -p 6379:6379 -it redis/redis-stack:latest
```

# Доп инструменты

- Liquibase
- Добавил в схему поле с inital_value для проверки условия на 207%
- Альтернативка через расчет количенства итераий - нельзя реализовать
- Валидация телефона validatePhoneNumber сделана очень просто
