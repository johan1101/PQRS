package Clases;

/**
 *
 * @author Johan Ordoñez
 */
public class Usuarios {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String cedula;
    private String contrasena;
    private String celular;
    private String correo;
    private String rol;
    
    public Usuarios(){
        
    }

    public Usuarios(int idUsuario, String nombre, String apellido, String cedula, String contrasena, String celular, String correo, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.contrasena = contrasena;
        this.celular = celular;
        this.correo = correo;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    
}
