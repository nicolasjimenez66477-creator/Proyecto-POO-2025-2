package modelo;

public class Abogado extends Usuario {
    private String especialidad;
    private String numeroLicencia;
    
    public Abogado(String nombre, String email, String contrasena,
                  String institucion, String secretariaEducacion) {
        super(nombre, email, contrasena, institucion, secretariaEducacion);
        this.especialidad = "Derecho Educativo";
        this.numeroLicencia = "";
    }
    
    @Override
    public String getTipoUsuario() {
        return "ABOGADO";
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public String getNumeroLicencia() {
        return numeroLicencia;
    }
    
    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }
}