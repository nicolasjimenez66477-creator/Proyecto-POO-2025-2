package modelo;

public class Rector extends Usuario {
    private Membresia membresia;
    private String telefono;
    
    public Rector(String nombre, String email, String contrasena,
                 String institucion, String secretariaEducacion) {
        super(nombre, email, contrasena, institucion, secretariaEducacion);
        this.telefono = "";
    }
    
    @Override
    public String getTipoUsuario() {
        return "RECTOR";
    }
    
    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }
    
    public Membresia getMembresia() {
        return membresia;
    }
    
    public boolean puedeConsultar() {
        return membresia != null && membresia.puedeConsultar();
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}