package modelo;

// RF-21: Campos del formulario estructurado
public class CampoFormulario {
    private String id;
    private String etiqueta;
    private String tipo; // "text", "textarea", "date", "number"
    private boolean obligatorio;
    
    public CampoFormulario(String id, String etiqueta, String tipo, boolean obligatorio) {
        this.id = id;
        this.etiqueta = etiqueta;
        this.tipo = tipo;
        this.obligatorio = obligatorio;
    }
    
    // Getters
    public String getId() { return id; }
    public String getEtiqueta() { return etiqueta; }
    public String getTipo() { return tipo; }
    public boolean isObligatorio() { return obligatorio; }
    
    @Override
    public String toString() {
        return etiqueta + (obligatorio ? " *" : "");
    }
}