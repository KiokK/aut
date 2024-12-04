package by.kiok.motorshow.models.enums;

import lombok.Getter;

@Getter
public enum CarBrand implements DescribableEnum {

    BMW("BMW"),
    AUDI("Audi"),
    VOLKSWAGEN("Volkswagen"),
    TESLA("Tesla"),
    NISSAN("Nissan"),
    LEXUS("Lexus");

    private final String description;

    CarBrand(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
