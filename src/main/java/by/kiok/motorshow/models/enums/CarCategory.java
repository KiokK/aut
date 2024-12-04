package by.kiok.motorshow.models.enums;
import lombok.Getter;

@Getter
public enum CarCategory implements DescribableEnum {

    CONVERTIBLE("Convertible"),
    SPORT_CAR("Sport car"),
    ELECTRIC("Electric"),
    TRUCK("Truck"),
    VAN("Van"),
    BUS("Bus");

    private final String description;

    CarCategory(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
