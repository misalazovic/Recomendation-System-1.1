/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Misa
 */
public class Zaokruzivanje {

    public static double zaokruzi(double broj, int mesta) {
        double pom1 = broj * Math.pow(10, mesta);
        long pom2 = Math.round(pom1);
        return (double) pom2 / Math.pow(10, mesta);
    }
}
