package modelo;

public class Administrador extends Usuario {
    private String nivelAcceso;
    
    public Administrador(String nombre, String email, String contrasena,
                        String institucion, String secretariaEducacion) {
        super(nombre, email, contrasena, institucion, secretariaEducacion);
        this.nivelAcceso = "COMPLETO";
    }
    
    @Override
    public String getTipoUsuario() {
        return "ADMINISTRADOR";
    }
    
    public String getNivelAcceso() {
        return nivelAcceso;
    }
    
    public void setNivelAcceso(String nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }
}