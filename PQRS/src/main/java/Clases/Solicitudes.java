package Clases;

import java.sql.Date;


/**
 *
 * @author Johan Ordoñez
 */
public class Solicitudes {

    private int idSolicitud;
    private String nombreSol;
    private String tipoSolicitud;
    private Date fechaRegistro;
    private String estado;
    private String descripcion;
    private String pdf;
    private String usuario;


    public Solicitudes() {
    }

    public Solicitudes(int idSolicitud, String nombreSol, String tipoSolicitud, Date fechaRegistro, String estado, String descripcion, String pdf, String usuario) {
        this.idSolicitud = idSolicitud;
        this.nombreSol = nombreSol;
        this.tipoSolicitud = tipoSolicitud;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.descripcion = descripcion;
        this.pdf = pdf;
        this.usuario = usuario;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
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

    public String getNombreSol() {
        return nombreSol;
    }

    public void setNombreSol(String nombreSol) {
        this.nombreSol = nombreSol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
