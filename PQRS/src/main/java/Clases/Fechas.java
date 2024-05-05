/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.Comparator;

/**
 *
 * @author maria
 */
public class Fechas implements Comparator<Solicitudes> {
    @Override
    public int compare(Solicitudes o1, Solicitudes o2) {
        return o1.getFechaRegistro().compareTo(o2.getFechaRegistro());
    }
}
