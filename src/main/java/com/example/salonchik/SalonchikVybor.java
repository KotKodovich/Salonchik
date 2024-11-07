package com.example.salonchik;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;

    public class SalonchikVybor {

        // Таблица для отображения списка пользователей
        @FXML
        private TableView<User> tableView;
        // Столбец таблицы для отображения ID пользователя
        @FXML
        private TableColumn<User, Integer> idColumn;
        // Столбец таблицы для отображения имени пользователя
        @FXML
        private TableColumn<User, String> nameColumn;
        // Кнопка для выбора пользователя
        @FXML
        private Button vybr;


        // Соединение с базой данных
        private Connection connection;


        // Метод инициализации, который устанавливает обработчики для столбцов таблицы и загружает данные из базы данных
        public void initialize() {
            // Устанавливаем обработчики для столбцов таблицы
            idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            // Соединение с базой данных
            String jdbcUrl = "jdbc:mysql://localhost:13306/Salon";
            String username = "javafxTest";
            String password = "changeme";

            try {
                // Попытка установить соединение с базой данных
                connection = DriverManager.getConnection(jdbcUrl, username, password);
            } catch (SQLException e) {
                // Если возникает ошибка при соединении с базой данных, выводим ее в консоль
                e.printStackTrace();
            }
            // Загрузка данных из базы данных и установка их в таблицу
            tableView.setItems(getUsersFromDatabase());
            loadData();
        }


        // Метод для получения списка пользователей из базы данных
    private ObservableList<User> getUsersFromDatabase() {
        // Создаем пустой список пользователей
        ObservableList<User> users = FXCollections.observableArrayList();

        // Соединение с базой данных
        String jdbcUrl = "jdbc:mysql://localhost:3306/Salon";
        String username = "javafxTest";
        String password = "changeme";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Попытка установить соединение с базой данных
            String query = "SELECT id, name FROM Users";
            // Подготовка и выполнение запроса
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                // Получение данных из результата запроса
                while (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    // Добавление пользователя в список
                    users.add(new User(userId, name));
                }
            }
        } catch (SQLException e) {
            // Если возникает ошибка при работе с базой данных, выводим ее в консоль
            e.printStackTrace();
        }
        // Возвращаем список пользователей
        return users;
    }


        // Метод обработки нажатия кнопки выбора пользователя
    public void onVybrButtonClick(ActionEvent actionEvent) {
        try {
            User user = (User) tableView.getSelectionModel().getSelectedItem();
            //Если пользователь был успешно выбран, вызывается метод vybr
            SalonchikApplication.vybr(user);
        } catch (Exception error) {
            // Если произошла ошибка, она выводится в консоль
            error.printStackTrace();
        }
    }


        // Внутренний класс для представления пользователя
    public static class User {
            // Свойства пользователя: id и name
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty name;
            // Конструктор класса User
        public User(int id, String name) {
            // Инициализация свойств id и name
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
        }
        // Геттеры для свойств id и name
        public int getId() {
            return id.get();
        }

        public SimpleIntegerProperty idProperty() {
            return id;
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }
    }


        // Метод для загрузки данных в таблицу
    private void loadData() {
        // Попытка создать объект Statement для выполнения SQL запроса
        try {
            Statement statement = connection.createStatement();
            // Выполнение SQL запроса для получения всех пользователей
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            // Получение данных из результата запроса и добавление их в таблицу
            while (resultSet.next()) {
                tableView.getItems().add(new User(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException ex) {
            // Если произошла ошибка при работе с базой данных, она выводится в консоль
            ex.printStackTrace();
        }
    }

}