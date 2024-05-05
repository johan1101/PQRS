package Clases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    

     public static ArrayList<Solicitudes> getSolicitudes( ) throws ClassNotFoundException {
        ArrayList<Solicitudes> array= new ArrayList();
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
                Solicitudes sol= new Solicitudes ();
                sol.setIdSolicitud(resultSetSolicitud.getInt("idSolicitud"));
                sol.setNombreSol (resultSetSolicitud.getString("nombreSolicitud"));
                sol.setTipoSolicitud(resultSetSolicitud.getString("tipoSolicitud"));
                
                sol.setFechaRegistro(resultSetSolicitud.getString("fechaRegistro"));
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

        return array;
    }
     
    public static ArrayList<Solicitudes> listarAdministradores(String par) throws ClassNotFoundException {
        ArrayList<Solicitudes> array= getSolicitudes();  
        //Caso hay parametro de busqueda
        if(par!=null){
          
        } 

        return array;
    }
}
