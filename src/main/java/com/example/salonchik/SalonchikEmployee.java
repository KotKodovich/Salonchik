package com.example.salonchik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class SalonchikEmployee implements SalonchikController {


    // Атрибут database представляет контроллер базы данных
    private  DatabaseController database = new DatabaseController();

    // Атрибут stage представляет текущее окно приложения
    private  Stage stage;

    // Атрибут userId хранит идентификатор текущего пользователя
    private int userId;


    // Атрибуты userName, userRole и userOrders связаны с элементами пользовательского интерфейса
	@FXML
    private TextField userName;
    @FXML
    private TextField userRole;
    @FXML
    private TableView userOrders;


    // Метод onExitButtonClick обрабатывает событие нажатия кнопки выхода
    @FXML
    protected void onExitButtonClick() {
        stage.close();
    }


    // Метод onTableViewSelect обрабатывает событие выбора элемента в таблице
    @FXML
    protected void onTableViewSelect() {
        // Попытка получения выбранного элемента из таблицы и его приведения к типу DatabaseOrder
        try {
            DatabaseOrder order = (DatabaseOrder) userOrders.getSelectionModel().getSelectedItem();
            // Открываем окно заказа с выбранным элементом
            SalonchikApplication.order(order, "Сотрудник", this);
        } catch (Exception error) {
            // Если произошла ошибка, выводим ее в консоль
            error.printStackTrace();
        }

    }


    // Метод getTableView возвращает таблицу заказов сотрудника
    @Override
    public TableView getTableView(){
        return this.userOrders;
    }


    // Метод fillUserData заполняет данные сотрудника в пользовательском интерфейсе
    public void fillUserData(DatabaseUser user, Stage stage) {
        // Устанавливаем ссылку на Stage
        this.stage = stage;
        // Устанавливаем идентификатор пользователя
        this.userId = user.idK;
        // Заполняем поля имени пользователя и его роли
        userName.setText(user.name);
        userRole.setText(user.role);
        // Получаем заказы пользователя и заполняем таблицу заказов
        userOrders.setItems(retrieveUserOrders(user.idK));
        // Создание и настройка колонок таблицы
        TableColumn idColumn = new TableColumn("Номер");
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn dateColumn = new TableColumn("Дата");
        dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        TableColumn allergieColumn = new TableColumn("Аллергия");
        allergieColumn.setCellValueFactory(new PropertyValueFactory("allergie"));

        TableColumn serviceColumn = new TableColumn("Услуга");
        serviceColumn.setCellValueFactory(new PropertyValueFactory("service"));
        TableColumn descriptionMColumn = new TableColumn("Описание маникюра");
        descriptionMColumn.setCellValueFactory(new PropertyValueFactory("descriptionM"));
        TableColumn priceColumn = new TableColumn("Стоимость");
        priceColumn.setCellValueFactory(new PropertyValueFactory("price"));

        TableColumn timeColumn = new TableColumn("Время записи");
        timeColumn.setCellValueFactory(new PropertyValueFactory("time"));
        TableColumn bookedColumn = new TableColumn("Статуc");
        bookedColumn.setCellValueFactory(new PropertyValueFactory("booked"));
        TableColumn masterColumn = new TableColumn("Мастер");
        masterColumn.setCellValueFactory(new PropertyValueFactory("master"));
        // Устанавливаем все колонки таблицы
        userOrders.getColumns().setAll(idColumn, dateColumn, allergieColumn, serviceColumn, descriptionMColumn, priceColumn, timeColumn, bookedColumn, masterColumn);
        // Обновляем таблицу
        userOrders.refresh();
    }


    // Метод retrieveUserOrders получает список заказов сотрудника из базы данных
    @Override
    public ObservableList retrieveUserOrders(int userId) {
        // Попытка подключения к базе данных
        try {
            this.database.connect();
        } catch (Exception error) {
            // Если подключение не удалось, выводим ошибку в консоль и возвращаем null
            error.printStackTrace();
            return null;
        }
        // Попытка получения заказов сотрудника из базы данных
        try {
                List<DatabaseOrder> orders = this.database.employeeOrders();
                // Получаем список заказов сотрудника и преобразуем его в ObservableList
                return FXCollections.observableList(orders);
        } catch (Exception error) {
            // Если произошла ошибка, выводим ее в консоль и возвращаем null
            error.printStackTrace();
            return null;
        } finally {
            // В любом случае, после выполнения попытки получения заказов, отключаемся от базы данных
            this.database.disconnect();
        }

    }


    // Метод onCreateButtonClick обрабатывает событие нажатия кнопки создания нового заказа
    public void onCreateButtonClick(ActionEvent actionEvent) {
        // Попытка открыть окно выбора
        try {
            SalonchikApplication.vybor();
        } catch (Exception error) {
            // Если произошла ошибка, выводим ее в консоль
            error.printStackTrace();
        }
    }
}
