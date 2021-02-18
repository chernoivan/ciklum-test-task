package com.samurai.task.starter.enums;

public enum ProductStatus {
    OUT_OF_STOCK("out_of_stock"),
    IN_STOCK("in_stock"),
    RUNNING_LOW("running_low"),
    NOT_FOUND("not_found");

    private final String value;

    ProductStatus(final String value) {
        this.value = value;
    }

    public static ProductStatus fromValue(String value) {
        for (final ProductStatus dayOfWeek : values()) {
            if (dayOfWeek.value.equalsIgnoreCase(value)) {
                return dayOfWeek;
            }
        }
        return NOT_FOUND;
    }
}
