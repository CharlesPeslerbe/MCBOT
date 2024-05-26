package fr.ensim.interop.introrest.api.mc;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class OpenWeatherForecast {
    private String cod;
    private int message;
    private int cnt;
    private List<Forecast> list;
    private City city;

    // getters and setters for each field
    @Setter
    @Getter
    public static class Forecast {
        private long dt;
        private Main main;
        private List<Weather> weather;
        private Clouds clouds;
        private Wind wind;
        private int visibility;
        private double pop;
        private Rain rain;
        private Sys sys;
        private String dt_txt;

        // getters and setters for each field
        @Setter
        @Getter
        public static class Main {
            private double temp;
            private double feels_like;
            private double temp_min;
            private double temp_max;
            private int pressure;
            private int sea_level;
            private int grnd_level;
            private int humidity;
            private double temp_kf;

            // getters and setters for each field
        }
        @Setter
        @Getter
        public static class Weather {
            private int id;
            private String main;
            private String description;
            private String icon;

            // getters and setters for each field
        }
        @Setter
        @Getter
        public static class Clouds {
            private int all;

            // getters and setters for each field
        }
        @Setter
        @Getter
        public static class Wind {
            private double speed;
            private int deg;
            private double gust;

            // getters and setters for each field
        }
        @Setter
        @Getter
        public static class Rain {
            private double _3h;

            // getters and setters for each field
        }
        @Setter
        @Getter
        public static class Sys {
            private String pod;

            // getters and setters for each field
        }
    }
    @Setter
    @Getter
    public static class City {
        private int id;
        private String name;
        private Coord coord;
        private String country;
        private int population;
        private int timezone;
        private int sunrise;
        private int sunset;

        // getters and setters for each field
        @Setter
        @Getter
        public static class Coord {
            private double lat;
            private double lon;

            // getters and setters for each field
        }
    }
}
