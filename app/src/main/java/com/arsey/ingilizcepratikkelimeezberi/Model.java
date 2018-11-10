package com.arsey.ingilizcepratikkelimeezberi;

/**
 * Created by mehme on 9.11.2018.
 */

public class Model {
    private String kelime;
    private String anlam;
    private int id;

    public Model(String kelime, String anlam, int id) {
        this.kelime = kelime;
        this.anlam = anlam;
        this.id = id;
    }

    public String getKelime() {
        return kelime;
    }

    public String getAnlam() {
        return anlam;
    }

    public int getId() {
        return id;
    }
}
