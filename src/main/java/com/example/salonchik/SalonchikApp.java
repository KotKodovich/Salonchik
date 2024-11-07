package com.example.salonchik;

public class SalonchikApp {

    // Атрибут loggedInUser представляет пользователя, который в настоящее время вошел в систему
    private static DatabaseUser loggedInUser;

    // Метод client устанавливает пользователя как текущего пользователя, который вошел в систему
    public static void client(DatabaseUser user) {
        loggedInUser = user;
    }

    // Метод employee устанавливает пользователя как текущего пользователя, который вошел в систему
    public static void employee(DatabaseUser user) {
        loggedInUser = user;
    }

    // Метод getLoggedInUserId возвращает идентификатор текущего пользователя, который вошел в систему
    // Если пользователь не вошел в систему, метод возвращает -1
    public static int getLoggedInUserId() {
        return loggedInUser != null ? loggedInUser.idK : -1;
    }
}

