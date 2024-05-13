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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
                request.getRequestDispatcher("usuario.jsp").forward(request, response);
            } else if (idRol == 2) { // Si es Administrador
                tipoUsuario = "administrador";
                session.setAttribute("tipoUsuario", tipoUsuario);
                // Redirigir a la página JSP
                String toastr = "siPasa";
                // Establecer el atributo en el objeto HttpServletRequest
                request.setAttribute("toastr", toastr);
                request.getRequestDispatcher("solicitudes.jsp").forward(request, response);
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
        return pdfText;
    }

    public static void agregarSolicitud(String nombreSolicitud, String tipoSolicitud, String estado, String descripcion, String pdf, int idUsuario, String respuesta, Connection connection) throws SQLException {

        // Llamar al procedimiento almacenado
        CallableStatement statement = connection.prepareCall("{CALL AgregarSolicitud(?, ?, ?, ?, ?, ?, ?)}");
        statement.setString(1, nombreSolicitud);
        statement.setString(2, tipoSolicitud);
        statement.setString(3, estado);
        statement.setString(4, descripcion);
        if (pdf.equals("")) {
            statement.setNull(5, java.sql.Types.VARCHAR); // Establecer el parámetro como NULL
        } else {
            statement.setString(5, pdf); // Establecer el ID del PDF si es diferente de 0
        }
        statement.setInt(6, idUsuario);
        if (respuesta.equals("")) {
            statement.setNull(7, java.sql.Types.VARCHAR); // Establecer el parámetro como NULL
        } else {
            statement.setString(7, respuesta); // Establecer la respuesta como cadena de texto si no está vacía
        }
        statement.execute();
    }


    public static ArrayList<Solicitudes> getSolicitudes() throws ClassNotFoundException {
        ArrayList<Solicitudes> array = new ArrayList();
        Conexion conexion = new Conexion();
        Connection connection = conexion.establecerConexion();
        try {

            // Consulta SQL para obtener datos de la tabla tutorial
            String sqlTutorial = "SELECT * FROM solicitudes left join usuarios  on usuarios.idUsuario=solicitudes.idUsuario";

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
                sol.setPdf(resultSetSolicitud.getString("pdf"));
                sol.setUsuario(resultSetSolicitud.getString("cedula"));
                sol.setRespuesta(resultSetSolicitud.getString("respuesta"));
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

    public static String listarAdministradores(Solicitudes sol, HttpServletRequest request) {

        // Inicializar fechaLimite como null
        Calendar fechaLimite = null;

        String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
        String HTML = "<article class=\"card article-container\">\n"
                + "    <div class=\"card-header\">\n"
                + "        <div>                                \n"
                + "            <h3 style=\"text-align: justify;\">Nombre: " + sol.getNombreSol() + "</h3>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"card-body\">\n"
                + "        <h4 style=\"text-align: justify;\">Tipo Solicitud: " + sol.getTipoSolicitud() + "</h4>\n"
                + "        <h4 style=\"text-align: justify;\">Fecha Registro: " + sol.getFechaRegistro() + "</h4>\n"
                + "        <h4 style=\"text-align: justify;\">Estado: " + sol.getEstado() + "</h4>\n";
        if (sol.getPdf() != null) {
            HTML += "        <h4 style=\"text-align: justify;\">PDF:</h4>\n";
            // Mostrar el PDF como una imagen y proporcionar un enlace de descarga
            HTML += "    <div class=\"pdf-container\" style=\"width: 100px; height: 120px; margin-left: 80px; margin-top: -30px;\">\n";
            HTML += "        <a href=\"pdfs/" + sol.getPdf() + "\" download>\n";
            HTML += "            <img src=\"img/pdf.png\" alt=\"PDF\">\n";
            HTML += "        </a>\n";
            HTML += "    </div>\n";
        }
        String descripcion = sol.getDescripcion();
        String idDescripcion = "descripcion-" + sol.getIdSolicitud(); // Genera un ID único para cada descripción
        String descripcionAbreviada = descripcion.length() > 300 ? descripcion.substring(0, 300) + "..." : descripcion;

        HTML += "        <h4 id=\"" + idDescripcion + "\" style=\"text-align: justify;\">Descripcion: <span class=\"short-description\">" + descripcionAbreviada + "</span></h4>\n";
        if (descripcion.length() > 300) {
            HTML += "        <h6><a href=\"#\" class=\"expandir-descripcion\" data-target=\"" + idDescripcion + "\" data-full-description=\"" + descripcion + "\">Leer más</a></h6>\n"
                    + "<br>";
        }

        // Verificar si se necesita calcular la fecha límite
        if (sol.getTipoSolicitud().equals("Peticion")) {
            Date fechaActual = sol.getFechaRegistro();
            // Crear una instancia de Calendar
            fechaLimite = Calendar.getInstance();
            // Establecer la fecha actual en el Calendar
            fechaLimite.setTime(fechaActual);
            // Sumar 15 días a la fecha actual
            fechaLimite.add(Calendar.DAY_OF_MONTH, 15);

            // Formatear la fecha límite
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaLimiteFormateada = dateFormat.format(fechaLimite.getTime());

            HTML += "        <h4 style=\"text-align: justify;\">Fecha limite de respuesta: " + fechaLimiteFormateada + "</h4>\n";
        }

        if (tipoUsuario.equals("administrador")) {
            HTML += "        <h4 style=\"text-align: justify;\">Usuario: " + sol.getUsuario() + "</h4>\n"
                    + "<hr>";
        }

        HTML += "    <div class=\"card-body\">\n";

        if (sol.getEstado().equals("Respondido")) {
            HTML += "        <div>    "
                    + "            <h3>Respuesta</h3>\n"
                    + "        </div>\n"
                    + "        <div>    ";
            HTML += "            <h4 style=\"text-align: justify;\">Descripcion: " + sol.getRespuesta() + "</h4>\n";
            HTML += "        </div>\n";
        }
        HTML += "    </div>\n";

        HTML += "    </div>\n"
                + "    <div class=\"card-footer\">\n";

        if (tipoUsuario.equals("administrador") && (sol.getEstado().equals("Por responder"))) {
            // Verificar si la fecha límite no es nula y si la fecha de registro es anterior a la fecha límite
            HTML += "        <a id='btnVisualizar'  data-nombre='" + sol.getIdSolicitud() + "'  href=\"#\">Responder Solicitud</a>\n";

        }
        HTML += "    </div>\n"
                + "</article>"
                + "<br>";
        return HTML;
    }

    public static String listarUsuario(Solicitudes sol, HttpServletRequest request) {
        String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
        String HTML = "<article class=\"card\">\n"
                + "    <div class=\"card-header\">\n"
                + "        <div>                                \n"
                + "            <h3>Nombre: " + sol.getNombreSol() + "</h3>\n"
                + "        </div>\n"
                + "        <label class=\"toggle\">\n"
                + "            <div class='btn-group'>\n"
                + "                <button type='button' class='btn btn-secondary dropdown-toggle' data-bs-toggle='dropdown' aria-expanded='false'>\n"
                + "                    Opciones\n"
                + "                </button>\n"
                + "                <ul class='dropdown-menu'>\n";
        if (!sol.getEstado().equals("Respondido")) {
            HTML += "                    <li><a id='btnEditar' class=\"btn btn-success\" data-nombre='" + sol.getIdSolicitud() + "'  href=\"#\">Editar</a></li>\n"
                    + "                    <li><hr class='dropdown-divider'></li>\n";
        }
        HTML += "                    <li><a href=\"#\" class=\"btn btn-danger deleteButton\" id=\"deleteButton\" data-titulo='" + sol.getIdSolicitud() + "' >Eliminar</a></li>\n"
                + "                </ul>\n"
                + "            </div>\n"
                + "        </label>\n"
                + "    </div>\n"
                + "    <div class=\"card-body\">\n"
                + "        <h4>Tipo Solicitud: " + sol.getTipoSolicitud() + "</h4>\n"
                + "        <h4>Fecha Registro: " + sol.getFechaRegistro() + "</h4>\n"
                + "        <h4>Estado: " + sol.getEstado() + "</h4>\n";
        if (sol.getPdf() != null) {
            HTML += "        <h4>PDF:</h4>\n";
            // Mostrar el PDF como una imagen y proporcionar un enlace de descarga
            HTML += "    <div class=\"pdf-container\" style=\"width: 100px; height: 120px; margin-left: 80px; margin-top: -30px;\">\n";
            HTML += "        <a href=\"pdfs/" + sol.getPdf() + "\" download>\n";
            HTML += "            <img src=\"img/pdf.png\" alt=\"PDF\">\n";
            HTML += "        </a>\n";
            HTML += "    </div>\n";
        }
        String descripcion = sol.getDescripcion();
        String idDescripcion = "descripcion-" + sol.getIdSolicitud(); // Genera un ID único para cada descripción
        String descripcionAbreviada = descripcion.length() > 300 ? descripcion.substring(0, 300) + "..." : descripcion;

        HTML += "        <h4 id=\"" + idDescripcion + "\" style=\"text-align: justify;\">Descripcion: <span class=\"short-description\">" + descripcionAbreviada + "</span></h4>\n";
        if (descripcion.length() > 300) {
            HTML += "        <h6><a href=\"#\" class=\"expandir-descripcion\" data-target=\"" + idDescripcion + "\" data-full-description=\"" + descripcion + "\">Leer más</a></h6>\n"
                    + "<br>";
        }
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

        HTML += "<hr>\n";

        HTML += "    <div class=\"card-body\">\n";

        if (sol.getEstado().equals("Respondido")) {
            HTML += "        <div>    "
                    + "            <h3>Respuesta</h3>\n"
                    + "        </div>\n"
                    + "        <div>    ";
            HTML += "            <h4>Descripcion: " + sol.getRespuesta() + "</h4>\n";
            HTML += "        </div>\n";
        }
        HTML += "    </div>\n";

        HTML += "    </div>\n"
                + "    <div class=\"card-footer\">\n";
        if (tipoUsuario.equals("administrador") && (sol.getEstado().equals("Por responder"))) {
            HTML += "        <a id='btnVisualizar'  data-nombre='" + sol.getIdSolicitud() + "'  href=\"#\">Responder Solicitud</a>\n";
        }
        HTML += "    </div>\n"
                + "</article>"
                + "<br>";
        return HTML;
    }

    public static String mensaje(HttpServletRequest request) {
        String HTML = "<article class=\"card\">\n"
                + "    <div class=\"card-footer\">\n"
                + "</div>"
                + "<br>";
        HTML += "        <h4 style=\"text-align: center;\"><span class=\"short-description\">No se han registrado solicitudes</span></h4>\n"
                + "<br>"
                + "    <div class=\"card-footer\">\n";
        HTML += "    </div>\n"
                + "</article>"
                + "<br>";
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

    public static void EditarSolicitud(int id, String nombreSolicitud, String tipoSolicitud, String estado, String descripcion, String pdf, int idUsuario, String respuesta, Connection connection) throws SQLException {

        // Llamar al procedimiento almacenado
        CallableStatement statement = connection.prepareCall("{CALL editarSolicitud(?,?, ?, ?, ?, ?, ?, ?)}");
        statement.setInt(1, id);
        statement.setString(2, nombreSolicitud);
        statement.setString(3, tipoSolicitud);
        statement.setString(4, estado);
        statement.setString(5, descripcion);
        if (pdf.equals("")) {
            statement.setNull(6, java.sql.Types.VARCHAR); // Establecer el parámetro como NULL
        } else {
            statement.setString(6, pdf); // Establecer el ID del PDF si es diferente de 0
        }
        statement.setInt(7, idUsuario);
        if (respuesta.equals("")) {
            statement.setNull(8, java.sql.Types.VARCHAR); // Establecer el parámetro como NULL
        } else {
            statement.setString(8, respuesta); // Establecer la respuesta como cadena de texto si no está vacía
        }
        statement.execute();

    }

    public static void eliminarSolicitud(String nombreSolicitud, String tipoSolicitud, String estado, String descripcion, String pdf, int idUsuario, Connection connection) throws SQLException {

        // Llamar al procedimiento almacenado
        CallableStatement statement = connection.prepareCall("{CALL AgregarSolicitud(?, ?, ?, ?, ?, ?)}");
        statement.setString(1, nombreSolicitud);
        statement.setString(2, tipoSolicitud);
        statement.setString(3, estado);
        statement.setString(4, descripcion);
        if (pdf == null) {
            statement.setNull(5, java.sql.Types.VARCHAR); // Establecer el parámetro como NULL
        } else {
            statement.setString(5, pdf); // Establecer el ID del PDF si es diferente de 0
        }
        statement.setInt(6, idUsuario);
        statement.execute();
    }

    public static String mostrarInfoSolicitud(Solicitudes sol, Usuarios usuario) {
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
                + "                                    <input type=\"text\" value=\"" + sol.getNombreSol() + "\" id=\"nombre\" name=\"nombre\" readonly>\n"
                + "                                </div>\n"
                + "                                <div class=\"idOculto\">\n"
                + "                                    <label for=\"nombre\">Nombre</label>\n"
                + "                                    <input type=\"text\" value=\"" + sol.getIdSolicitud() + "\" id=\"idOculto\" name=\"idOculto\" readonly>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                            <div class=\"col\">\n"
                + "                                <div class=\"form-element\">\n"
                + "                                    <label for=\"correo\">Correo</label>\n"
                + "                                    <input type=\"text\" value=\"" + usuario.getCorreo() + "\" id=\"correo\" name=\"correo\" readonly>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                        <div class=\"row\">\n"
                + "                            <div class=\"col\">\n"
                + "                                <div class=\"form-element\">\n"
                + "                                    <label for=\"descripcion\">Descripción</label>\n"
                + "                                    <textarea id=\"descripcion\" name=\"descripcion\" rows=\"4\" cols=\"50\" readonly>" + sol.getDescripcion() + "</textarea>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                            <div class=\"col\">\n"
                + "                                <div class=\"form-element\">\n"
                + "                                    <label for=\"respuesta\">Respuesta</label>\n"
                + "                                    <textarea id=\"respuesta\" name=\"respuesta\" rows=\"4\" cols=\"50\" placeholder=\"Ingrese la respuesta a la solicitud\" required></textarea>\n"
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
                solicitud.setPdf(resultado.getString("pdf"));
                solicitud.setUsuario(resultado.getString("idUsuario"));
                solicitud.setRespuesta(resultado.getString("respuesta"));
                solicitud.setTipoSolicitud(resultado.getString("tipoSolicitud"));
            }

            // Devolver la solicitud (puede ser nula si no se encontró ninguna solicitud con el ID proporcionado)
            return solicitud;
        }
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

    // Método para editar la respuesta de una solicitud en la base de datos
    public static void editarRespuestaEstado(int idSolicitud, String respuesta, String estado, Connection conn) throws SQLException {
        // Definir la llamada al procedimiento almacenado
        String sql = "{CALL editarRespuesta(?, ?, ?)}";

        // Crear el objeto CallableStatement
        try (CallableStatement cs = conn.prepareCall(sql)) {
            // Establecer los parámetros del procedimiento almacenado
            cs.setInt(1, idSolicitud);
            cs.setString(2, respuesta);
            cs.setString(3, estado);

            // Ejecutar el procedimiento almacenado
            cs.executeUpdate();
        }
    }

    public static String editarInfoSolicitud(Solicitudes sol) {
        if (sol == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula.");
        }
        System.out.println(sol.getTipoSolicitud());
        String HTML = "<div class=\"form\">\n"
                + "    <h2>Editar solicitud</h2>\n"
                + "    <hr>\n"
                + "    <div class=\"row\">\n"
                + "        <div class=\"col\">\n"
                + "            <div class=\"form-element\">\n"
                + "                <label for=\"nombre\">Nombre</label>\n"
                + "                <input type=\"hidden\" id=\"id\" name=\"id\" required  value=\"" + sol.getIdSolicitud() + "\">\n"
                + "                <input type=\"text\" id=\"nombre\" name=\"nombre\" placeholder=\"Ingresa el nombre de la solicitud\" maxlength=\"50\" required pattern=\"[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+\" title=\"No se permiten números\" value=\"" + sol.getNombreSol() + "\">\n"
                + "            </div>\n"
                + "        </div>\n"
                + "        <div class=\"col\">\n"
                + "            <div class=\"form-element\">\n"
                + "                <label for=\"tipoSolicitud\">Tipo de solicitud</label>\n"
                + "                <select class=\"form-control\" id=\"tipo\" name=\"tipo\" required>\n"
                + "                    <option value=\"\" disabled>Seleccione el tipo de solicitud</option>\n"
                + "                    <option value=\"Peticion\"" + (sol.getTipoSolicitud().equals("Peticion") ? " selected" : "") + ">Peticion</option>\n"
                + "                    <option value=\"Queja\"" + (sol.getTipoSolicitud().equals("Queja") ? " selected" : "") + ">Queja</option>\n"
                + "                    <option value=\"Reclamo\"" + (sol.getTipoSolicitud().equals("Reclamo") ? " selected" : "") + ">Reclamo</option>\n"
                + "                    <option value=\"Sugerencia\"" + (sol.getTipoSolicitud().equals("Sugerencia") ? " selected" : "") + ">Sugerencia</option>\n"
                + "                </select>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"row\">\n"
                + "        <div class=\"col\">\n"
                + "            <div class=\"form-element\">\n"
                + "                <label for=\"descripcion\">Descripción</label>\n"
                + "                <textarea id=\"descripcion\" name=\"descripcion\" rows=\"4\" cols=\"50\" placeholder=\"Ingresa la descripción de la solicitud\">" + sol.getDescripcion() + "</textarea>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "        <div class=\"col\">\n"
                + "            <div class=\"form-element\">\n"
                + "                <label for=\"pdf\">Subir archivo</label>\n"
                + "                <input type=\"file\" id=\"pdf\" name=\"pdf\" accept=\"application/pdf\">\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "    <div class=\"row\">\n"
                + "        <div class=\"col\">\n"
                + "            <div class=\"form-element\">\n"
                + "                <button type=\"submit\">Agregar</button>\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</div>";
        return HTML;
    }

    public static void eliminarSolicitud(int id, Connection connection) throws SQLException {

        PreparedStatement preparedStatement = null;

        try {
            // Consulta SQL parametrizada para eliminar la fila
            String sqlTutorial = "DELETE FROM solicitudes WHERE idSolicitud = ?";

            // Crear una declaración preparada para la consulta de eliminación
            preparedStatement = connection.prepareStatement(sqlTutorial);

            // Establecer el parámetro de la consulta
            preparedStatement.setInt(1, id);

            // Ejecutar la consulta de eliminación
            int filasEliminadas = preparedStatement.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("La fila fue eliminada exitosamente.");
            } else {
                System.out.println("No se encontró la fila a eliminar.");
            }
        } finally {
            // Cerrar los recursos en un bloque finally
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static Solicitudes obtenerSolicitud(int id) throws ClassNotFoundException {
        Conexion conexion = new Conexion();
        Connection connection = conexion.establecerConexion();
        Solicitudes sol = new Solicitudes();
        try {

            // Consulta SQL para obtener datos de la tabla tutorial
            String sqlTutorial = "SELECT * FROM solicitudes left join usuarios  on usuarios.idUsuario=solicitudes.idUsuario WHERE idSolicitud=" + id;

            // Crear una declaración para la consulta de tutoriales
            Statement statement = connection.createStatement();
            ResultSet resultSetSolicitud = statement.executeQuery(sqlTutorial);

            // Iterar sobre los resultados de tutoriales y almacenarlos en el array
            while (resultSetSolicitud.next()) {

                sol.setIdSolicitud(resultSetSolicitud.getInt("idSolicitud"));
                sol.setNombreSol(resultSetSolicitud.getString("nombreSolicitud"));
                sol.setTipoSolicitud(resultSetSolicitud.getString("tipoSolicitud"));

                sol.setFechaRegistro(resultSetSolicitud.getDate("fechaRegistro"));
                sol.setEstado(resultSetSolicitud.getString("estado"));
                sol.setDescripcion(resultSetSolicitud.getString("descripcion"));
                sol.setPdf(resultSetSolicitud.getString("pdf"));
                sol.setUsuario(resultSetSolicitud.getString("cedula"));
                sol.setRespuesta(resultSetSolicitud.getString("respuesta"));
            }

            // Cerrar la conexión
            resultSetSolicitud.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones
        }
        return sol;
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

    public void editarUsuario(int idUsuario, String nombre, String apellido, String cedula, String celular, String correo, Connection conn) throws SQLException {

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
}
