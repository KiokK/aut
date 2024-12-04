package by.kiok.motorshow.models.enums;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CarBrandConverter extends EnumConverter<CarBrand> {

    public CarBrandConverter() {
        super(CarBrand.class);
    }
}
