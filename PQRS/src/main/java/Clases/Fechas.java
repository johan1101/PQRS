/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.Comparator;

/**
 * Fechas
 * @author Johan- María
 */
public class Fechas implements Comparator<Solicitudes> {
    /**
     * Metodo para comparar las fechas y organizar
     * @param o1
     * @param o2
     * @return 
     */
    @Override
    public int compare(Solicitudes o1, Solicitudes o2) {
        return o1.getFechaRegistro().compareTo(o2.getFechaRegistro());
    }
}
