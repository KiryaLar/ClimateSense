# ClimateSense

ClimateSense – REST API-сервис на Spring Boot для мониторинга климатических параметров, таких как температура и осадки, на основе данных с сенсоров.

Приложение позволяет:
✔️ Регистрировать сенсоры 📡
✔️ Добавлять измерения температуры и осадков 🌡️🌧️
✔️ Получать список всех измерений 📊
✔️ Фильтровать измерения по сенсору 🔍
✔️ Анализировать количество дождливых дней за период ☔

## Содержание
- [Описание](#описание)
- [Технологии](#технологии)
- [Сборка и запуск](#сборка-и-запуск)
- [Использование API](#использование-api)
  - [Регистрация сенсора](#регистрация-сенсора)
  - [Получение измерений по конкретному сенсору](#получение-измерений-по-конкретному-сенсору)
  - [Добавление измерения](#добавление-измерения)
  - [Получение всех измерений](#получение-всех-измерений)
  - [Получение количества дождливых дней](#получение-количества-дождливых-дней)

## Описание
Приложение **RestApp** позволяет:
1. Регистрировать новые сенсоры.
2. Добавлять измерения (температура, дождь).
3. Запрашивать список измерений или фильтровать по сенсору.
4. Получать количество дождливых дней за весь период или в заданном диапазоне дат.

## Структура проекта
- **`RestAppApplication.java`** – точка входа в Spring Boot приложение.  
- **`controllers`** – REST-контроллеры, обрабатывают входящие HTTP-запросы.  
- **`dto`** – объекты передачи данных, используемые для сериализации/десериализации JSON.  
- **`exceptions`** – кастомные исключения.  
- **`models`** – сущности (Entity) приложения.  
- **`repositories`** – интерфейсы Spring Data JPA для взаимодействия с базой данных.  
- **`services`** – бизнес-логика приложения.  
- **`util`** – вспомогательные классы (например, для валидации, ответов об ошибках).  
- **`validators`** – отдельные валидаторы для сенсоров и измерений.  

## Технологии
- **Java 17+**  
- **Spring Boot 3+**  
- **Spring Data JPA**  
- **Hibernate**  
- **Maven**

  
## Сборка и запуск

### 1️⃣ Клонирование репозитория
```sh
git clone https://github.com/username/RestApp.git
cd ClimateSense
```

### 2️⃣ Настройка базы данных
- Убедитесь, что у вас установлена **PostgreSQL**.
- Настройте подключение к БД, изменив **src/main/resources/application.properties.origin**.
- Создайте таблицы с помощью SQL-скрипта:


### 3️⃣ Сборка и запуск проекта
```sh
mvn clean install
mvn spring-boot:run
```

После успешного запуска приложение будет доступно по адресу:
👉 [http://localhost:8080](http://localhost:8080)

# Использование API 
## Регистрация сенсора  
**Метод:** POST /sensors/registration  
**Тело (JSON):**
```json
{
  "name": "Mega sensor"
}
```

Пример запроса (Java RestTemplate):
```java
RestTemplate restTemplate = new RestTemplate();
String url = "http://localhost:8080/sensors/registration";

Map<String, String> sensorMap = new HashMap<>();
sensorMap.put("name", "Mega sensor");

ResponseEntity<String> response = restTemplate.postForEntity(url, sensorMap, String.class);
System.out.println(response.getStatusCode());
```

Ответ:

200 OK при успешной регистрации.
400 Bad Request при ошибке (например, имя сенсора уже занято).

Получение измерений по конкретному сенсору  
Метод: GET /sensor/measurements?name={sensorName}

### Пример вызова:
GET /sensor/measurements?name=Mega%20sensor

**Ответ (JSON-массив):**
```json
[
  {
    "temperature": 23.4,
    "raining": false,
    "sensor": {
      "name": "Mega sensor"
    }
  },
  ...
]
```

Статус:

200 OK при успехе.
400 Bad Request / 404 при неправильном параметре.

## Добавление измерения  
**Метод:** POST /measurements/add  
**Тело (JSON):**
```json
{
  "temperature": 25.5,
  "raining": true,
  "sensor": {
    "name": "Mega sensor"
  }
}
```

Ответ:

200 OK при успехе.
400 Bad Request при ошибке валидации (неверное поле temperature и т.д.).

## Получение всех измерений  
Метод: GET /measurements  

**Ответ (JSON-массив):**
```json
[
  {
    "temperature": 25.5,
    "raining": true,
    "sensor": {
      "name": "Mega sensor"
    }
  },
  ...
]
```

Статус: 200 OK.

## Получение количества дождливых дней  
**Метод:** GET /measurements/rainyDaysCount  
**Параметры:** (необязательные) startDate, endDate в формате YYYY-MM-DD.

**Примеры:**
GET /measurements/rainyDaysCount (за всё время)
GET /measurements/rainyDaysCount?startDate=2025-01-01&endDate=2025-12-31

**Ответ (JSON):**
```json
{
  "rainy days": 10,
  "startDate": "2025-01-01",
  "endDate": "2025-12-31"
}
```
