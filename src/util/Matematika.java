/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Misa
 */
public class Matematika {

    public static double zaokruzi(double broj, int mesta) {
        double pom1 = broj * Math.pow(10, mesta);
        long pom2 = Math.round(pom1);
        return (double) pom2 / Math.pow(10, mesta);
    }

    public static int prebaciUInt(double predvidjanjeD) {
        int temp = (int) Math.floor(predvidjanjeD);
        double ostatak = predvidjanjeD % 1;
        if (ostatak >= 0.5) {
            temp++;
        }
        if (temp > 5) {
            temp--;
        }
        if (temp < 1) {
            temp++;
        }
        return temp;
    }
}
