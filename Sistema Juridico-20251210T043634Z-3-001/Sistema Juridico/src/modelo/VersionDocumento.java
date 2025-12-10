package modelo;

import java.util.Date;

// RF-25: Mantener versionado básico de documentos
public class VersionDocumento {
    private int numeroVersion;
    private String contenido;
    private Date fechaModificacion;
    private Usuario autorModificacion;
    private String descripcionCambio;
    
    public VersionDocumento(int numeroVersion, String contenido, Date fechaModificacion,
                           Usuario autorModificacion, String descripcionCambio) {
        this.numeroVersion = numeroVersion;
        this.contenido = contenido;
        this.fechaModificacion = fechaModificacion;
        this.autorModificacion = autorModificacion;
        this.descripcionCambio = descripcionCambio;
    }
    
    // Getters
    public int getNumeroVersion() { return numeroVersion; }
    public String getContenido() { return contenido; }
    public Date getFechaModificacion() { return fechaModificacion; }
    public Usuario getAutorModificacion() { return autorModificacion; }
    public String getDescripcionCambio() { return descripcionCambio; }
    
    @Override
    public String toString() {
        return "v" + numeroVersion + " - " + fechaModificacion + " - " + descripcionCambio;
    }
}