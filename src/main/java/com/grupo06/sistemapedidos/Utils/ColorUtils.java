package com.grupo06.sistemapedidos.Utils;

public class ColorUtils {

    public static String pintarVerde(String mensaje) {
        // \u001B[32m es verde y \u001B[0m resetea el color
        return "\u001B[32m" + mensaje + "\u001B[0m";
    }

    public static String pintarRojo(String mensaje) {
        // \u001B[31m es rojo y \u001B[0m resetea el color
        return "\u001B[31m" + mensaje + "\u001B[0m";
    }
}