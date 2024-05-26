package fr.ensim.interop.introrest.api.mc;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class GeoResponse {

    private String name;
    private Map<String, String> localNames;
    @Setter
    @Getter
    private String lat;
    @Setter
    @Getter
    private String lon;
    private String country;
    private String state;
}
