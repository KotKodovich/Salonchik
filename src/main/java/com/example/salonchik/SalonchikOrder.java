package com.example.salonchik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class SalonchikOrder {

    // Атрибут database представляет контроллер базы данных
    private  DatabaseController database = new DatabaseController();

    // Атрибут stage представляет текущее окно приложения
    private  Stage stage;

    // Атрибут order представляет заказ
    private DatabaseOrder order;

    // Атрибут view представляет контроллер пользовательского интерфейса
    private SalonchikController view;


    // Атрибуты user, numb, date, allerg, service, desM, price, time, stat, master связаны с элементами пользовательского интерфейса
    @FXML
    private TextField user;
    @FXML
    private TextField numb;
    @FXML
    private TextField date;
    @FXML
    private TextField allerg;
    @FXML
    private ComboBox service;
    @FXML
    private TextField desM;
    @FXML
    private TextField price;
    @FXML
    private TextField time;
    @FXML
    private TextField stat;
    @FXML
    private TextField master;


    // Метод onExitButtonClick обрабатывает событие нажатия кнопки выхода
    @FXML
    protected void onExitButtonClick() {
        stage.close();
    }


    // Метод fillOrderData заполняет данные заказа в пользовательском интерфейсе
    public void fillOrderData(DatabaseOrder order, Stage stage, String role, SalonchikController view) {
        // Устанавливаем ссылку на Stage и объект заказа
        this.stage = stage;
        this.order = order;
        this.view = view;
        // Заполняем поля данных заказа
        this.user.setText(String.valueOf(order.getUser()));
        this.date.setText(order.getDate());
        this.numb.setText(String.valueOf(order.getId()));
        this.allerg.setText(order.getAllergie());
        this.service.setItems(retrieveServices("name"));
        this.service.setValue(order.getService());
        this.desM.setText(order.getDescriptionM());
        this.price.setText(String.valueOf(order.getPrice()));
        this.time.setText(order.getTime());
        this.stat.setText(order.getBooked());
        this.master.setText(order.getMaster());
	}


    // Метод retrieveMasters получает список мастеров из базы данных
	private ObservableList retrieveMasters() {
		try {
            // Подключение к базе данных
            this.database.connect();
        } catch (Exception error) {
            // Если возникнет ошибка при подключении к базе данных, выводим ошибку и возвращаем null
            error.printStackTrace();
            return null;
        }
		try {
            // Получение списка мастеров из базы данных
            List<String> masters = this.database.masters();
            // Преобразование списка в ObservableList
            return FXCollections.observableList(masters);
        } catch (Exception error) {
            // Если возникнет ошибка при получении списка мастеров, выводим ошибку и возвращаем null
            error.printStackTrace();
			return null;
        } finally {
            // Независимо от того, возникла ли ошибка, отключаемся от базы данных
			this.database.disconnect();
		}

	}


    // Метод retrieveServices получает список услуг из базы данных
    private ObservableList retrieveServices(String field) {
        try {
            // Подключение к базе данных
            this.database.connect();
        } catch (Exception error) {
            // Если возникнет ошибка при подключении к базе данных, выводим ошибку и возвращаем null
            error.printStackTrace();
            return null;
        }
        try {
            // Получение списка услуг из базы данных
            List<String> services = this.database.services(field);
            // Преобразование списка в ObservableList
            return FXCollections.observableList(services);
        } catch (Exception error) {
            // Если возникнет ошибка при подключении к базе данных, выводим ошибку и возвращаем null
            error.printStackTrace();
            return null;
        } finally {
            // Независимо от того, возникла ли ошибка, отключаемся от базы данных
            this.database.disconnect();
        }

    }


    // Метод retrieveSchedule получает расписание из базы данных
    private ObservableList retrieveSchedule(String field) {
        try {
            // Подключение к базе данных
            this.database.connect();
        } catch (Exception error) {
            // Если возникнет ошибка при подключении к базе данных, выводим ошибку и возвращаем null
            error.printStackTrace();
            return null;
        }
        try {
            // Получение расписания из базы данных
            List<String> schedule = this.database.schedule(field);
            // Преобразование расписания в ObservableList
            return FXCollections.observableList(schedule);
        } catch (Exception error) {
            // Если возникнет ошибка при получении расписания, выводим ошибку и возвращаем null
            error.printStackTrace();
            return null;
        } finally {
            // Независимо от того, возникла ли ошибка, отключаемся от базы данных
            this.database.disconnect();
        }

    }


    // Метод onCreateButtonClick обрабатывает событие нажатия кнопки создания нового заказа
    public void onCreateButtonClick(ActionEvent actionEvent) {
        // Создание нового объекта DatabaseOrder
        DatabaseOrder formData = new DatabaseOrder();
        // Заполнение полей объекта данными из формы
        formData.setId(Integer.parseInt(numb.getText()));
        formData.setUser(Integer.parseInt(user.getText()));
        formData.setDate(date.getText());
        formData.setAllergie(allerg.getText());
        formData.setService(String.valueOf(service.getValue()));
        formData.setDescriptionM(String.valueOf(desM.getText()));
        formData.setPrice(Float.parseFloat(String.valueOf(price.getText())));
        formData.setTime(String.valueOf(time.getText()));
        formData.setBooked(String.valueOf(stat.getText()));
        formData.setMaster(String.valueOf(master.getText()));
        try {
            // Подключение к базе данных
            this.database.connect();
        } catch (Exception error) {
            // Если возникнет ошибка при подключении к базе данных, выводим ошибку и возвращаем
            error.printStackTrace();
            return;
        }
        try {
            // Создание нового заказа в базе данных
            this.database.create(formData);
            // Обновление таблицы заказов в пользовательском интерфейсе
            this.view.getTableView().setItems(this.view.retrieveUserOrders(formData.getUser()));
            this.view.getTableView().refresh();
            // Закрытие текущего окна
            this.stage.close();
        } catch (Exception error) {
            // Если возникнет ошибка при создании заказа, выводим ошибку и закрываем окно
            error.printStackTrace();
            this.stage.close();
        } finally {
            // Независимо от того, возникла ли ошибка, отключаемся от базы данных
            this.database.disconnect();
        }
    }


    // Метод onSaveButtonClick обрабатывает событие нажатия кнопки сохранения заказа
    public void onSaveButtonClick(ActionEvent actionEvent) {
        // Создание нового объекта DatabaseOrder
        DatabaseOrder formData = new DatabaseOrder();
        // Заполнение полей объекта данными из формы
        formData.setId(Integer.parseInt(numb.getText()));
        formData.setUser(Integer.parseInt(user.getText()));
        formData.setDate(date.getText());
        formData.setAllergie(allerg.getText());
        formData.setService(String.valueOf(service.getValue()));
        formData.setDescriptionM(String.valueOf(desM.getText()));
        formData.setPrice(Float.parseFloat(String.valueOf(price.getText())));
        formData.setTime(String.valueOf(time.getText()));
        formData.setBooked(String.valueOf(stat.getText()));
        formData.setMaster(String.valueOf(master.getText()));
        try {
            // Подключение к базе данных
            this.database.connect();
        } catch (Exception error) {
            // Если возникнет ошибка при подключении к базе данных, выводим ошибку и возвращаем
            error.printStackTrace();
            return;
        }
        try {
            // Обновление заказа в базе данных
            this.database.update(formData);
            // Обновление таблицы заказов в пользовательском интерфейсе
            this.view.getTableView().setItems(this.view.retrieveUserOrders(formData.getUser()));
            this.view.getTableView().refresh();
            // Закрытие текущего окна
            this.stage.close();
        } catch (Exception error) {
            // Если возникнет ошибка при обновлении заказа, выводим ошибку и закрываем окно
            error.printStackTrace();
            this.stage.close();
        } finally {
            // Независимо от того, возникла ли ошибка, отключаемся от базы данных
            this.database.disconnect();
        }
    }


    // Метод onDeleteButtonClick обрабатывает событие нажатия кнопки удаления заказа
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        try {
            // Подключение к базе данных
            this.database.connect();
        } catch (Exception error) {
            // Если возникнет ошибка при подключении к базе данных, выводим ошибку и возвращаем
            error.printStackTrace();
            return;
        }
        try {
            // Удаление заказа из базы данных
            this.database.delete(Integer.parseInt(numb.getText()));
            // Обновление таблицы заказов в пользовательском интерфейсе
            this.view.getTableView().setItems(this.view.retrieveUserOrders(Integer.parseInt(user.getText())));
            this.view.getTableView().refresh();
            // Закрытие текущего окна
            this.stage.close();
        } catch (Exception error) {
            // Если возникнет ошибка при удалении заказа, выводим ошибку и закрываем окно
            error.printStackTrace();
            this.stage.close();
        } finally {
            // Независимо от того, возникла ли ошибка, отключаемся от базы данных
            this.database.disconnect();
        }
    }
}

