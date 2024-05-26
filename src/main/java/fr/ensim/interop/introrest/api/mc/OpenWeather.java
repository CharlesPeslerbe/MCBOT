package fr.ensim.interop.introrest.api.mc;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OpenWeather {

    private List<Weather> weather;
    private Main main;

}
