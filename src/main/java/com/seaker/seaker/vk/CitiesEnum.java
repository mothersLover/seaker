package com.seaker.seaker.vk;

public enum CitiesEnum {
    ARSENIEV("2435", "Арсеньев Приморский Край"),
    SAINT_PETERSBURG("2", "Saint Petersburg"),
    MOSCOW("1", "Moscow"),
    VOLGOGRAD("3", "Volgograd"),
    VLADIVOSTOK("37", "Vladivostok"),
    KHABAROVSK("153", "Khabarovsk"),
    KAZAN("60", "Kazan"),
    KALININGRAD("61", "Kaliningrad"),
    NOVOSIBIRSK("99", "Novosibirsk");

    private final String code;
    private final String name;

    CitiesEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
