package modelo;

import java.util.List;
import java.util.ArrayList;

// RF-20: Catálogo de plantillas jurídicas educativas
public class PlantillaDocumento {
    private String nombre;
    private String descripcion;
    private String tipoDocumento;
    private List<CampoFormulario> campos;
    
    public PlantillaDocumento(String nombre, String descripcion, 
                             String tipoDocumento, List<CampoFormulario> campos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoDocumento = tipoDocumento;
        this.campos = new ArrayList<>(campos); // Crear copia para encapsulación
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getTipoDocumento() { return tipoDocumento; }
    public List<CampoFormulario> getCampos() { return new ArrayList<>(campos); }
    
    @Override
    public String toString() {
        return nombre + " - " + descripcion;
    }
}