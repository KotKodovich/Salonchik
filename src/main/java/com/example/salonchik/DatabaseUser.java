package com.example.salonchik;

public class DatabaseUser {

	// Атрибут idK представляет уникальный идентификатор пользователя в базе данных
	public int idK = 0;
	// Атрибут name представляет имя пользователя
    public String name = null;
	// Атрибут role представляет роль пользователя (например, "admin", "user")
    public String role = null;
	// Атрибут login представляет логин пользователя для входа в систему
	public String login = null;
	// Атрибут password представляет пароль пользователя для входа в систему
	public String password = null;

	@Override
	public String toString() {
		return "DatabaseUser{" +
				"idK=" + idK +
				", name='" + name + '\'' +
				", role='" + role + '\'' +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}

