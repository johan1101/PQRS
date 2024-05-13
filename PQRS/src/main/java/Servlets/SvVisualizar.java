/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Clases.Conexion;
import Clases.Metodos;
import Clases.Solicitudes;
import Clases.Usuarios;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Johan Ordoñez
 */
@WebServlet(name = "SvVisualizar", urlPatterns = {"/SvVisualizar"})
public class SvVisualizar extends HttpServlet {
    Metodos metodo = new Metodos();
    Conexion conexion = new Conexion();
    Solicitudes sol = new Solicitudes();
    Usuarios user = new Usuarios();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         Connection conn = conexion.establecerConexion();
        // Obtener la sesión
        HttpSession session = request.getSession();

        // Obtener el valor del atributo "idUsuario" de la sesión
        int idUsuario = (int) session.getAttribute("idUsuario");
        int idSolicitud = Integer.parseInt(request.getParameter("idTutorial"));// Obtener el atributo "idUsuario" de la solicitud

        try {
            System.out.println("corriendo metodo para obtener el usuario");
            user = metodo.obtenerUsuarioPorId(idUsuario, conn);
            System.out.println("Usuario obtenido con exito");
        } catch (SQLException ex) {
            Logger.getLogger(SvVisualizar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            System.out.println("Metodo para obtener la solicitud");
            sol = metodo.obtenerSolicitudPorId(idSolicitud, conn);
            System.out.println("Solicitud obrenida con exito");
        } catch (SQLException ex) {
            Logger.getLogger(SvVisualizar.class.getName()).log(Level.SEVERE, null, ex);
        }

        String respuesta = Metodos.editarInfoSolicitud(sol);

        // Se configura el tipo de contenido de la respuesta como "text/html"
        response.setContentType("text/html");
        // Se escribe la información del contacto en el cuerpo de la respuesta
        response.getWriter().write(respuesta);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        System.out.println("Corriendo metodo para visualizar los datos antes de responder la solicitud");
        Connection conn = conexion.establecerConexion();
        // Obtener la sesión
        HttpSession session = request.getSession();
        
        int usuario = 0;

// Obtener el valor del atributo "idUsuario" de la sesión
        int idSolicitud = Integer.parseInt(request.getParameter("idTutorial"));// Obtener el atributo "idUsuario" de la solicitud

        try {
            System.out.println("corriendo metodo para obtener el correo del usuario");
            
            usuario = metodo.obtenerUsuarioPorIdSolicitud(idSolicitud ,conn);
            user = metodo.obtenerUsuarioPorId(usuario ,conn);
        } catch (SQLException ex) {
            Logger.getLogger(SvVisualizar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            System.out.println("Metodo para obtener la solicitud");
            sol = metodo.obtenerSolicitudPorId(idSolicitud, conn);
            System.out.println("Solicitud obrenida con exito");
        } catch (SQLException ex) {
            Logger.getLogger(SvVisualizar.class.getName()).log(Level.SEVERE, null, ex);
        }

        String respuesta = metodo.mostrarInfoSolicitud(sol, user);

        // Se configura el tipo de contenido de la respuesta como "text/html"
        response.setContentType("text/html");
        // Se escribe la información del contacto en el cuerpo de la respuesta
        response.getWriter().write(respuesta);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
