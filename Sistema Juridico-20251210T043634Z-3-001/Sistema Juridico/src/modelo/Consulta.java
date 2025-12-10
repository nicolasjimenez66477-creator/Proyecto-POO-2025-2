package modelo;

import java.util.Date;

public class Consulta {
    private String id;
    private String pregunta;
    private String respuesta;
    private Date fecha;
    private Usuario usuario;
    private String categoria;
    
    public Consulta(String pregunta, Usuario usuario) {
        this.id = "CONS_" + System.currentTimeMillis();
        this.pregunta = pregunta;
        this.usuario = usuario;
        this.fecha = new Date();
        this.categoria = clasificarCategoria();
    }
    
    // ENCAPSULACIÓN: lógica interna privada
    private String clasificarCategoria() {
        if (pregunta.toLowerCase().contains("expulsi")) {
            return "DISCIPLINARIO";
        }
        return "GENERAL";
    }
    
    // Getters
    public String getId() { return id; }
    public String getPregunta() { return pregunta; }
    public String getRespuesta() { return respuesta; }
    public Date getFecha() { return fecha; }
    public Usuario getUsuario() { return usuario; }
    public String getCategoria() { return categoria; }
    
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    @Override
    public String toString() {
        return "Consulta[" + categoria + "] " + pregunta.substring(0, Math.min(50, pregunta.length())) + "...";
    }
}