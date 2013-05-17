/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.util.ArrayList;
import java.util.List;
import util.Zaokruzivanje;

/**
 *
 * @author Misa
 */
public class Film {

    private int filmID;
    private int godina;
    private String naslov;
    private List<Integer> ocene;
    private double prosecnaOcena;

    public Film(int filmID, int godina, String naslov) {
        this.filmID = filmID;
        this.godina = godina;
        this.naslov = naslov;
        ocene = new ArrayList<>();
        prosecnaOcena = 0.0;
    }

    public int getFilmID() {
        return filmID;
    }

    public int getGodina() {
        return godina;
    }

    public String getNaslov() {
        return naslov;
    }

    public List<Integer> getOcene() {
        return ocene;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void racunajProsecnuOcenu() {
        int suma = 0;
        int brojac = 0;
        for (Integer ocena : ocene) {
            suma += ocena;
            brojac++;
        }

        if (brojac != 0) {
            prosecnaOcena = Zaokruzivanje.zaokruzi((double) suma / (double) brojac, 2);
        }
    }

    public void dodajOcenu(int ocena) {
        ocene.add(ocena);
    }

    @Override
    public String toString() {
        return "(" + godina + ") " + naslov + ", prosecna ocena: " + prosecnaOcena;
    }
}
