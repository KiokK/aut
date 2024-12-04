package by.kiok.motorshow.models.enums;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;

public abstract class EnumConverter<T extends Enum<T> & DescribableEnum> implements AttributeConverter<T, String> {

    private final Class<T> enumType;

    protected EnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getDescription() : null;
    }

    @Override
    public T convertToEntityAttribute(String entityData) {
        if (entityData == null) {
            return null;
        }

        return Arrays.stream(enumType.getEnumConstants())
                .filter(e -> e.getDescription().equals(entityData))
                .findFirst()
                .orElse(null);
    }
}
