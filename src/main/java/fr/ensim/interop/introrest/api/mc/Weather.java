package fr.ensim.interop.introrest.api.mc;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Weather {

    private Integer id;

    private String main;

    private String description;

}
