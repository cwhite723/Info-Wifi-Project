package model.vo;

import lombok.Getter;

@Getter
public class WifiDistance {
    private Wifi wifi;
    private double distance;

    public WifiDistance(Wifi wifi, double distance) {
        this.wifi = wifi;
        this.distance = distance;
    }
}
