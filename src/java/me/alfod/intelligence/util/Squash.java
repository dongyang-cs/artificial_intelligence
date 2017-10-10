package me.alfod.intelligence.util;

/**
 * Created by Arvin Alfod on 2017/10/9.
 */
public class Squash {
    public static void main(String[] args){
        System.out.println(sigmoid(-1));
    }
    public static double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E, -x));
    }
}
