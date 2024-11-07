package com.example.salonchik;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public interface SalonchikController {

    // Метод retrieveUserOrders получает список заказов пользователя из базы данных
    public ObservableList retrieveUserOrders(int userId);

    // Метод getTableView возвращает таблицу заказов пользователя
    public TableView getTableView();
}

