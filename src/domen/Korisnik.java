/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.util.HashMap;
import util.Zaokruzivanje;

/**
 *
 * @author Misa
 */
public class Korisnik {

    private int korisnikID;
    private HashMap<Integer, Integer> ocene;    //<filmID, ocena>
    private HashMap<Integer, Double> slicnosti; //<korinsnikID, stepenSlicnosti>
    private double prosecnaOcena;

    public Korisnik(int korisnikID) {
        this.korisnikID = korisnikID;
        ocene = new HashMap<>();
        slicnosti = new HashMap<>();
        prosecnaOcena = 0;
    }

    public int getKorisnikID() {
        return korisnikID;
    }

    public HashMap<Integer, Integer> getOcene() {
        return ocene;
    }

    public HashMap<Integer, Double> getSlicnosti() {
        return slicnosti;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void racunajProsecnuOcenu() {
        int suma = 0;
        int brojac = 0;
        for (Integer ocena : ocene.values()) {
            suma += ocena;
            brojac++;
        }

        if (brojac != 0) {
            prosecnaOcena = Zaokruzivanje.zaokruzi((double) suma / (double) brojac, 2);
        }
    }

    public void dodajOcenu(int filmID, int ocena) {
        ocene.put(filmID, ocena);
    }

    public void dodajSlicnost(int korisnikID, double stepenSlicnosti) {
        slicnosti.put(korisnikID, stepenSlicnosti);
    }
}
