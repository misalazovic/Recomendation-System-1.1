/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.FileWriter;
import java.io.IOException;
import kontroler.Kontroler;

/**
 *
 * @author Misa
 */
public class Main {

    public static void main(String[] args) {
        FileWriter izvestaj;
        try {
            izvestaj = new FileWriter("resursi/izvestaj.txt");
            Kontroler.getInstance().ucitajFilmove();     //kreira filmove iz fajla movie_titles.txt
//            Kontroler.getInstance().ucitajKorisnike();    //kreira korisnike iz fajla ratings.training
            Kontroler.getInstance().ucitajKorisnikeITest();
            Kontroler.getInstance().obradiKorisnike();     //obradjuje statistiku za korisnike
            Kontroler.getInstance().obradiFilmove();     //obradjuje statistiku za filmove
            Kontroler.getInstance().odrediSlicnost(izvestaj);    //pronalazi slicne korisnike
            Kontroler.getInstance().predvidiPCC();   //vrsi predvidjanje
//            Kontroler.getInstance().preporuka(k.getKorisnikID());     //daje preporuku
            Kontroler.getInstance().izracunajRMSE(); //racuna preciznost

            izvestaj.close();
        } catch (IOException ex) {
            System.err.println("Greska u izvestaju: " + ex.getMessage());
        }
    }
}
