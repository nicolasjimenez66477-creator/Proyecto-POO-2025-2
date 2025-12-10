package modelo;

import java.util.Date;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

// RF-20, RF-21, RF-22, RF-23, RF-24, RF-25: Sistema completo de documentos
public abstract class Documento {
    // ENCAPSULACIÓN
    private String id;
    private String titulo;
    private String contenido;
    private Date fechaCreacion;
    private Usuario creador;
    private String tipoDocumento;
    private String estado; // "BORRADOR", "FINALIZADO", "ARCHIVADO"
    
    // RF-25: Versionado básico de documentos
    private List<VersionDocumento> versiones;
    
    // RF-24: Vinculación con consulta
    private Consulta consultaRelacionada;
    
    // Constructor protegido para HERENCIA
    protected Documento(String titulo, Usuario creador, String tipoDocumento) {
        this.id = "DOC_" + UUID.randomUUID().toString().substring(0, 8);
        this.titulo = titulo;
        this.creador = creador;
        this.tipoDocumento = tipoDocumento;
        this.fechaCreacion = new Date();
        this.estado = "BORRADOR";
        this.versiones = new ArrayList<>();
        
        // Crear versión inicial
        guardarVersion("Creación del documento");
    }
    
    // POLIMORFISMO: método abstracto que cada tipo de documento implementa diferente
    public abstract void generarContenidoAutomatico();
    
    // RF-22: Editar documento
    public void editarContenido(String nuevoContenido, String descripcionCambio) {
        this.contenido = nuevoContenido;
        guardarVersion(descripcionCambio);
    }
    
    // RF-25: Guardar versión
    private void guardarVersion(String descripcion) {
        VersionDocumento version = new VersionDocumento(
            versiones.size() + 1,
            this.contenido,
            new Date(),
            this.creador,
            descripcion
        );
        versiones.add(version);
    }
    
    // Getters y setters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public Usuario getCreador() { return creador; }
    public String getTipoDocumento() { return tipoDocumento; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<VersionDocumento> getVersiones() { return new ArrayList<>(versiones); }
    
    public Consulta getConsultaRelacionada() { return consultaRelacionada; }
    public void setConsultaRelacionada(Consulta consulta) { this.consultaRelacionada = consulta; }
    
    @Override
    public String toString() {
        return titulo + " (" + tipoDocumento + ") - " + estado;
    }
}