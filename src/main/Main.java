/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import kontroler.Kontroler;

/**
 *
 * @author Misa
 */
public class Main {

    public static void main(String[] args) {
        Kontroler.getInstance().ucitajFilmove();     //kreira filmove iz fajla movie_titles.txt

//        Kontroler.getInstance().ucitajKorisnike();    //kreira korisnike iz fajla ratings.all
        Kontroler.getInstance().ucitajKorisnikeITest(); //kreira korisnike za trening i korisnike za test

        Kontroler.getInstance().obradiKorisnike();     //obradjuje statistiku za korisnike
        Kontroler.getInstance().obradiFilmove();     //obradjuje statistiku za filmove
        Kontroler.getInstance().odrediSlicnost();    //pronalazi slicne korisnike

//        Kontroler.getInstance().predvidiPCC();   //vrsi predvidjanje za korisnike iz test seta
//        Kontroler.getInstance().izracunajRMSE(); //racuna preciznost
        Kontroler.getInstance().predvidiPCC(55555);

        Kontroler.getInstance().preporuka(55555);     //daje preporuku
        //korisnik 55555 je dao ocenu 5 Staloneovim filmovima (Rambo: First Blood Part II, Rambo III: Ultimate Edition,
        //Rocky II, Rocky III, Rocky V), a ocenu 4 su dobile komedije (Chappelle's Show: Season 1, Airplane II: The Sequel,
        //Mr. Bean: The Whole Bean, The Pink Panther)
    }
}
