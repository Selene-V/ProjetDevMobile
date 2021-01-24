package utils;

import java.text.NumberFormat;

public class Utils {

    public double arrondir(double nombre){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        String s = nf.format(nombre);
        return Double.parseDouble(s.replace(",","."));
    }

    public String toUpperCaseFirst(String s){
        String first = s.substring(0, 1);
        String rest = s.substring(1);
        first = first.toUpperCase();
        return first + rest;
    }
}
