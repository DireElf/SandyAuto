# SandyBox

**SandyBox** — это приложение, предназначенное для практики навыков автоматизации тестирования.

## Системные требования

- **Java** 11 или выше
- Для запуска на Windows используйте файл `run-on-windows.bat`
- Для запуска на Linux выполните в директории проекта

```bash
chmod +x run-on-linux.sh
./run-on-linux.sh
```

## Функциональность текущего релиза

### Главная страница
- Меню сворачивается в «сэндвич» при уменьшении размеров окна.

### Страница "Students"
- Добавление студентов в базу данных через модальное окно. Список курсов выбирается из доступных.
- Добавление случайного студента с помощью Drag-n-drop в таблицу студентов.
- Очистка списка студентов кнопкой **Clear** с подтверждением через alert.
- Пагинация списка студентов.
- Переход на вкладку курса со списком студентов по клику на название курса в таблице студентов.

### Страница "Courses"
- Добавление нового курса через модальное окно (новый курс сразу доступен для назначения студентов).
- Отображение курсов во вкладках.
- По клику на вкладку курса отображается список его студентов.

### Страница "REST API"
- Описание эндпойнтов сервиса **Students**.
- Описание эндпойнтов сервиса **Courses**.

## Особенности
- Приложение работает на порту **2024** (например, http://localhost:2024/).
- И UI, и REST-сервисы используют одну и ту же базу данных (встроенная H2).
- Консоль H2 доступна по адресу `/h2-console` (например, http://localhost:2024/h2-console). Логин: `user`, пароль: `pass`.
- База данных создается заново при каждом запуске приложения.
- После создания в БД автоматически добавляются 5 случайных студентов и курсов (это количество можно изменить в классе `DataInitializer`).
- Каждый студент имеет уникальный email, а каждый курс - уникальное название
- Через API невозможно добавить курс с отсутствующими в БД студентами
- Курс **"No course"** присутствует в базе по умолчанию и может быть удален только с помощью прямого SQL-запроса либо JDBC
- Курсу **"No course"** невозможно изменить название (только с помощью прямого SQL-запроса либо JDBC)

---
