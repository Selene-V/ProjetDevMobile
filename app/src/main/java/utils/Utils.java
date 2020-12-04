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
}
