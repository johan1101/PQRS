/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Clases.EnvioCorreo;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Johan Ordoñez
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        System.out.println("Corriendo metodo para responder solicitud");
        String emailFrom = "pqrsresponse@gmail.com"; // Cambia esto por tu dirección de correo electrónico
        String passwordFrom = "wkro wbtv mnoi gsjy"; // Cambia esto por tu contraseña
        String emailTo = "johanrealpelibro@gmail.com"; // Cambia esto por la dirección de correo electrónico del destinatario
        String subject = "Asunto del correo";
        String content = "Contenido del correo electrónico.";

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
            response.getWriter().println("Correo enviado exitosamente");
        } catch (MessagingException ex) {
            response.getWriter().println("Error al enviar el correo: " + ex.getMessage());
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
