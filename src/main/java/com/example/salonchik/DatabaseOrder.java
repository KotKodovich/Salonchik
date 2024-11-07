package com.example.salonchik;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleFloatProperty;

public class DatabaseOrder {


	// Объекты свойств, которые представляют атрибуты заказа
	public SimpleIntegerProperty id;
	public SimpleIntegerProperty user;
	public SimpleStringProperty date;
	public SimpleStringProperty allergie;
	public SimpleStringProperty criteria;
	public SimpleStringProperty service;
	public SimpleStringProperty descriptionM;
	public SimpleFloatProperty price;
	public SimpleStringProperty resource;
	public SimpleStringProperty descriptionR;
	public SimpleStringProperty time;
	public SimpleStringProperty booked;
	public SimpleStringProperty master;


	// Конструктор, который инициализирует все свойства
	public DatabaseOrder() {
		this.id = new SimpleIntegerProperty();
		this.user = new SimpleIntegerProperty();
		this.date = new SimpleStringProperty();
		this.allergie = new SimpleStringProperty();
		this.criteria = new SimpleStringProperty();
		this.service = new SimpleStringProperty();
		this.descriptionM = new SimpleStringProperty();
		this.price = new SimpleFloatProperty();
		this.resource = new SimpleStringProperty();
		this.descriptionR = new SimpleStringProperty();
		this.time = new SimpleStringProperty();
		this.booked = new SimpleStringProperty();
		this.master = new SimpleStringProperty();
	}


	// Методы get и set для каждого свойства
	// Эти методы используются для получения и установки значений свойств
	// Методы property возвращают объект свойства, который может быть использован для привязки к пользовательскому интерфейсу
	public int getId() {
		return id.get();
	}

	public SimpleIntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public int getUser() {
		return user.get();
	}

	public SimpleIntegerProperty userProperty() {
		return user;
	}

	public void setUser(int user) {
		this.user.set(user);
	}

	public String getDate() {
		return date.get();
	}

	public SimpleStringProperty dateProperty() {
		return date;
	}

	public void setDate(String date) {
		this.date.set(date);
	}

	public String getAllergie() {
		return allergie.get();
	}

	public SimpleStringProperty allergieProperty() {
		return allergie;
	}

	public void setAllergie(String allergie) {
		this.allergie.set(allergie);
	}

	public String getCriteria() {
		return criteria.get();
	}

	public SimpleStringProperty criteriaProperty() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria.set(criteria);
	}

	public String getService() {
		return service.get();
	}

	public SimpleStringProperty serviceProperty() {
		return service;
	}

	public void setService(String service) {
		this.service.set(service);
	}

	public String getDescriptionM() {
		return descriptionM.get();
	}

	public SimpleStringProperty descriptionMProperty() {
		return descriptionM;
	}

	public void setDescriptionM(String descriptionM) {
		this.descriptionM.set(descriptionM);
	}

	public float getPrice() {
		return price.get();
	}

	public SimpleFloatProperty priceProperty() {
		return price;
	}

	public void setPrice(float price) {
		this.price.set(price);
	}

	public String getResource() {
		return resource.get();
	}

	public SimpleStringProperty resourceProperty() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource.set(resource);
	}

	public String getDescriptionR() {
		return descriptionR.get();
	}

	public SimpleStringProperty descriptionRProperty() {
		return descriptionR;
	}

	public void setDescriptionR(String descriptionR) {
		this.descriptionR.set(descriptionR);
	}

	public String getTime() {
		return time.get();
	}

	public SimpleStringProperty timeProperty() {
		return time;
	}

	public void setTime(String time) {
		this.time.set(time);
	}

	public String getBooked() {
		return booked.get();
	}

	public SimpleStringProperty bookedProperty() {
		return booked;
	}

	public void setBooked(String booked) {
		this.booked.set(booked);
	}

	public String getMaster() {
		return master.get();
	}

	public SimpleStringProperty masterProperty() {
		return master;
	}

	public void setMaster(String master) {
		this.master.set(master);
	}


	// Метод toString возвращает строковое представление объекта DatabaseOrder
	// Это может быть полезно для отладки или для вывода информации о заказе
	@Override
	public String toString() {
		return "DatabaseOrder{" +
				"id=" + id +
				", user=" + user +
				", date=" + date +
				", allergie=" + allergie +
				", criteria=" + criteria +
				", service=" + service +
				", descriptionM=" + descriptionM +
				", price=" + price +
				", resource=" + resource +
				", descriptionR=" + descriptionR +
				", time=" + time +
				", booked=" + booked +
				", master=" + master +
				'}';
	}
}

