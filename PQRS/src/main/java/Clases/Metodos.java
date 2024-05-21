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
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Metodos
 *
 * @author Johan- María
 */
public class Metodos {

    /**
     * agregarPdf
     * Metodo para agregar el PDF
     */
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
    /**
     * agregarSolicitud
     * Metodo para agregar la solicitud
     */
    public static void agregarSolicitud(String nombreSolicitud, String tipoSolicitud, String estado, String descripcion, String pdf, int idUsuario, String respuesta, Connection connection) throws SQLException {

        // Llamar al procedimiento almacenado
        CallableStatement statement = connection.prepareCall("{CALL AgregarSolicitud(?, ?, ?, ?, ?, ?, ?)}");
        //Establecer los parametros
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
    /**
     * getSolicitudes
     * Metodo para obtener TODAS las solicitudes
     */
    public static ArrayList<Solicitudes> getSolicitudes() throws ClassNotFoundException {
        ArrayList<Solicitudes> array = new ArrayList();//Nuevo array
        //Conexion
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
                //Llenar el objeto
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
            }

            // Cerrar la conexión
            resultSetSolicitud.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones
        }
        //Ordenar por fecha
        Collections.sort(array, new Fechas());
        return array;//Devolver el array
    }
    /**
     * getSolicitudesUs
     * Obtener las solicitudes de los Usuarios
     */
    public static ArrayList<Solicitudes> getSolicitudesUs(String cedula) throws ClassNotFoundException {
        ArrayList<Solicitudes> array = new ArrayList();//Nuevo array a llenar
        //Conexion
        Conexion conexion = new Conexion();
        Connection connection = conexion.establecerConexion();
        try {

            // Consulta SQL para obtener datos de la tabla tutorial
            String sqlTutorial = "SELECT * FROM pqrs.solicitudes left JOIN  usuarios  on usuarios.idUsuario=solicitudes.idUsuario where cedula=" + cedula;

            // Crear una declaración para la consulta de tutoriales
            Statement statement = connection.createStatement();
            ResultSet resultSetSolicitud = statement.executeQuery(sqlTutorial);

            // Iterar sobre los resultados de tutoriales y almacenarlos en el array
            while (resultSetSolicitud.next()) {
                Solicitudes sol = new Solicitudes();
                //Llenar el objeto
                sol.setIdSolicitud(resultSetSolicitud.getInt("idSolicitud"));
                sol.setNombreSol(resultSetSolicitud.getString("nombreSolicitud"));
                sol.setTipoSolicitud(resultSetSolicitud.getString("tipoSolicitud"));

                sol.setFechaRegistro(resultSetSolicitud.getDate("fechaRegistro"));
                sol.setEstado(resultSetSolicitud.getString("estado"));
                sol.setDescripcion(resultSetSolicitud.getString("descripcion"));
                sol.setPdf(resultSetSolicitud.getString("pdf"));
                sol.setUsuario(resultSetSolicitud.getString("cedula"));
                sol.setRespuesta(resultSetSolicitud.getString("respuesta"));
                array.add(sol);//Agregar al array
            }

            // Cerrar la conexión
            resultSetSolicitud.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones
        }
        //Ordenar por fecha
        Collections.sort(array, new Fechas());
        return array;
    }
    /**
     * ListarAdministradores
     * Generar la lista para los administradores
     */
    public static String listarAdministradores(Solicitudes sol, HttpServletRequest request) throws SQLException {

        // Inicializar fechaLimite como null
        Calendar fechaLimite = null;
        if ((sol.getEstado().equals("Por responder")) && sol.getTipoSolicitud().equals("Peticion")) {
            // Obtener la fecha actual
            Calendar fechaActuall = Calendar.getInstance();
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
            // Verificar si la fecha límite no es nula y si la fecha actual es posterior a la fecha límite
            if (fechaLimite != null && fechaActuall.after(fechaLimite)) {
                // La fecha actual ha pasado la fecha límite, por lo tanto, la solicitud está vencida
                if (sol.getEstado().equals("Por responder")) {
                    Conexion conexion = new Conexion();
                    Connection conn = conexion.establecerConexion();
                    int usuario = MetodosUsuarios.obtenerUsuarioPorIdSolicitud(sol.getIdSolicitud(), conn);
                    Usuarios user = MetodosUsuarios.obtenerUsuarioPorId(usuario, conn);
                    sol.setEstado("Vencido");
                    System.out.println("---------" + sol.getEstado());
                    EditarEstado(sol.getIdSolicitud(), "Vencido");
                    enviarCorreo(user.getCorreo(), sol.getNombreSol());

                }
            }
        }

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

        if (tipoUsuario.equals("administrador") && (sol.getEstado().equals("Por responder")) || (sol.getEstado().equals("Vencido"))) {

            if (sol.getEstado().equals("Vencido")) {
                HTML += "        <h6 style=\"color: red; text-align: justify;\">¡Esta solicitud está vencida!</h6>\n";
            }
        } 
        if(tipoUsuario.equals("administrador") && (sol.getEstado().equals("Por responder")) ){
            // Verificar si la fecha límite no es nula y si la fecha de registro es anterior a la fecha límite
            HTML += "        <a id='btnVisualizar'  data-nombre='" + sol.getIdSolicitud() + "'  href=\"#\">Responder Solicitud</a>\n";
        }

        HTML += "    </div>\n"
                + "</article>"
                + "<br>";
        return HTML;
    }
    /**
     * listarUsuario
     * Generar la lista para los usuarios
     */
    public static String listarUsuario(Solicitudes sol, HttpServletRequest request) throws SQLException {

        // Inicializar fechaLimite como null
        Calendar fechaLimite = null;
        if ((sol.getEstado().equals("Por responder")) && sol.getTipoSolicitud().equals("Peticion")) {
            // Obtener la fecha actual
            Calendar fechaActuall = Calendar.getInstance();
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
            // Verificar si la fecha límite no es nula y si la fecha actual es posterior a la fecha límite
            if (fechaLimite != null && fechaActuall.after(fechaLimite)) {
                // La fecha actual ha pasado la fecha límite, por lo tanto, la solicitud está vencida
                if (sol.getEstado().equals("Por responder")) {
                    Conexion conexion = new Conexion();
                    Connection conn = conexion.establecerConexion();
                    int usuario = MetodosUsuarios.obtenerUsuarioPorIdSolicitud(sol.getIdSolicitud(), conn);
                    Usuarios user = MetodosUsuarios.obtenerUsuarioPorId(usuario, conn);
                    sol.setEstado("Vencido");
                    System.out.println("---------" + sol.getEstado());
                    EditarEstado(sol.getIdSolicitud(), "Vencido");
                    enviarCorreo(user.getCorreo(), sol.getNombreSol());

                }
            }
        }
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
        if (!sol.getEstado().equals("Respondido") && !sol.getEstado().equals("Vencido")) {
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
            fechaLimite = Calendar.getInstance();
            // Establecer la fecha actual en el Calendar
            fechaLimite.setTime(fechaActual);
            // Sumar 15 días a la fecha actual
            fechaLimite.add(Calendar.DAY_OF_MONTH, 15);

            // Formatear la fecha límite
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechaLimiteFormateada = dateFormat.format(fechaLimite.getTime());

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

        if ((sol.getEstado().equals("Vencido"))) {
            HTML += "        <h6 style=\"color: red; text-align: justify;\">¡Esta solicitud está vencida!</h6>\n";
        }
        HTML += "    </div>\n"
                + "</article>"
                + "<br>";

        return HTML;
    }
    /**
     *  mensaje
     *  Genera un mensaje para indicar que no hay solicitudes
     */
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
    /**
     * EditarSolicitud
     */
    public static void EditarSolicitud(int id, String nombreSolicitud, String tipoSolicitud, String estado, String descripcion, String pdf, int idUsuario, String respuesta, Connection connection) throws SQLException {

        // Llamar al procedimiento almacenado
        CallableStatement statement = connection.prepareCall("{CALL editarSolicitud(?,?, ?, ?, ?, ?, ?, ?)}");
        //Parametros
        statement.setInt(1, id);
        statement.setString(2, nombreSolicitud);
        statement.setString(3, tipoSolicitud);
        statement.setString(4, estado);
        statement.setString(5, descripcion);
        //GestionPDF
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
    /**
     * EliminarSolicitud
     */
    public static void eliminarSolicitud(String nombreSolicitud, String tipoSolicitud, String estado, String descripcion, String pdf, int idUsuario, Connection connection) throws SQLException {

        // Llamar al procedimiento almacenado
        CallableStatement statement = connection.prepareCall("{CALL AgregarSolicitud(?, ?, ?, ?, ?, ?)}");
        //Parametros
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
    /**
     * mostrarInfoSolicitud
     */
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
    /**
     * obtenerSolicitudPorId 
     */
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

    /**
     * editarRespuestaEstado
     * Método para editar la respuesta de una solicitud en la base de datos
     */
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
    /**
     * editarInfoSolicitud
     * Método para editar la informacion de una solicitud en la base de datos
     */
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
                + "                <input type=\"hidden\" id=\"pdfv\" name=\"pdfv\" required  value=\"" + sol.getPdf() + "\">\n"
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
    /**
     * eliminarSolicitud
     */
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
    /**
     * obtenerSolicitud
     * Obtener un objeto de solicitud por medio del ID
     */
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
    /**
     * EditarEstado 
     */
    public static void EditarEstado(int id, String estado) throws SQLException {
        Conexion conexion = new Conexion();
        Connection connection = conexion.establecerConexion();
        // Llamar al procedimiento almacenado
        CallableStatement statement = connection.prepareCall("UPDATE solicitudes SET estado='" + estado + "' WHERE idSolicitud=" + id);
        statement.execute();
    }
    /**
     * enviarCorreo
     * Metodo para enviar correo  
     */
    public static void enviarCorreo(String correo, String nombre) {
        String emailFrom = "pqrsresponse@gmail.com"; // Cambia esto por tu dirección de correo electrónico
        String passwordFrom = "wkro wbtv mnoi gsjy"; // Cambia esto por tu contraseña
        String emailTo = correo; // Cambia esto por la dirección de correo electrónico del destinatario
        String subject = "Información de su petición: " + nombre;

        // Contenido del correo electrónico
        String content = "Cordial saludo," + "\n"
                + "Espero que este mensaje le encuentre bien. Quisiera informarle que se ha vencido el tiempo para su respuesta. Por favor contacte a soporte" + "\n"
                + "Mil disculpas.";

        // Crear instancia de EnvioCorreos y configurar detalles del correo
        EnvioCorreo envioCorreos = new EnvioCorreo();
        envioCorreos.setEmailFrom(emailFrom);
        envioCorreos.setPasswordFrom(passwordFrom);
        envioCorreos.setTo(emailTo);
        envioCorreos.setSubject(subject);
        envioCorreos.setContent(content);
        System.out.println("SE VA A ENVIAR");
        // Intentar enviar el correo electrónico
        try {
            envioCorreos.sendEmail();
            System.out.println("Correo enviado con exito");
        } catch (MessagingException ex) {
            System.out.println("Error al enviar el correo");
        }
    }

}
