package com.example.salonchik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class SalonchikApplication extends Application {

    // Метод main запускает приложение
	public static void main(String[] args) {
        launch();
    }


    // Метод vybor открывает окно выбора пользователя
    public static void vybor() throws IOException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/photo/icon.png"));
        FXMLLoader loader = new FXMLLoader(SalonchikApplication.class.getResource("salonchik-vybor.fxml"));
        Scene scene = new Scene(loader.load(), 400, 400);
        stage.setTitle("Пользователь");
        stage.setScene(scene);
        stage.show();
    }

    // Метод vybr открывает окно создания нового пользователя
    public static void vybr(SalonchikVybor.User user) throws IOException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/photo/icon.png"));
        FXMLLoader loader = new FXMLLoader(SalonchikApplication.class.getResource("salonchik-sozdanie.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        stage.setTitle("Пользователь");
        stage.setScene(scene);
        stage.show();

        SalonchikSozdanie controller = loader.getController();
        controller.fillSozdanieData(stage, user.getId());
    }


    // Метод start запускает приложение и открывает окно авторизации
    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image("/photo/icon.png"));
        FXMLLoader loader = new FXMLLoader(SalonchikApplication.class.getResource("salonchik-authorization.fxml"));
        Scene scene = new Scene(loader.load(), 320, 480);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    // Метод client открывает окно клиента
    public static void client(DatabaseUser user) throws IOException {
        FXMLLoader loader = new FXMLLoader(SalonchikApplication.class.getResource("salonchik-client.fxml"));
        Scene scene = new Scene(loader.load(), 640, 480);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/photo/icon.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Клиент");
        stage.setScene(scene);
        stage.show();
		
		SalonchikClient controller = loader.getController();
		controller.fillUserData(user, stage);
    }


    // Метод employee открывает окно сотрудника
	public static void employee(DatabaseUser user) throws IOException {

        FXMLLoader loader = new FXMLLoader(SalonchikApplication.class.getResource("salonchik-employee.fxml"));
        Scene scene = new Scene(loader.load(), 640, 480);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/photo/icon.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Сотрудник");
        stage.setScene(scene);
        stage.show();
		
		SalonchikEmployee controller = loader.getController();
		controller.fillUserData(user, stage);
    }


    // Метод order открывает окно заказа
    public static void order(DatabaseOrder order, String role, SalonchikController view) throws IOException {

        FXMLLoader loader = new FXMLLoader(SalonchikApplication.class.getResource("salonchik-order.fxml"));
        Scene scene = new Scene(loader.load(), 640, 600);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/photo/icon.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Заказ");
        stage.setScene(scene);
        stage.show();

        SalonchikOrder controller = loader.getController();
        controller.fillOrderData(order, stage, role, view);
    }


    // Метод sozdanie открывает окно создания нового заказа
    public static void sozdanie(int userId) throws IOException {

        FXMLLoader loader = new FXMLLoader(SalonchikApplication.class.getResource("salonchik-sozdanie.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/photo/icon.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Новый заказ");
        stage.setScene(scene);
        stage.show();

        SalonchikSozdanie controller = loader.getController();
        controller.fillSozdanieData(stage, userId);
    }


    // Метод registration открывает окно регистрации
    public static void registration() throws IOException {

        FXMLLoader loader = new FXMLLoader(SalonchikApplication.class.getResource("salonchik-registration.fxml"));
        Scene scene = new Scene(loader.load(), 320, 520);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/photo/icon.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Регистрация");
        stage.setScene(scene);
        stage.show();

    }
}
