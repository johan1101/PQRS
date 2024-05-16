/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author maria
 */
public class MetodosUsuarios {
    public static Usuarios obtenerUsuario(int id) throws SQLException{
        Conexion conexion = new Conexion();
        Connection conn = conexion.establecerConexion();
        Usuarios us=new Usuarios();
       

        // Declarar la conexión, el PreparedStatement y el ResultSet como variables locales
        PreparedStatement consulta = conn.prepareStatement("SELECT * FROM pqrs.solicitudes left JOIN  usuarios  on usuarios.idUsuario=solicitudes.idUsuario WHERE idSolicitud = ?");
        {
            // Establecer el parámetro en la consulta preparada
            consulta.setInt(1, id);

            // Ejecutar la consulta
            ResultSet resultado = consulta.executeQuery();

            // Verificar si se encontró una solicitud con el ID proporcionado
            if (resultado.next()) {
                // Crear un objeto de solicitud con los datos obtenidos de la base de datos
                us.setIdUsuario(resultado.getInt("idUsuario"));
                us.setNombre(resultado.getString("nombre"));
                us.setApellido(resultado.getString("apellido"));
                us.setCedula(resultado.getString("cedula"));
                us.setCorreo(resultado.getString("correo"));
            }
        }
        return us;
    }
    public static Usuarios obtenerUsuarioPorId(int idUsuario, Connection conn) throws SQLException {
        // Inicializar el usuario como nulo
        Usuarios usuario = new Usuarios();

        // Declarar el PreparedStatement y el ResultSet como variables locales
        try (PreparedStatement consulta = conn.prepareStatement("SELECT * FROM usuarios WHERE idUsuario = ?");) {
            // Establecer el parámetro en la consulta preparada
            consulta.setInt(1, idUsuario);

            // Ejecutar la consulta
            try (ResultSet resultado = consulta.executeQuery()) {
                // Verificar si se encontró un usuario con el ID proporcionado
                if (resultado.next()) {
                    // Crear un objeto de usuario con los datos obtenidos de la base de datos

                    usuario.setIdUsuario(resultado.getInt("idUsuario"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellido(resultado.getString("apellido"));
                    usuario.setCedula(resultado.getString("cedula"));
                    usuario.setContrasena(resultado.getString("contrasena"));
                    usuario.setCelular(resultado.getString("celular"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setRol(resultado.getString("idRol"));
                    // Agregar más atributos según sea necesario
                }
            }
        }

        // Devolver el usuario (puede ser nulo si no se encontró ningún usuario con el ID proporcionado)
        return usuario;
    }
    public static int obtenerUsuarioPorIdSolicitud(int idSolicitud, Connection conn) throws SQLException {
        
        // Inicializar el usuario como nulo
        int usuario = 0;

        // Declarar el PreparedStatement y el ResultSet como variables locales
        try (PreparedStatement consulta = conn.prepareStatement("SELECT * FROM solicitudes WHERE idSolicitud = ?");) {
            // Establecer el parámetro en la consulta preparada
            consulta.setInt(1, idSolicitud);

            // Ejecutar la consulta
            try (ResultSet resultado = consulta.executeQuery()) {
                // Verificar si se encontró un usuario con el nombre proporcionado
                if (resultado.next()) {
                    // Crear un objeto de usuario con los datos obtenidos de la base de datos

                    usuario = (resultado.getInt("idUsuario"));

                    // Agregar más atributos según sea necesario
                }
            }
        }

        // Devolver el usuario (puede ser nulo si no se encontró ningún usuario con el nombre proporcionado)
        return usuario;
    }
    
    public static String mostrarInformacionUsuario(Usuarios usuario) {
        StringBuilder sb = new StringBuilder();

        sb.append("<div class=\"form\">");
        sb.append("<div>");
        sb.append("<span><img src=\"img/icono.png\" /></span>");
        sb.append("<h3>Editar información</h3>");
        sb.append("</div>");
        sb.append("<hr>");
        sb.append("<div class=\"row\">");
        sb.append("<div class=\"col\">");
        sb.append("<div class=\"form-element\">");
        sb.append("<label for=\"nombre\">Nombre</label>");
        sb.append("<input type=\"text\" id=\"nombre\" value=\"").append(usuario.getNombre()).append("\" name=\"nombre\" placeholder=\"Ingresa el nuevo nombre\" maxlength=\"50\" required pattern=\"[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+\" title=\"No se permiten números\" required>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div class=\"col\">");
        sb.append("<div class=\"form-element\">");
        sb.append("<label for=\"apellido\">Apellido</label>");
        sb.append("<input type=\"text\" id=\"apellido\" value=\"").append(usuario.getApellido()).append("\" name=\"apellido\" placeholder=\"Ingresa el nuevo apellido\" maxlength=\"50\" required pattern=\"[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+\" title=\"No se permiten números\" required>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div class=\"row\">");
        sb.append("<div class=\"col\">");
        sb.append("<div class=\"form-element\">");
        sb.append("<label for=\"cedula\">Cédula</label>");
        sb.append("<input type=\"text\" id=\"cedula\" value=\"").append(usuario.getCedula()).append("\" name=\"cedula\" placeholder=\"Ingresa la nueva cedula\" maxlength=\"10\" required pattern=\"[0-9]+\" title=\"Solo se permiten números\" required>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div class=\"col\">");
        sb.append("<div class=\"form-element\">");
        sb.append("<label for=\"celular\">Celular</label>");
        sb.append("<input type=\"text\" id=\"celular\" value=\"").append(usuario.getCelular()).append("\" name=\"celular\" placeholder=\"Ingresa el nuevo número celular\" maxlength=\"50\" required pattern=\"[0-9]+\" title=\"Solo se permiten números\" required>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div class=\"row\">");
        sb.append("<div class=\"col\">");
        sb.append("<div class=\"form-element\">");
        sb.append("<label for=\"correo\">Correo</label>");
        sb.append("<input type=\"text\" id=\"correo\" value=\"").append(usuario.getCorreo()).append("\" name=\"correo\" placeholder=\"Ingresa el nuevo correo\" maxlength=\"50\" required required>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div class=\"row\">");
        sb.append("<div class=\"col\">");
        sb.append("<div class=\"form-element\">");
        sb.append("<button type=\"submit\">Editar</button>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");

        return sb.toString();
    }

    public static void editarUsuario(int idUsuario, String nombre, String apellido, String cedula, String celular, String correo, Connection conn) throws SQLException {

        String sql = "CALL editarUsuario(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido);
            stmt.setString(4, cedula);
            stmt.setString(5, celular);
            stmt.setString(6, correo);
            stmt.executeUpdate();
        }

    }

    public static boolean verificarExistenciaCedula(String cedula, Connection conn) throws SQLException {
        boolean cedulaExistente = false;

        // Consulta SQL para verificar si la cédula ya existe en la base de datos
        String consultaSQL = "SELECT * FROM usuarios WHERE cedula = ?";

        // Declarar el PreparedStatement y el ResultSet como variables locales
        try (PreparedStatement consulta = conn.prepareStatement(consultaSQL)) {
            // Establecer el parámetro en la consulta preparada
            consulta.setString(1, cedula);

            // Ejecutar la consulta
            try (ResultSet resultado = consulta.executeQuery()) {
                // Verificar si se encontró alguna fila en el resultado
                if (resultado.next()) {
                    // La cédula ya existe en la base de datos
                    cedulaExistente = true;
                }
            }
        }

        return cedulaExistente;
    }
    public static void agregarUsuario(String cedula, String nombre, String apellido, String celular, String correo, String contrasena, int idRol, Connection connection) throws SQLException {

        String sql = "CALL AgregarUsuario(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nombre);
        statement.setString(2, apellido);
        statement.setString(3, cedula);
        statement.setString(4, contrasena);
        statement.setString(5, celular);
        statement.setString(6, correo);
        statement.setInt(7, idRol); // ID del rol
        statement.executeUpdate();
    }

    public static void validarIngreso(String cedula, String contrasena, Connection connection, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws SQLException, IOException, ServletException {
        // Consulta SQL para buscar un usuario con la cédula y contraseña proporcionadas
        String sql = "SELECT idUsuario, idRol FROM usuarios WHERE cedula = ? AND contrasena = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        // Establecer los parámetros en la consulta preparada
        statement.setString(1, cedula);
        statement.setString(2, contrasena);

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();
        // Verificar si se encontró un usuario con la cédula y contraseña proporcionadas
        if (resultSet.next()) {
            // Obtener el ID del usuario y el ID del rol del usuario
            int idUsuario = resultSet.getInt("idUsuario");
            int idRol = resultSet.getInt("idRol");

            String tipoUsuario = "";

            // Agregar el ID del usuario a la sesión
            session.setAttribute("idUsuario", idUsuario);

            session.setAttribute("cedula", cedula);
            // Redireccionar según el rol del usuario
            if (idRol == 1) { // Si es Usuario
                tipoUsuario = "usuario";
                session.setAttribute("tipoUsuario", tipoUsuario);
                String toastr = "siPasa";
                // Establecer el atributo en el objeto HttpServletRequest
                request.setAttribute("toastr", toastr);
                request.getRequestDispatcher("usuario.jsp?res=Todos").forward(request, response);
            } else if (idRol == 2) { // Si es Administrador
                tipoUsuario = "administrador";
                session.setAttribute("tipoUsuario", tipoUsuario);
                // Redirigir a la página JSP
                String toastr = "siPasa";
                // Establecer el atributo en el objeto HttpServletRequest
                request.setAttribute("toastr", toastr);
                request.getRequestDispatcher("solicitudes.jsp?res=no").forward(request, response);
            }

        } else {
            // Credenciales incorrectas
            String toastr = "noPasa";
            // Establecer el atributo en el objeto HttpServletRequest
            request.setAttribute("toastr", toastr);

            // Redirigir a la página JSP
            request.getRequestDispatcher("index.jsp").forward(request, response);

        }
    }
}
