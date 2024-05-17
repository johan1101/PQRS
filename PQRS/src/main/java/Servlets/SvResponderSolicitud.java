/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Clases.Conexion;
import Clases.EnvioCorreo;
import Clases.Metodos;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SvResponderSolicitud
 *
 * @author Johan- María
 */
@WebServlet(name = "SvResponderSolicitud", urlPatterns = {"/SvResponderSolicitud"})
public class SvResponderSolicitud extends HttpServlet {

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
     * Metodo POST para la respuesta y envio de correo 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        Connection conn = conexion.establecerConexion();

        int idSolicitud = Integer.parseInt(request.getParameter("idOculto"));
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String respuesta = request.getParameter("respuesta");
        String nuevoEstado = "Respondido";

        // Ahora puedes hacer lo que necesites con estos datos
        // Por ejemplo, imprimirlos en la consola
        System.out.println("IdOculto: " + idSolicitud);
        System.out.println("Nombre: " + nombre);
        System.out.println("Correo: " + correo);
        System.out.println("Respuesta: " + respuesta);

        System.out.println("Corriendo metodo para responder solicitud");
        String emailFrom = "pqrsresponse@gmail.com"; // Cambia esto por tu dirección de correo electrónico
        String passwordFrom = "wkro wbtv mnoi gsjy"; // Cambia esto por tu contraseña
        String emailTo = correo; // Cambia esto por la dirección de correo electrónico del destinatario
        String subject = "Solicitud atendida: " + nombre;

// Contenido del correo electrónico
        String content = "Cordial saludo," + "\n"
                + "Espero que este mensaje le encuentre bien. Quisiera informarle que hemos procesado su solicitud titulada '" + nombre + "' con éxito. A continuación, detallo la respuesta correspondiente." + "\n"
                + "" + "\n"
                + respuesta + "\n"
                + "" + "\n"
                + "Estamos a su disposición para cualquier pregunta adicional que pueda surgir.";

        // Crear instancia de EnvioCorreos y configurar detalles del correo
        EnvioCorreo envioCorreos = new EnvioCorreo();
        envioCorreos.setEmailFrom(emailFrom);
        envioCorreos.setPasswordFrom(passwordFrom);
        envioCorreos.setTo(emailTo);
        envioCorreos.setSubject(subject);
        envioCorreos.setContent(content);

        // Intentar enviar el correo electrónico
        try {
            envioCorreos.sendEmail();
            System.out.println("Correo enviado con exito");
            metodo.editarRespuestaEstado(idSolicitud, respuesta, nuevoEstado ,conn);
        } catch (MessagingException ex) {
            System.out.println("Error al enviar el correo");
        } catch (SQLException ex) {
            Logger.getLogger(SvResponderSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("solicitudes.jsp?res=no");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
