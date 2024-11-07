package com.example.salonchik;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalonchikSozdanie {

    // Экземпляр Stage, который представляет окно приложения
    private Stage stage;
    // Комбобокс для выбора услуги
    @FXML
    private ComboBox<String> serviceComboBox;
    // Метка для отображения цены услуги
    @FXML
    private Label idPrice;
    // Текстовое поле для ввода ID пользователя
    @FXML
    private TextField user;
    // Комбобокс для выбора времени
    @FXML
    private ComboBox<String> scheduleComboBox;
    // Текстовое поле для ввода аллергий
    @FXML
    private TextField allergyTextField;
    // Текстовое поле для отображения описания услуги
    @FXML
    private TextField textService;
    // Текстовое поле для отображения ресурсов, связанных с услугой
    @FXML
    private TextField textResource;
    // Кнопка для создания заказа
    @FXML
    private Button idButton;


    // URL базы данных
    private static final String DB_URL = "jdbc:mysql://localhost:13306/Salon";
    // Имя пользователя для подключения к базе данных
    private static final String DB_USER = "javafxTest";
    // Пароль пользователя для подключения к базе данных
    private static final String DB_PASSWORD = "changeme";


    // Метод инициализации, который загружает услуги и расписание в комбобоксы
    @FXML
    public void initialize() {
        // Загрузка услуг в комбобокс
        loadServicesIntoComboBox();
        // Загрузка расписания в комбобокс
        loadScheduleIntoComboBox();
    }


    // Метод для заполнения данных формы создания заказа
    public void fillSozdanieData(Stage stage, int userId) {
        // Установка текущего окна приложения
        this.stage = stage;
        // Установка идентификатора пользователя в текстовое поле
        user.setText(String.valueOf(userId));
    }


    // Метод обработки нажатия кнопки создания заказа
    @FXML
    private void onCreateButtonClick(ActionEvent event) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Получение нового идентификатора заказа
            String getIdQuery = "SELECT MAX(id) + 1 as newId FROM Orders";
            int newOrderId;
            try (PreparedStatement getIdStatement = connection.prepareStatement(getIdQuery);
                 ResultSet getIdResult = getIdStatement.executeQuery()) {
                newOrderId = getIdResult.next() ? getIdResult.getInt("newId") : 1;
            }
            // Получение идентификатора выбранного сервиса
            String selectedService = serviceComboBox.getSelectionModel().getSelectedItem();
            int serviceId;
            String getServiceIdQuery = "SELECT id FROM Services WHERE name = ?";
            try (PreparedStatement getServiceIdStatement = connection.prepareStatement(getServiceIdQuery)) {
                getServiceIdStatement.setString(1, selectedService);
                try (ResultSet getServiceIdResult = getServiceIdStatement.executeQuery()) {
                    serviceId = getServiceIdResult.next() ? getServiceIdResult.getInt("id") : -1;
                }
            }
            // Получение идентификатора выбранного времени
            String selectedTime = scheduleComboBox.getSelectionModel().getSelectedItem();
            int scheduleId;
            String getScheduleIdQuery = "SELECT id FROM Schedule WHERE time = ?";
            try (PreparedStatement getScheduleIdStatement = connection.prepareStatement(getScheduleIdQuery)) {
                getScheduleIdStatement.setString(1, selectedTime);
                try (ResultSet getScheduleIdResult = getScheduleIdStatement.executeQuery()) {
                    scheduleId = getScheduleIdResult.next() ? getScheduleIdResult.getInt("id") : -1;
                }
            }
            // Получение информации об аллергии
            String allergy = !allergyTextField.getText().isEmpty() ? allergyTextField.getText() : "нет";
            // Получение идентификатора пользователя
            int userId = DatabaseController.idK;
            // Вставка нового заказа в базу данных
            String insertOrderQuery = "INSERT INTO Orders (id, user, service, schedule, allergie) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertOrderStatement = connection.prepareStatement(insertOrderQuery)) {
                insertOrderStatement.setInt(1, newOrderId);
                insertOrderStatement.setInt(2, Integer.parseInt(user.getText()));
                insertOrderStatement.setInt(3, serviceId);
                insertOrderStatement.setInt(4, scheduleId);
                insertOrderStatement.setString(5, allergy);
                insertOrderStatement.executeUpdate();
            }
            // Закрытие окна после успешного создания заказа
        this.stage.close();
        } catch (SQLException e) {
            // Вывод сообщения об ошибке в случае возникновения исключения SQLException
            e.printStackTrace();
        }
    }


    // Метод загрузки услуг в комбобокс
    private void loadServicesIntoComboBox() {
        // Подключение к базе данных
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // SQL-запрос для получения списка услуг
            String query = "SELECT name FROM Services";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                // Добавление каждой услуги в комбобокс
                while (resultSet.next()) {
                    serviceComboBox.getItems().add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            // Вывод сообщения об ошибке в случае возникновения исключения SQLException
            e.printStackTrace();
        }
    }


    // Метод загрузки расписания в комбобокс
    private void loadScheduleIntoComboBox() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Создание SQL-запроса для выборки свободных времени
            String query = "SELECT time FROM Schedule WHERE booked = 0";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                // Проверка на наличие свободных времени
                if (!resultSet.isBeforeFirst()) {
                    showErrorWindow("Нет свободных окон, попробуйте создать заказ позже");
                } else {
                    // Если есть свободные времена, они добавляются в комбобокс
                    while (resultSet.next()) {
                        scheduleComboBox.getItems().add(resultSet.getString("time"));
                    }
                }
            }
        } catch (SQLException e) {
            // Если произошла ошибка при работе с базой данных, она выводится на экран
            e.printStackTrace();
        }
    }


    // Метод отображения окна с ошибкой
    private void showErrorWindow(String message) {
    }


    // Метод загрузки описания услуги в текстовое поле
    private void loadDescriptionIntoTextService(String selectedService) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Создание SQL-запроса для выборки описания выбранной услуги
            String query = "SELECT description FROM Services WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Установка параметра в SQL-запрос
                preparedStatement.setString(1, selectedService);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Если описание найдено, оно устанавливается в текстовое поле
                    if (resultSet.next()) {
                        textService.setText(resultSet.getString("description"));
                    }
                }
            }
        } catch (SQLException e) {
            // Если произошла ошибка при работе с базой данных, она выводится на экран
            e.printStackTrace();
        }
    }


    // Метод загрузки ресурсов, связанных с услугой, в текстовое поле
    private void loadResourcesIntoTextResource(String selectedService) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Создание SQL-запроса для выборки ресурсов, связанных с выбранной услугой
            // Используем JOIN для объединения данных из двух таблиц
            String query = "SELECT r.name FROM Resources r " +
                    "JOIN resources_services rs ON r.id = rs.resources_id " +
                    "WHERE rs.services_id = (SELECT id FROM Services WHERE name = ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Установка параметра в SQL-запрос
                preparedStatement.setString(1, selectedService);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    StringBuilder resources = new StringBuilder();
                    // Получение и объединение всех ресурсов, связанных с выбранной услугой
                    while (resultSet.next()) {
                        resources.append(resultSet.getString("name")).append(", ");
                    }

                    // Убираем последнюю запятую и пробел, если они есть
                    if (resources.length() > 0) {
                        resources.setLength(resources.length() - 2);
                    }
                    // Установка полученных ресурсов в текстовое поле
                    textResource.setText(resources.toString());
                }
            }
        } catch (SQLException e) {
            // Если произошла ошибка при работе с базой данных, она выводится на экран
            e.printStackTrace();
        }
    }


    // Метод обработки изменения выбора услуги в комбобоксе
    @FXML
    private void onServiceComboBoxSelectionChange(ActionEvent event) {
        // Получение выбранной услуги из комбобокса
        String selectedService = serviceComboBox.getSelectionModel().getSelectedItem();
        // Если была выбрана услуга
        if (selectedService != null) {
            // Загрузка описания выбранной услуги в текстовое поле
            loadDescriptionIntoTextService(selectedService);
            // Загрузка ресурсов, связанных с выбранной услугой, в текстовое поле
            loadResourcesIntoTextResource(selectedService);
            // Обновление цены выбранной услуги
            updateIdPrice(selectedService);
        }
    }


    // Метод обновления цены услуги
    private void updateIdPrice(String selectedService) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Создание SQL-запроса для выборки цены выбранной услуги
            String query = "SELECT price FROM Services WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Установка параметра в SQL-запрос
                preparedStatement.setString(1, selectedService);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Если цена найдена, она устанавливается в текстовое поле
                    if (resultSet.next()) {
                        idPrice.setText(String.valueOf(resultSet.getDouble("price")));
                    }
                }
            }
        } catch (SQLException e) {
            // Если произошла ошибка при работе с базой данных, она выводится на экран
            e.printStackTrace();
        }
    }
}