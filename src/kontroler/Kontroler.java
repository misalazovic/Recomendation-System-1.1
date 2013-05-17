/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Film;
import domen.Korisnik;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Misa
 */
public class Kontroler {
    
    private static Kontroler instance = null;
    private HashMap<Integer, Film> filmovi;   //<filmID, Film>
    private HashMap<Integer, Korisnik> korisnici;   //<korisnikID, Korisnik>
    private HashMap<Integer, Korisnik> korisniciTestSet;   //<korisnikID, Korisnik>

    private Kontroler() {
        filmovi = new HashMap<>();
        korisnici = new HashMap<>();
        korisniciTestSet = new HashMap<>();
    }
    
    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }
    
    public HashMap<Integer, Film> getFilmovi() {
        return filmovi;
    }
    
    public HashMap<Integer, Korisnik> getKorisnici() {
        return korisnici;
    }
    
    public HashMap<Integer, Korisnik> getKorisniciTestSet() {
        return korisniciTestSet;
    }

    /**
     * Parsira fajl "movie_titles.txt", kreira objekte klase Film i dodaje ih u
     * HashMapu filmovi
     */
    public void ucitajFilmove() {
        System.out.println("Ucitavanje filmova");
        try {
            Scanner in = new Scanner(new File("resursi/movie_titles.txt"));     //filmID,godina,naslov
            int brojacFilmova = 0;
            
            while (in.hasNext()) {
                String linija = in.nextLine();
                String[] split = linija.split(",");
                
                int id = Integer.parseInt(split[0]);
                int godina = Integer.parseInt(split[1]);
                String naslov = split[2];
                
                Film f = new Film(id, godina, naslov);
                filmovi.put(f.getFilmID(), f);
                brojacFilmova++;
                
                if (brojacFilmova % 300 == 0) {
                    System.out.print("_");
                }
            }
            
            in.close();
            System.out.println(" DONE\nBroj filmova: " + brojacFilmova + "\n");
        } catch (FileNotFoundException e) {
            System.err.println("GRESKA! Fajl resursi/movie_titles.txt nije pronadjen");
        }
    }

    /**
     * Parsira fajl "ratings.training", kreira objekat klase Korisnik, dodaje ga
     * u HashMapu korisnici i dodaje njegove ocene u HashMapu filmovi
     */
    public void ucitajKorisnike() {
        System.out.println("Ucitavanje korisnika");
        try {
            Scanner in = new Scanner(new File("resursi/ratings.training"));     //filmID korisnikID ocena
            int brojacOcena = 0;
            
            while (in.hasNext()) {
                String linija = in.nextLine();
                String[] split = linija.split(" ");
                
                int filmID = Integer.parseInt(split[0]);
                int korisnikID = Integer.parseInt(split[1]);
                int ocena = Integer.parseInt(split[2]);
                
                dodajKorisnika(korisnikID);
                
                korisnici.get(korisnikID).dodajOcenu(filmID, ocena);
                filmovi.get(filmID).dodajOcenu(ocena);
                brojacOcena++;
                
                if (brojacOcena % 120000 == 0) {
                    System.out.print("_");
                }
            }
            
            in.close();
            System.out.println(" DONE\nBroj ocena: " + brojacOcena + "\n");
        } catch (FileNotFoundException e) {
            System.err.println("GRESKA! Fajl resursi/ratings.training nije pronadjen");
        }
    }

    /**
     * Parsira fajl "ratings.training", u 20% slucajeva kreira objekat klase
     * KorisnikTest i dodaje ga u HashMapu korisniciTestSet, a u ostalim
     * slucajevima kreira objekat klase Korisnik, dodaje ga u HashMapu korisnici
     * i dodaje njegove ocene u HashMapu filmovi
     */
    public void ucitajKorisnikeITest() {
        System.out.println("Ucitavanje korisnika i testcase-ova");
        try {
            Scanner in = new Scanner(new File("resursi/ratings.training"));     //filmID korisnikID ocena
            int brojacOcena = 0;
            int brojacTestova = 0;
            
            while (in.hasNext()) {
                int broj = (int) (Math.random() * 10 + 1); // slucajan broj od 1 do 10
                if (broj > 2) {
                    String linija = in.nextLine();
                    String[] split = linija.split(" ");
                    
                    int filmID = Integer.parseInt(split[0]);
                    int korisnikID = Integer.parseInt(split[1]);
                    int ocena = Integer.parseInt(split[2]);
                    
                    dodajKorisnika(korisnikID);
                    
                    korisnici.get(korisnikID).dodajOcenu(filmID, ocena);
                    filmovi.get(filmID).dodajOcenu(ocena);
                    brojacOcena++;
                    
                    if (brojacOcena % 96000 == 0) {
                        System.out.print("_");
                    }
                } else {
                    String linija = in.nextLine();
                    String[] split = linija.split(" ");
                    
                    int filmID = Integer.parseInt(split[0]);
                    int korisnikID = Integer.parseInt(split[1]);
                    int ocena = Integer.parseInt(split[2]);
                    
                    dodajKorisnikaUTest(korisnikID);
                    
                    korisniciTestSet.get(korisnikID).dodajOcenu(filmID, ocena);
                    brojacTestova++;
                    
                    if (brojacTestova % 24000 == 0) {
                        System.out.print("*");
                    }
                }
            }
            
            in.close();
            System.out.println(" DONE\nBroj ocena: " + brojacOcena + "\nBroj testcase-ova: " + brojacTestova + "\n");
        } catch (FileNotFoundException e) {
            System.err.println("GRESKA! Fajl resursi/ratings.training nije pronadjen");
        }
    }

    /**
     * Obradjuje informacije o korisnicima nakon sto su ucitane sve ocene
     */
    public void obradiKorisnike() {
        System.out.println("Obrada korisnika");
        int brojacKorisnika = 0;
        
        for (Korisnik korisnik : korisnici.values()) {      //korisnici(<korisnikID, Korisnik>)
            korisnik.racunajProsecnuOcenu();
            brojacKorisnika++;
            
            if (brojacKorisnika % 400 == 0) {
                System.out.print("_");
            }
        }
        
        System.out.println(" DONE\nBroj korisnika: " + brojacKorisnika + "\n");
    }

    /**
     * Obradjuje informacije o filmovima nakon sto su ucitane sve ocene
     */
    public void obradiFilmove() {
        System.out.println("Obrada filmova");
        int brojacFilmova = 0;
        
        for (Film film : filmovi.values()) {        //filmovi(<filmID, Film>)
            film.racunajProsecnuOcenu();
            brojacFilmova++;
            
            if (brojacFilmova % 300 == 0) {
                System.out.print("_");
            }
        }
        
        System.out.println(" DONE\nBroj filmova: " + brojacFilmova + "\n");
    }

    /**
     * Odredjuje stepen slicnosti dva korisnika i sastavlja izvestaj
     *
     * @param izvestaj fajl sa izvestajem
     */
    public void odrediSlicnost(FileWriter izvestaj) {
        int max = 100;//7500;               //100      //min-max za broj ocena konkretnog korisnika
        int min = 3;//60;                   //3
        double minSlicnost = 0.2;//0.01;    //0.2      //minimalni stepen slicnosti
        int minBrojSlicnihOcena = 10;//35;  //10       //minimalni broj filmova ocenjenih od strane oba korisnika

        System.out.println("Racunanje slicnosti");
        try {
            izvestaj.write("Parametri");
            izvestaj.write('\n');
            izvestaj.write('\t');
            izvestaj.write("Broj ocena korisnika: max=" + max + ", min=" + min);
            izvestaj.write('\n');
            izvestaj.write('\t');
            izvestaj.write("Minimalni stepen slicnosti: " + minSlicnost + ", minimalni broj filmova ocenjenih od strane oba korisnika: " + minBrojSlicnihOcena);
            
            List<Korisnik> listaKorisnika = new ArrayList<>();
            for (Korisnik korisnik : korisnici.values()) {      //korisnici(<korisnikID, Korisnik>)
                if ((korisnik.getOcene().size() > max) || (korisnik.getOcene().size() < min)) {
                    continue;
                } else {
                    listaKorisnika.add(korisnik);
                }
            }
            
            int brojacKorisnika = 0;
            int brojKorisnika = listaKorisnika.size();
            if (brojKorisnika > 1) {
                for (int x = 0; x < brojKorisnika; x++) {       //petlja prolazi kroz validne korisnike
                    double brojilac = 0.0;
                    double imenilac1 = 0.0;
                    double imenilac2 = 0.0;
                    Korisnik prvi = korisnici.get(listaKorisnika.get(x).getKorisnikID());
                    int brojOcena1 = prvi.getOcene().size();
                    for (int y = x + 1; y < brojKorisnika; y++) {       //poredjenje sa ostalim korisnicima
                        int brojSlicnosti = 0;
                        Korisnik drugi = korisnici.get(listaKorisnika.get(y).getKorisnikID());
                        int brojOcena2 = drugi.getOcene().size();
                        for (Integer filmID : prvi.getOcene().keySet()) {       //Korisnik.ocene(<filmID, ocena>)
                            if (drugi.getOcene().containsKey(filmID)) {         //da li je drugi ocenio taj film?
                                brojSlicnosti++;
                                double ocenaPrvog = (double) prvi.getOcene().get(filmID) - prvi.getProsecnaOcena();  //=Ry0(x) - Ry0(Ave)
                                double ocenaDrugog = (double) drugi.getOcene().get(filmID) - drugi.getProsecnaOcena();  //=Ry(x) - Ry(Ave)
                                brojilac += ocenaPrvog * ocenaDrugog;
                                imenilac1 += Math.pow(ocenaPrvog, 2);
                                imenilac2 += Math.pow(ocenaDrugog, 2);
                            }
                        }
                        if (brojSlicnosti > minBrojSlicnihOcena) {      //zadrzava samo korisnike sa dovoljno velikim brojem ocena
                            double stepenSlicnosti = ((imenilac1 != 0.0) && (imenilac2 != 0.0))
                                    ? stepenSlicnosti = (brojilac / (Math.sqrt(imenilac1) * Math.sqrt(imenilac2)))
                                    : 0.0;
                            if (stepenSlicnosti >= minSlicnost) {       //zadrzava samo dovoljno slicne korisnike
                                double stepenSlicnosti1 = stepenSlicnosti * brojOcena1 * brojSlicnosti;
                                double stepenSlicnosti2 = stepenSlicnosti * brojOcena2 * brojSlicnosti;
                                
                                prvi.dodajSlicnost(drugi.getKorisnikID(), stepenSlicnosti2);    //dodaje vrednost u Korisnik.slicnosti(<korisnikID, stepenSlicnosti>)
                                drugi.dodajSlicnost(prvi.getKorisnikID(), stepenSlicnosti1);
                                brojacKorisnika++;
                            }
                        }
                    }
//                    brojacKorisnika++;
                    if (brojacKorisnika % 200 == 0) {
                        System.out.print("_");
                    }
                }
            }
            System.out.println(" DONE\nSlicnosti pronadjeno: " + brojacKorisnika + "\n");
        } catch (IOException ex) {
            System.err.println("Greska u izvestaju: " + ex.getMessage());
        }
    }

    /**
     * Predvidja ocene prosledjenog korisnika na osnovu ocena slicnih korisnika
     * i trendova medju korisnicima/filmovima. PCC = Pierson Correlation
     * Coefficient = Pirsonov koeficijent korelacije
     *
     * @param korisnikID ID konkretnog korisnika za kog se vrsi predvidjanje
     */
    public void predvidiPCC() {
        System.out.println("Predvidjanje ocena");
        int testovi = 0;
        try {
            FileWriter out = new FileWriter("resursi/izlaz.txt");
            for (Integer testKorisnikID : korisniciTestSet.keySet()) {
                Korisnik testKorisnik = korisnici.get(testKorisnikID);
                double brojilac = 0.0;
                double imenilac = 0.0;
                testovi++;
                
                for (Integer slicanKorisnikID : testKorisnik.getSlicnosti().keySet()) {        //Korisnik.slicnosti(<korisnikID, stepenSLicnosti>)
                    for (Integer testFilmID : korisniciTestSet.get(testKorisnikID).getOcene().keySet()) {
                        if (korisnici.get(slicanKorisnikID).getOcene().containsKey(testFilmID)) {
                            brojilac += testKorisnik.getSlicnosti().get(slicanKorisnikID)
                                    * (korisnici.get(slicanKorisnikID).getOcene().get(testFilmID)
                                    - korisnici.get(slicanKorisnikID).getProsecnaOcena());
                            imenilac += Math.abs(testKorisnik.getSlicnosti().get(slicanKorisnikID));
                        }
                    }
                }
                double predvidjanjeD;
                if (imenilac != 0.0) {      //predvidjanje na osnovu slicnih korisnika
                    predvidjanjeD = testKorisnik.getProsecnaOcena() + brojilac / imenilac;
                    out.write(Math.round(predvidjanjeD) + "," + testKorisnikID);
                    out.write('\n');
                } else {        //predvidjanje na osnovu prosecne ocene konkretnog korisnika i prosecne ocene filma
                    double nemaSlicnih;
                    for (Integer testFilmID : korisniciTestSet.get(testKorisnikID).getOcene().keySet()) {
                        if (filmovi.get(testFilmID).getProsecnaOcena() > 0) {
                            nemaSlicnih = (2 * testKorisnik.getProsecnaOcena() + filmovi.get(testFilmID).getProsecnaOcena()) / 3;
                        } else {
                            nemaSlicnih = testKorisnik.getProsecnaOcena();
                        }
                        out.write(Math.round(nemaSlicnih) + "," + testKorisnikID);
                        out.write('\n');
                    }
                }
                if (testovi % 2000 == 0) {
                    System.out.print("_");
                }
            }
            out.close();
            System.out.println("DONE\n");
        } catch (IOException e) {
            System.err.println("Greska u izlazu (predvidjanju): " + e.getMessage());
        }
    }

    /**
     * Dodaje novog korisnika ukoliko ne postoji u HashMapi korisnici
     *
     * @param korisnikID ID korisnika kog treba dodati
     */
    private void dodajKorisnika(int korisnikID) {
        if (!korisnici.containsKey(korisnikID)) {
            korisnici.put(korisnikID, new Korisnik(korisnikID));
        }
    }

    /**
     * Poziva se posle predvidiPCC, preporucuje filmove koje korisnik nije
     * ocenio, a ocekivano je da ih oceni ocenom 5
     *
     * @param korisnikID ID konkretnog korisnika
     */
    public void preporuka(int korisnikID) {
        System.out.println("\nPreporuka:");
        try {
            Scanner in = new Scanner(new File("resursi/izlaz.txt"));
            FileWriter out = new FileWriter("resursi/preporuka.txt");
            while (in.hasNext()) {
                String linija = in.nextLine();
                if (linija.length() > 0) {
                    String[] split = linija.split(",");
                    int ocena = Integer.parseInt(split[0]);
                    int filmID = Integer.parseInt(split[1]);
                    //ako se predpostavlja ocena veca od proseka za ovog korisnika i ako film nije ranije ocenjen
                    if (ocena > korisnici.get(korisnikID).getProsecnaOcena() && !korisnici.get(korisnikID).getOcene().containsKey(filmID)) {      //korisnici(<korisnikID, Korisnik>)
//                        System.out.println(filmovi.get(filmID));        //ispisuje ime filma
                        out.write(filmovi.get(filmID).toString());
                        out.write('\n');
                    }
                }
            }
            out.close();
            in.close();
        } catch (IOException ex) {
            System.err.println("Greska u radu sa fajlovima resursi/izvestaj.txt i resursi/preporuka.txt");
        }
    }

    /**
     * Dodaje novog korisnika ukoliko ne postoji u HashMapi korisniciTestSet
     *
     * @param korisnikID ID korisnika kog treba dodati
     */
    private void dodajKorisnikaUTest(int korisnikID) {
        if (!korisniciTestSet.containsKey(korisnikID)) {
            korisniciTestSet.put(korisnikID, new Korisnik(korisnikID));
        }
    }

    /**
     * Racuna srednju kvadratnu gresku. RMSE = Root Mean Square Error
     */
    public void izracunajRMSE() {
//        double kvadratnaGreska;
//        double kvadratnaSuma = 0.0;
//        for (Integer korisnikID : korisniciTestSet.keySet()) {
//            KorisnikTest korTest = korisniciTestSet.get(korisnikID);
//            for (Integer filmID : korTest.getPredpostavljeneOcene().keySet()) {
//                double element = korTest.getPredpostavljeneOcene().get(filmID) - korTest.getOcene().get(filmID);   //(predvidjanje - stvarna ocena)
//                kvadratnaSuma += element * element;     //element1^2 + element2^2 + ... + elementN^2
//            }
//        }
//        kvadratnaGreska = Math.sqrt(kvadratnaSuma);     //greska je koren sume
//
//        System.out.println("RMSE iznosi: " + kvadratnaGreska);
    }
}
