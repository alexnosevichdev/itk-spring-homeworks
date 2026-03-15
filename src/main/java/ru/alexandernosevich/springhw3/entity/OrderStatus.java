package ru.alexandernosevich.springhw3.entity;

public enum OrderStatus {
    CREATED,
    CONFIRMED,
    BUILDED,
    TRANSFERED_FOR_DELIVERY,
    DELIVERING,
    DELIVERED,
    PAUSED,
    CANCELED
}
