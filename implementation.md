# Реалізація об’єктно-реляційного відображення

## 1. Створення бази даних MySQL

Було створено базу даних `test_db` у MySQL для зберігання даних.

```
CREATE DATABASE test_db;
USE test_db;

CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  email VARCHAR(100)
);

CREATE TABLE products (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  price DECIMAL(10,2)
);
```


## 2. Створення Java-проєкту

Для реалізації завдання створено консольний Java-проєкт без використання IDE. Усі дії виконувались через термінал macOS.

```
mkdir dao-demo
cd dao-demo
mkdir -p src/model src/dao src/test

```


**Структура проєкту:**

```

dao-demo/
├── src/
│   ├── model/      — bean-класи (модель таблиць)
│   ├── dao/        — реалізація DAO
│   └── test/       — тестовий клас

````

## 3. Створення таблиці `users`

У базі даних `test_db` створено таблицю `users` зі структурою:

- `id` — унікальний ідентифікатор
- `name` — ім’я користувача
- `email` — електронна пошта

Ця таблиця використовується для демонстрації операцій вставки та пошуку.

```
CREATE DATABASE test_db;
USE test_db;

CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  email VARCHAR(100)
);

```

## 4. Створення bean-класу

Створено клас `User` (у `src/model/User.java`), який описує один запис таблиці `users`.

```java
package model;

public class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}

````

## 5. Розробка DAO-інфраструктури

У пакеті `src/dao/` реалізовано клас `UserDAO.java`, який містить методи для:

* вставки нового користувача
* пошуку користувача за ім'ям

```java
package dao;

import model.User;
import java.sql.*;
import java.util.*;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO users (name, email) VALUES (?, ?)"
        );
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getEmail());
        stmt.executeUpdate();
    }

    public List<User> findByName(String name) throws SQLException {
        List<User>

```

## 6. Тестування DAO

У `src/test/TestDAO.java` створено тестовий клас `TestDAO`, який:

* підключається до бази `test_db`
* створює нові записи
* виконує пошук за ім’ям
* використовує `UserDAO` для роботи з базою

```
package test;

import dao.UserDAO;
import model.User;
import java.sql.*;
import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test_db";
        String user = "root";
        String password = "your_password"; // замініть на актуальний

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            UserDAO userDAO = new UserDAO(conn);

            // Додавання користувачів
            userDAO.insert(new User(0, "Олег", "oleh@example.com"));
            userDAO.insert(new User(0, "Анна", "anna@example.com"));

            // Пошук
            List<User> users = userDAO.findByName("Олег");
            for (User u : users) {
                System.out.println("Found: " + u.getName() + ", " + u.getEmail());
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

```


## 7. Запуск програми

**Компіляція:**

```bash
javac -cp "lib/mysql-connector-j-<version>.jar" src/**/*.java
```

**Запуск:**

```bash
java -cp "lib/mysql-connector-j-<version>.jar:src" test.TestDAO
```

**Перевірка результатів у MySQL:**

```sql
javac -cp "lib/mysql-connector-j-9.3.0.jar" \
src/model/User.java src/dao/UserDAO.java src/test/TestDAO.java
______________________________________________________________

java -cp "lib/mysql-connector-j-9.3.0.jar:src" test.TestDAO

______________________________________________________________

Found: Oleh | oleh@example.com
Found: Oleh | oleh@example.com

```
<img width="266" alt="image" src="https://github.com/user-attachments/assets/57236c0b-c5b6-4ecd-a2ae-3d2b4cd9c7db" />



## 8. Висновок

* Реалізовано шаблон **DAO (Data Access Object)** для взаємодії з базою MySQL.
* Створено базу `test_db` та таблицю `users`.
* Описано модель `User`.
* Реалізовано клас `UserDAO` з методами вставки та пошуку.
* Проведено тестування з реальними даними.
* Програма успішно читає та записує інформацію до бази.

Це підтверджує правильність реалізації архітектурного патерна DAO.

```

