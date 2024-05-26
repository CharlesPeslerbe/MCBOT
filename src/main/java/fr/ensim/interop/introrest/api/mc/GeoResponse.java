package fr.ensim.interop.introrest.api.mc;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class GeoResponse {

    private String name;
    private Map<String, String> localNames;
    private String lat;
    private String lon;
    private String country;
    private String state;
}
