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
import javax.servlet.http.HttpSession;

/**
 * SvLoginCheck
 *
 * @author Johan- María
 */
@WebServlet(name = "SvLoginCheck", urlPatterns = {"/SvLoginCheck"})
public class SvLoginCheck extends HttpServlet {

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
     * Metodo POST para login
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        Connection connection = conexion.establecerConexion();
        // Obtener los parámetros del formulario
        String cedula = request.getParameter("cedula");
        String contrasenia = request.getParameter("contrasenia");
         HttpSession session = request.getSession();
        
        try {
            request.getSession().setAttribute("usuario", cedula);
            MetodosUsuarios.validarIngreso(cedula, contrasenia, connection, response, session, request);
        } catch (SQLException ex) {
            Logger.getLogger(SvLoginCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
