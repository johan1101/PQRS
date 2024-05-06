package Clases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author Johan Ordoñez
 */
public class Metodos {

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

    public static void validarIngreso(String cedula, String contrasena, Connection connection, HttpServletResponse response, HttpSession session) throws SQLException, IOException {
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

            // Agregar el ID del usuario a la sesión
            session.setAttribute("idUsuario", idUsuario);

            session.setAttribute("cedula", cedula);
            // Redireccionar según el rol del usuario
            if (idRol == 1) { // Si es Usuario
                response.sendRedirect("usuario.jsp");
            } else if (idRol == 2) { // Si es Administrador
                response.sendRedirect("administrador.jsp");
            }

        } else {
            // Credenciales incorrectas
            // Aquí puedes redirigir a otra página o realizar otras acciones según tu lógica de negocio
            // Por ejemplo, redirigir a una página de inicio de sesión fallida
            response.sendRedirect("index.jsp");
        }
    }

    public static String agregarPdf(Part filePart, ServletContext context, Connection connection) throws IOException, SQLException {
        // Procesa el archivo aquí, por ejemplo, guarda el PDF en el servidor
        // Directorio de carga en el servidor donde se guardarán los PDFs

        String pdfText = null;
        String uploadPath = context.getRealPath("/pdfs");

        // Obtener el nombre del archivo PDF enviado
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Construir la ruta completa del archivo PDF en el servidor
        String filePath = uploadPath + File.separator + fileName;

        //Abrir un flujo de entrada para el archivo PDF recibido
        try (InputStream fileContent = filePart.getInputStream(); FileOutputStream outputStream = new FileOutputStream(filePath)) {

            int read;
            byte[] buffer = new byte[1024];

            //Leer el archivo PDF y escribirlo en el servidor
            while ((read = fileContent.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        }

        // Leer el contenido del PDF y guardarlo en una descripción
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            pdfText = stripper.getText(document);
            // Aquí puedes guardar pdfText en la base de datos o hacer cualquier otra operación con él
        } catch (IOException e) {
            e.printStackTrace();
        }
        agregarPdfBD(connection, fileName);
        return pdfText;
    }

    public static void agregarPdfBD(Connection connection, String fileName) throws SQLException, IOException {
        // Llamar al procedimiento almacenado para agregar el nombre del PDF a la base de datos
        try (PreparedStatement statement = connection.prepareStatement("CALL AgregarPDF(?)")) {
            statement.setString(1, fileName);
            statement.executeUpdate();
        }
    }

    public static void agregarSolicitud(String nombreSolicitud, String tipoSolicitud, String estado, String descripcion, int idPdf, int idUsuario, Connection connection) throws SQLException {

        // Llamar al procedimiento almacenado
        CallableStatement statement = connection.prepareCall("{CALL AgregarSolicitud(?, ?, ?, ?, ?, ?)}");
        statement.setString(1, nombreSolicitud);
        statement.setString(2, tipoSolicitud);
        statement.setString(3, estado);
        statement.setString(4, descripcion);
        if (idPdf == 0) {
            statement.setNull(5, java.sql.Types.INTEGER); // Establecer el parámetro como NULL
        } else {
            statement.setInt(5, idPdf); // Establecer el ID del PDF si es diferente de 0
        }
        statement.setInt(6, idUsuario);
        statement.execute();
    }

    // Método para buscar el ID de un PDF por su nombre
    public static int buscarIDPDF(Connection connection, String nombrePDF) throws SQLException {
        int idPDF = -1; // Valor predeterminado si el PDF no se encuentra

        String sql = "SELECT idPdf FROM pdfs WHERE nombre = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombrePDF);
            ResultSet resultSet = statement.executeQuery();

            // Si se encuentra un resultado, obtener el ID del PDF
            if (resultSet.next()) {
                idPDF = resultSet.getInt("idPdf");
            }
        }

        return idPDF;
    }

    public static ArrayList<Solicitudes> getSolicitudes() throws ClassNotFoundException {
        ArrayList<Solicitudes> array = new ArrayList();
        Conexion conexion = new Conexion();
        Connection connection = conexion.establecerConexion();
        try {

            // Consulta SQL para obtener datos de la tabla tutorial
            String sqlTutorial = "SELECT * FROM solicitudes left join pdfs on pdfs.idPdf=solicitudes.idPdf left join usuarios  on usuarios.idUsuario=solicitudes.idUsuario";

            // Crear una declaración para la consulta de tutoriales
            Statement statement = connection.createStatement();
            ResultSet resultSetSolicitud = statement.executeQuery(sqlTutorial);

            // Iterar sobre los resultados de tutoriales y almacenarlos en el array
            while (resultSetSolicitud.next()) {
                Solicitudes sol = new Solicitudes();
                sol.setIdSolicitud(resultSetSolicitud.getInt("idSolicitud"));
                sol.setNombreSol(resultSetSolicitud.getString("nombreSolicitud"));
                sol.setTipoSolicitud(resultSetSolicitud.getString("tipoSolicitud"));

                sol.setFechaRegistro(resultSetSolicitud.getDate("fechaRegistro"));
                sol.setEstado(resultSetSolicitud.getString("estado"));
                sol.setDescripcion(resultSetSolicitud.getString("descripcion"));
                sol.setPdf(resultSetSolicitud.getString("nombre"));
                sol.setUsuario(resultSetSolicitud.getString("cedula"));
                array.add(sol);
                //data.add(row);
            }

            // Cerrar la conexión
            resultSetSolicitud.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones
        }
        Collections.sort(array, new Fechas());
        return array;
    }

    public static String listarAdministradores(Solicitudes sol) {
        String HTML = "<article class=\"card\">\n"
                + "    <div class=\"card-header\">\n"
                + "        <div>                                \n"
                + "            <h3>Solicitud #" + sol.getIdSolicitud() + "</h3>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"card-body\">\n"
                + "        <h4>Nombre: " + sol.getNombreSol() + "</h4>\n"
                + "        <h4>Tipo Solicitud: " + sol.getTipoSolicitud() + "</h4>\n"
                + "        <h4>Fecha Registro: " + sol.getFechaRegistro() + "</h4>\n"
                + "        <h4>Estado: " + sol.getEstado() + "</h4>\n"
                + "        <h4>Descripcion: " + sol.getDescripcion() + "</h4>\n";

        // Verificar si se necesita calcular la fecha límite
        if (sol.getTipoSolicitud().equals("Peticion")) {
            Date fechaActual = sol.getFechaRegistro();
            // Crear una instancia de Calendar
            Calendar calendar = Calendar.getInstance();
            // Establecer la fecha actual en el Calendar
            calendar.setTime(fechaActual);
            // Sumar 15 días a la fecha actual
            calendar.add(Calendar.DAY_OF_MONTH, 15);

            // Formatear la fecha límite
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaLimiteFormateada = dateFormat.format(calendar.getTime());

            HTML += "        <h4>Fecha limite de respuesta: " + fechaLimiteFormateada + "</h4>\n";
        }

        HTML += "        <h4>Usuario: " + sol.getUsuario() + "</h4>\n"
                + "    </div>\n"
                + "    <div class=\"card-footer\">\n"
                + "        <a id='btnVisualizar'  data-nombre='" + sol.getIdSolicitud() + "'  href=\"#\">Responder Solicitud</a>\n"
                + "    </div>\n"
                + "</article>";
        return HTML;
    }

    public static ArrayList<Solicitudes> SolicitudesUsuario(String cedula) throws ClassNotFoundException {
        ArrayList<Solicitudes> array = getSolicitudes();
        ArrayList<Solicitudes> array2 = new ArrayList();
        for (Solicitudes sol : array) {
            if (cedula.equals(sol.getUsuario())) {
                array2.add(sol);
            }
        }
        Collections.sort(array, new Fechas());
        return array2;
    }

    public static String listarUsuario(Solicitudes sol) {
        String HTML = "<article class=\"card\">\n"
                + "    <div class=\"card-header\">\n"
                + "        <div>                                \n"
                + "            <h3>Solicitud #" + sol.getIdSolicitud() + "</h3>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"card-body\">\n"
                + "        <h4>Nombre: " + sol.getNombreSol() + "</h4>\n"
                + "        <h4>Tipo Solicitud: " + sol.getTipoSolicitud() + "</h4>\n"
                + "        <h4>Fecha Registro: " + sol.getFechaRegistro() + "</h4>\n"
                + "        <h4>Estado: " + sol.getEstado() + "</h4>\n"
                + "        <h4>Descripcion: " + sol.getDescripcion() + "</h4>\n"
                + "        <h4>Pdf: " + sol.getPdf() + "</h4>\n"
                + "        <h4>Usuario: " + sol.getUsuario() + "</h4>\n"
                + "    </div>\n"
                + "    <div class=\"card-footer\">\n"
                + "        <a href=\"#\">Editar</a>\n"
                + "        <a href=\"#\">Eliminar</a>\n"
                + "    </div>\n"
                + "</article>";
        return HTML;
    }

