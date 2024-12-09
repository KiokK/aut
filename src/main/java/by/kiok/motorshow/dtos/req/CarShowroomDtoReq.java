package by.kiok.motorshow.dtos.req;

import jakarta.validation.constraints.NotBlank;

public class CarShowroomDtoReq {

    @NotBlank
    public String name;

    @NotBlank
    public String address;
}
