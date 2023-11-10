package pe.msbaek.rfcases.functionalcore;

import lombok.Data;

enum Country {
    ROU, ESP, FRA, SRB, BGR
}

@Data
public class Address {
    private String streetName;
    private Integer streetNumber;
    private String city;
    private Country country;
}