//        public static void editarSolicitud(String nombreSolicitud, String tipoSolicitud, String estado, String descripcion, int idPdf, int idUsuario) throws SQLException {
//            Conexion conexion = new Conexion();
//          Connection connection = conexion.establecerConexion();
//          CallableStatement preparedStatement = null;
//    try {
//
//      
//        // Consulta SQL parametrizada para actualizar la fila
//        String sqlTutorial = "UPDATE tutorial SET nombre = ?, idCategoria = ?, URL = ?, prioridad = ?, estado = ? WHERE id = ?";
//        
//        // Crear una declaración preparada para la consulta de actualización
//        preparedStatement = connection.prepareStatement(sqlTutorial);
//        
//        // Establecer los parámetros de la consulta
//        preparedStatement.setString(1, nombre);
//        preparedStatement.setInt(2, idCat);
//        preparedStatement.setString(3, url);
//        preparedStatement.setInt(4, prioridad);
//        preparedStatement.setString(5, estado);
//        preparedStatement.setInt(6, id);
//        
//        // Ejecutar la consulta de actualización
//        int filasActualizadas = preparedStatement.executeUpdate();
//        
//        if (filasActualizadas > 0) {
//            System.out.println("La fila fue actualizada exitosamente.");
//        } else {
//            System.out.println("No se encontró la fila a actualizar.");
//        }
//    } finally {
//        // Cerrar los recursos en un bloque finally
//        if (preparedStatement != null) {
//            preparedStatement.close();
//        }
//        if (connection != null) {
//            connection.close();
//        }
//    }
//    }
    public static void eliminarSolicitud(String nombreSolicitud, String tipoSolicitud, String estado, String descripcion, int idPdf, int idUsuario, Connection connection) throws SQLException {

        // Llamar al procedimiento almacenado
        CallableStatement statement = connection.prepareCall("{CALL AgregarSolicitud(?, ?, ?, ?, ?, ?)}");
        statement.setString(1, nombreSolicitud);
        statement.setString(2, tipoSolicitud);
        statement.setString(3, estado);
        statement.setString(4, descripcion);
        if (idPdf == 0) {
            statement.setNull(5, java.sql.Types.INTEGER); // Establecer el parámetro como NULL
        } else {
            statement.setInt(5, idPdf); // Establecer el ID del PDF si es diferente de 0
        }
        statement.setInt(6, idUsuario);
        statement.execute();
    }

    public static String mostrarInfoSolicitud(Solicitudes sol, String correo) {
        if (sol == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula.");
        }

        String HTML = "<div class=\"form\">\n"
                + "                        <h2>Responder solicitud</h2>\n"
                + "                        <hr>\n"
                + "                        <div class=\"row\">\n"
                + "                            <div class=\"col\">\n"
                + "                                <div class=\"form-element\">\n"
                + "                                    <label for=\"nombre\">Nombre</label>\n"
                + "                                    <input type=\"text\" value=\"" + sol.getNombreSol() + "\" id=\"nombre\" name=\"nombre\">\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                            <div class=\"col\">\n"
                + "                                <div class=\"form-element\">\n"
                + "                                    <label for=\"correo\">Correo</label>\n"
                + "                                    <input type=\"text\" value=\"" + correo + "\" id=\"correo\" name=\"correo\">\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <div class=\"row\">\n"
                + "                            <div class=\"col\">\n"
                + "                                <div class=\"form-element\">\n"
                + "                                    <label for=\"descripcion\">Descripción</label>\n"
                + "                                    <textarea id=\"descripcion\" name=\"descripcion\" rows=\"4\" cols=\"50\">" + sol.getDescripcion() + "</textarea>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                            <div class=\"col\">\n"
                + "                                <div class=\"form-element\">\n"
                + "                                    <label for=\"respuesta\">Respuesta</label>\n"
                + "                                    <textarea id=\"respuesta\" name=\"respuesta\" rows=\"4\" cols=\"50\" placeholder=\"Ingrese la respuesta a la solicitud\"></textarea>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <div class=\"row\">\n"
                + "                            <div class=\"col\">\n"
                + "                                <div class=\"form-element\">\n"
                + "                                    <button type=\"submit\">Enviar</button>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>";
        return HTML;
    }

    public static Solicitudes obtenerSolicitudPorId(int idSolicitud, Connection conn) throws SQLException {

        // Inicializar la solicitud como nula
        Solicitudes solicitud = new Solicitudes();

        // Declarar la conexión, el PreparedStatement y el ResultSet como variables locales
        PreparedStatement consulta = conn.prepareStatement("SELECT * FROM solicitudes WHERE idSolicitud = ?");
        {
            // Establecer el parámetro en la consulta preparada
            consulta.setInt(1, idSolicitud);

            // Ejecutar la consulta
            ResultSet resultado = consulta.executeQuery();

            // Verificar si se encontró una solicitud con el ID proporcionado
            if (resultado.next()) {
                // Crear un objeto de solicitud con los datos obtenidos de la base de datos
                solicitud.setIdSolicitud(resultado.getInt("idSolicitud"));
                solicitud.setNombreSol(resultado.getString("nombreSolicitud"));
                solicitud.setFechaRegistro(resultado.getDate("fechaRegistro"));
                solicitud.setEstado(resultado.getString("estado"));
                solicitud.setDescripcion(resultado.getString("descripcion"));
                solicitud.setPdf(resultado.getString("idPdf"));
                solicitud.setUsuario(resultado.getString("idUsuario"));
                // Agregar más atributos según sea necesario
            }

            // Devolver la solicitud (puede ser nula si no se encontró ninguna solicitud con el ID proporcionado)
            return solicitud;
        }
    }
    
        public static Usuarios obtenerUsuarioPorId(int idUsuario, Connection conn) throws SQLException {
        // Inicializar el usuario como nulo
        Usuarios usuario = new Usuarios();

        // Declarar el PreparedStatement y el ResultSet como variables locales
        try (PreparedStatement consulta = conn.prepareStatement("SELECT * FROM usuarios WHERE idUsuario = ?");
        ) {
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
}
