/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Clases.Conexion;
import Clases.Metodos;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Johan Ordoñez
 */
@WebServlet(name = "SvRegistrarUsuario", urlPatterns = {"/SvRegistrarUsuario"})
public class SvRegistrarUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    Metodos metodo = new Metodos();

    Conexion conexion = new Conexion();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        Connection conectarBD = conexion.establecerConexion();

        // Obtener los parámetros del formulario
        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String celular = request.getParameter("celular");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasenia");
        int idRol = (1);
        
        try {
            metodo.agregarUsuario(cedula, nombre, apellido, celular, correo, contrasena, idRol, conectarBD);
        } catch (SQLException ex) {
            Logger.getLogger(SvRegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        String redirigir="index";
        if(request.getParameter("redireccion")!=null){redirigir="agregar";}
        response.sendRedirect(redirigir+".jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
