package com.example.salonchik;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SalonchikAuthorization {


    // Атрибут database представляет контроллер базы данных
    private  DatabaseController database = new DatabaseController();


    // Атрибуты userLogin, userPassword и userResult связаны с элементами пользовательского интерфейса
	@FXML
    private TextField userLogin;
    @FXML
    private PasswordField userPassword;
    @FXML
    private Label userResult;


    // Метод onAuthorizationButtonClick обрабатывает событие нажатия кнопки авторизации
    @FXML
    protected void onAuthorizationButtonClick() {
        // Очищаем текстовое поле результата авторизации
        this.userResult.setText("");
        // Попытка подключения к базе данных
        try {
            this.database.connect();
        } catch (Exception error) {
            // Если подключение не удалось, выводим сообщение об ошибке и прерываем выполнение метода
            this.userResult.setText("Невозможно подключиться к базе данных!");
            return;
        }
        // Попытка авторизации пользователя
        try {
            // Получаем объект пользователя из базы данных
            DatabaseUser user = this.database.authorization(userLogin.getText(), userPassword.getText());
            // Если пользователь найден и его роль "Клиент", открываем окно клиента
            // Если пользователь найден и его роль не "Клиент", открываем окно сотрудника
            if (user != null) {
                if (user.role.equals("Клиент")) {
					SalonchikApplication.client(user);
				} else {
					SalonchikApplication.employee(user);
				}
				return;
            }
            // Если пользователь не найден, выводим сообщение об ошибке
            throw new NullPointerException();
        } catch (Exception error) {
            this.userResult.setText("Пользователь не найден!");
        } finally {
            // В любом случае, после выполнения попытки авторизации, отключаемся от базы данных
			this.database.disconnect();
		}
    }


    // Метод onRegistrationButtonClick обрабатывает событие нажатия кнопки регистрации
    public void onRegistrationButtonClick(ActionEvent actionEvent) {
        try {
            SalonchikApplication.registration();
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
