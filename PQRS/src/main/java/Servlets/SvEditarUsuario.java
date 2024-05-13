/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Clases.Conexion;
import Clases.Metodos;
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

/**
 *
 * @author Johan Ordoñez
 */
@WebServlet(name = "SvEditarUsuario", urlPatterns = {"/SvEditarUsuario"})
public class SvEditarUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }
    Metodos metodo = new Metodos();
    Usuarios usuario = new Usuarios();
    Conexion conexion = new Conexion();
    int id = 0;
    String cedulaA = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        System.out.println("Corriendo metodo para mostrar informacion de un usuario para ser editado");
        Connection conn = conexion.establecerConexion();
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        id = idUsuario;
        try {
            usuario = metodo.obtenerUsuarioPorId(idUsuario, conn);
        } catch (SQLException ex) {
            Logger.getLogger(SvEditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        String html = metodo.mostrarInformacionUsuario(usuario);

        cedulaA = usuario.getCedula();

        // Se configura el tipo de contenido de la respuesta como "text/html"
        response.setContentType("text/html");
        // Se escribe la información del contacto en el cuerpo de la respuesta
        response.getWriter().write(html);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        boolean cedulaExistente = false;
        System.out.println("Corriendo metodo para editar el usuario");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String cedula = request.getParameter("cedula");
        String celular = request.getParameter("celular");
        String correo = request.getParameter("correo");

        Connection conn = conexion.establecerConexion();

        if (!cedulaA.equals(cedula)) {
            try {
                cedulaExistente = metodo.verificarExistenciaCedula(cedula, conn);
            } catch (SQLException ex) {
                Logger.getLogger(SvEditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (cedulaExistente == false) {
            try {
                metodo.editarUsuario(id, nombre, apellido, cedula, celular, correo, conn);
            } catch (SQLException ex) {
                Logger.getLogger(SvEditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            String toastr = "noExistente";
            // Establecer el atributo en el objeto HttpServletRequest
            request.setAttribute("toastr", toastr);

            // Redirigir a la página JSP
            request.getRequestDispatcher("perfilUsuario.jsp").forward(request, response);
        } else {
            String toastr = "existente";
            // Establecer el atributo en el objeto HttpServletRequest
            request.setAttribute("toastr", toastr);

            // Redirigir a la página JSP
            request.getRequestDispatcher("perfilUsuario.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
