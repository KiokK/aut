package by.kiok.motorshow.models.enums;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CarCategoryConverter extends EnumConverter<CarCategory> {

    public CarCategoryConverter() {
        super(CarCategory.class);
    }
}
