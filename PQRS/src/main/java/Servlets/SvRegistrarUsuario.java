/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Clases.Conexion;
import Clases.Metodos;
import Clases.MetodosUsuarios;
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
 * SvRegistrarUsuario
 *
 * @author Johan- María
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
    //Conexion
    Metodos metodo = new Metodos();

    Conexion conexion = new Conexion();
    /**
     * Metodo POST para registro de usuario
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        boolean cedulaExistente = false;

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
            cedulaExistente = MetodosUsuarios.verificarExistenciaCedula(cedula, conectarBD);
        } catch (SQLException ex) {
            Logger.getLogger(SvRegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (cedulaExistente == false) {
            try {
                MetodosUsuarios.agregarUsuario(cedula, nombre, apellido, celular, correo, contrasena, idRol, conectarBD);
            } catch (SQLException ex) {
                Logger.getLogger(SvRegistrarUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            String redirigir = "index";
            if (request.getParameter("redireccion") != null) {
                redirigir = "agregar";
            }

            String toastr = "registrado";
            // Establecer el atributo en el objeto HttpServletRequest
            request.setAttribute("toastr", toastr);

            // Redirigir a la página JSP
            request.getRequestDispatcher(redirigir + ".jsp").forward(request, response);
        } else {
            String redirigir = "index";
            if (request.getParameter("redireccion") != null) {
                redirigir = "agregar";
            }
            String toastr = "noRegistrado";
            // Establecer el atributo en el objeto HttpServletRequest
            request.setAttribute("toastr", toastr);

            // Redirigir a la página JSP
            request.getRequestDispatcher(redirigir + ".jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
