package Clases;


/**
 *
 * @author Johan Ordoñez
 */
public class Solicitudes {

    private int idSolicitud;
    private String tipoSolicitud;
    private String fechaRegistro;
    private String estado;
    private String descripcion;
    private String pdf;

    public Solicitudes(int idSolicitud, String tipoSolicitud, String fechaRegistro, String estado, String descripcion, String pdf) {
        this.idSolicitud = idSolicitud;
        this.tipoSolicitud = tipoSolicitud;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.descripcion = descripcion;
        this.pdf = pdf;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

}
