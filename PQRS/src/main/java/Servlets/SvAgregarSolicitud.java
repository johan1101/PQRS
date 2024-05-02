/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Clases.Conexion;
import Clases.Metodos;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Johan Ordoñez
 */
@MultipartConfig
@WebServlet(name = "SvAgregarSolicitud", urlPatterns = {"/SvAgregarSolicitud"})
public class SvAgregarSolicitud extends HttpServlet {

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

        Connection connection = conexion.establecerConexion();
        // Obtén el archivo enviado desde el formulario
        int idPdf = 0;
        String descripcion;
        Part filePart = request.getPart("pdf"); // "pdf" debe coincidir con el nombre del campo del formulario
        System.out.println(filePart);
        String nombre = request.getParameter("nombre");
        descripcion = request.getParameter("descripcion");
        String tipoSolicitud = request.getParameter("tipo");
        String estado = "Por responder";
        int idUsuario = (Integer) request.getSession().getAttribute("idUsuario");
        System.out.println(idUsuario);

        //Obtener el contexto del servlet
        ServletContext context = getServletContext();

        // Verifica si se recibió un archivo
        if (filePart != null && filePart.getSize() > 0) {
            try {
                descripcion = metodo.agregarPdf(filePart, context, connection);
                // Obtener el nombre del archivo PDF enviado
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                idPdf = metodo.buscarIDPDF(connection, fileName);
            } catch (SQLException ex) {
                Logger.getLogger(SvAgregarSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            metodo.agregarSolicitud(nombre, tipoSolicitud, estado, descripcion, idPdf, idUsuario, connection);
        } catch (SQLException ex) {
            Logger.getLogger(SvAgregarSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("usuario.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
