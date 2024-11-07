package com.example.salonchik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SalonchikRegistration {

    // Атрибут database представляет контроллер базы данных
    private DatabaseController database = new DatabaseController();

    // Атрибут stage представляет текущее окно приложения
    private Stage stage;


    // Атрибуты userName, userLogin, userPassword, userResult связаны с элементами пользовательского интерфейса
    @FXML
    private TextField userName;
    @FXML
    private TextField userLogin;
    @FXML
    private TextField userPassword;
    @FXML
    private Label userResult;


    // Метод onRegButtonClick обрабатывает событие нажатия кнопки регистрации
    @FXML
    private void onRegButtonClick() {
        // Очищаем текстовое поле userResult
        this.userResult.setText("");
        // Пытаемся подключиться к базе данных
        try {
            this.database.connect();
        } catch (Exception error) {
            // Если возникнет ошибка при подключении к базе данных, выводим сообщение об ошибке
            this.userResult.setText("Невозможно подключиться к базе данных!");
            return;
        }
        // Пытаемся зарегистрировать нового пользователя в базе данных
        try {
            boolean result = this.database.registration(userName.getText(), userLogin.getText(), userPassword.getText());
            // Если регистрация прошла успешно, выводим сообщение об успехе
            this.userResult.setText("Регистрация прошла успешно!");
        } catch (Exception error) {
            // Если возникнет ошибка при регистрации, выводим сообщение об ошибке
            this.userResult.setText("Не получилось зарегистрироваться!");
        } finally {
            // В конце отключаемся от базы данных
            this.database.disconnect();
        }


    }
}
