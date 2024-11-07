package com.example.salonchik;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseController {

    // Константы для настройки подключения к базе данных
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:13306/Salon";
    private static final String DATABASE_USERNAME = "javafxTest";
    private static final String DATABASE_PASSWORD = "changeme";
    private static final String DATABASE_MAX_POOL = "250";


    // Объект соединения с базой данных
    private Connection connection;
    public static int idK;


    // Объект для хранения свойств подключения к базе данных
    private Properties properties;


    // Метод для инициализации свойств подключения к базе данных
    private Properties properties() {
        if (this.properties == null) {
            this.properties = new Properties();
            this.properties.setProperty("user", DATABASE_USERNAME);
            this.properties.setProperty("password", DATABASE_PASSWORD);
            this.properties.setProperty("MaxPooledStatements", DATABASE_MAX_POOL);
        }
        return this.properties;
    }


    // Метод для установки соединения с базой данных
    public Connection connect() {
        if (this.connection == null) {
            try {
                // Загрузка драйвера и установка соединения
                Class.forName(DATABASE_DRIVER);
                this.connection = DriverManager.getConnection(DATABASE_URL, properties());
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
        return this.connection;
    }


    // Метод для разрыва соединения с базой данных
    public void disconnect() {
        if (this.connection != null) {
            try {
                // Закрытие соединения
                this.connection.close();
                this.connection = null;
            } catch (Exception error) {
                error.printStackTrace();
            }
        }
    }


    // Метод для авторизации пользователя
    public DatabaseUser authorization(String login, String password) {
        // Проверка наличия соединения с базой данных
        if (this.connection == null) {
            return null;
        }
        try {
            // Подготовка и выполнение SQL-запроса для авторизации
            String query = "SELECT * FROM `Users` WHERE `Users`.`login` = ? AND `Users`.`password` = ?;";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Установка параметров запроса
            statement.setString(1, login);
            statement.setString(2, password);
            // Выполнение SQL запроса и получение результата
            ResultSet result = statement.executeQuery();

            // Обработка результата запроса
            if (result.next()) {
                DatabaseUser user = new DatabaseUser();
                // Заполнение объекта пользователя данными из результата запроса
                user.idK       = result.getInt("id");
                user.name     = result.getString("name");
                user.role     = result.getString("role");
                user.login    = result.getString("login");
                user.password = result.getString("password");
                // Закрытие PreparedStatement
                statement.close();
                // Возвращение объекта пользователя
                return user;
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idK = resultSet.getInt("id"); // Получить id
            }
            statement.close();
            throw new NullPointerException("User with given login is not found in database!");
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение null в случае ошибки
            return null;
        }
    }


    // Метод для регистрации пользователя
    public boolean registration (String name, String login, String password) throws SQLException {
        // Проверка наличия соединения с базой данных
        if (this.connection == null) {
            return false;
        }
        try {
            // Создание SQL запроса для вставки нового пользователя
            String query = "INSERT INTO `Users` (`name`, `login`, `password`) VALUES (?, ?, ?);";
            // Создание PreparedStatement для выполнения SQL запроса
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Установка параметров запроса
            statement.setString(1, name);
            statement.setString(2, login);
            statement.setString(3, password);
            // Выполнение SQL запроса и получение результата
            boolean result = statement.execute();
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение результата
            return result;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Выбрасывание исключения SQLException
            throw new SQLException(error.getMessage());
        }
    }


    // Метод для получения заказов клиента
    public List<DatabaseOrder> clientOrders(int id) {
        // Проверка наличия соединения с базой данных
        if (this.connection == null) {
            return new ArrayList<DatabaseOrder>();
        }
        try {
            // Подготовка и выполнение SQL-запроса для получения заказов клиента
            String query = "SELECT * FROM `Orders` LEFT JOIN `Services` ON `Orders`.`service` = `Services`.`id` LEFT JOIN `Schedule` ON `Orders`.`schedule` = `Schedule`.`id` LEFT JOIN `Masters` ON `Schedule`.`master` = `Masters`.`id` WHERE `Orders`.`user` = ?;";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Установка параметра запроса
            statement.setInt(1, id);
            // Выполнение SQL запроса и получение результата
            ResultSet result = statement.executeQuery();
            // Создание списка для хранения заказов
            List<DatabaseOrder> orders = new ArrayList<DatabaseOrder>();
            // Обработка результата запроса
            while (result.next()) {
                orders.add(fillDatabaseOrder(result));
            }
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение списка заказов
            return orders;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение пустого списка в случае ошибки
            return new ArrayList<DatabaseOrder>();
        }
    }


    // Метод для получения заказов сотрудников
    public List<DatabaseOrder> employeeOrders() {
        // Проверка наличия соединения с базой данных
        if (this.connection == null) {
            return new ArrayList<DatabaseOrder>();
        }
        try {
            // Подготовка и выполнение SQL-запроса для получения заказов сотрудников
            String query = "SELECT * FROM `Orders` LEFT JOIN `Services` ON `Orders`.`service` = `Services`.`id` LEFT JOIN `Schedule` ON `Orders`.`schedule` = `Schedule`.`id` LEFT JOIN `Masters` ON `Schedule`.`master` = `Masters`.`id`;";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Выполнение SQL запроса и получение результата
            ResultSet result = statement.executeQuery();
            // Создание списка для хранения заказов
            List<DatabaseOrder> orders = new ArrayList<DatabaseOrder>();
            // Обработка результата запроса
            while (result.next()) {
                orders.add(fillDatabaseOrder(result));
            }
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение списка заказов
            return orders;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение пустого списка в случае ошибки
            return new ArrayList<DatabaseOrder>();
        }
    }


    // Метод для заполнения объекта DatabaseOrder из ResultSet
    private DatabaseOrder fillDatabaseOrder(ResultSet result) throws SQLException {
        // Создание нового объекта DatabaseOrder
        DatabaseOrder order = new DatabaseOrder();
        // Заполнение объекта DatabaseOrder данными из ResultSet
        order.setId(result.getInt(1));
        order.setUser(result.getInt(2));
        order.setDate(result.getString(3));
        order.setAllergie(result.getString(4));
        order.setService(result.getString(9));
        order.setDescriptionM(result.getString(10));
        order.setPrice(result.getFloat(11));
        order.setTime(result.getString(14));
        order.setBooked(result.getBoolean(15)?"Занято":"Свободно");
        order.setMaster(result.getString(17));
        // Возвращение объекта DatabaseOrder
        return order;
    }


    // Метод для получения списка мастеров
    public List<String> masters() {
        // Попытка выполнения операции
        try {
            // Подготовка и выполнение SQL-запроса для получения списка мастеров
            String query = "SELECT * FROM `Masters`;";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Выполнение SQL запроса и получение результата
            ResultSet result = statement.executeQuery();
            // Создание списка для хранения мастеров
            List<String> masters = new ArrayList<>();
            // Обработка результата запроса
            while (result.next()) {
                masters.add(result.getString("fullname"));
            }
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение списка мастеров
            return masters;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение пустого списка в случае ошибки
            return new ArrayList<>();
        }
    }


    // Метод для получения списка услуг
    public List<String> services(String field) {
        try {
            // Подготовка и выполнение SQL-запроса для получения списка услуг
            String query = "SELECT * FROM `Services`;";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Выполнение SQL запроса и получение результата
            ResultSet result = statement.executeQuery();
            // Создание списка для хранения услуг
            List<String> services = new ArrayList<>();
            // Обработка результата запроса
            while (result.next()) {
                services.add(result.getString(field));
            }
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение списка услуг
            return services;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение пустого списка в случае ошибки
            return new ArrayList<>();
        }
    }


    // Метод для получения списка ресурсов
    public List<String> resources(String field) {
        try {
            // Подготовка и выполнение SQL-запроса для получения списка ресурсов
            String query = "SELECT * FROM `Resources`;";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Выполнение SQL запроса и получение результата
            ResultSet result = statement.executeQuery();
            // Создание списка для хранения ресурсов
            List<String> resources = new ArrayList<>();
            // Обработка результата запроса
            while (result.next()) {
                resources.add(result.getString(field));
            }
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение списка ресурсов
            return resources;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение пустого списка в случае ошибки
            return new ArrayList<>();
        }
    }


    // Метод для получения расписания
    public List<String> schedule(String field) {
        try {
            // Подготовка и выполнение SQL-запроса для получения списка расписания
            String query = "SELECT * FROM `Schedule`;";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Выполнение SQL запроса и получение результата
            ResultSet result = statement.executeQuery();
            // Создание списка для хранения расписания
            List<String> schedule = new ArrayList<>();
            // Обработка результата запроса
            while (result.next()) {
                schedule.add(result.getString(field));
            }
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение списка расписания
            return schedule;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение пустого списка в случае ошибки
            return new ArrayList<>();
        }
    }


    // Метод для создания нового заказа
    public boolean create (DatabaseOrder order) {
        try {
            // Подготовка и выполнение SQL-запроса для создания нового заказа
            String query = "CALL CreateNewOrder(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Установка значений параметров запроса
            statement.setInt(1, order.getUser());
            statement.setString(2, order.getDate());
            statement.setString(3, order.getAllergie());
            statement.setString(4, order.getCriteria());
            statement.setString(5, order.getService());
            statement.setString(6, order.getDescriptionM());
            statement.setFloat(7, order.getPrice());
            statement.setString(8, order.getResource());
            statement.setString(9, order.getDescriptionR());
            statement.setString(10, order.getTime());
            statement.setBoolean(11, order.getBooked().equalsIgnoreCase("Занято")?true:false);
            statement.setString(12, order.getMaster());
            // Выполнение SQL запроса и получение результата
            boolean result = statement.execute();
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение результата операции
            return result ;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение false в случае ошибки
            return false;
        }
    }


    // Метод для обновления существующего заказа
    public boolean update (DatabaseOrder order) {
        try {
            // Подготовка и выполнение SQL-запроса для обновления существующего заказа
            String query = "CALL UpdateExistOrder(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Установка значений параметров запроса
            statement.setInt(1, order.getId());
            statement.setString(2, order.getDate());
            statement.setString(3, order.getAllergie());
            statement.setString(4, order.getService());
            statement.setString(5, order.getDescriptionM());
            statement.setFloat(6, order.getPrice());
            statement.setString(7, order.getResource());
            statement.setString(8, order.getDescriptionR());
            statement.setString(9, order.getTime());
            statement.setBoolean(10, order.getBooked().equalsIgnoreCase("Занято")?true:false);
            statement.setString(11, order.getMaster());
            // Выполнение SQL запроса и получение результата
            int result = statement.executeUpdate();
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение результата операции
            return result > 0;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение false в случае ошибки
            return false;
        }
    }


    // Метод для удаления заказа
    public boolean delete (int id) {
        try {
            // Подготовка и выполнение SQL-запроса для удаления заказа
            String query = "DELETE FROM `Orders` WHERE `Orders`.`id` = ?;";
            PreparedStatement statement = this.connection.prepareStatement(query);
            // Установка значения параметра запроса
            statement.setInt(1, id);
            // Выполнение SQL запроса и получение результата
            boolean result = statement.execute();
            // Закрытие PreparedStatement
            statement.close();
            // Возвращение результата операции
            return result;
        } catch (Exception error) {
            // Вывод информации об ошибке
            error.printStackTrace();
            // Возвращение false в случае ошибки
            return false;

        }
    }
}
