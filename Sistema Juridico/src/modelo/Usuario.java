package modelo;

import java.util.UUID;

public abstract class Usuario {
    // ENCAPSULACIÓN: atributos privados
    private String id;
    private String nombre;
    private String email;
    private String contrasena;
    private String institucion;
    private String secretariaEducacion; // NUEVO: RF-02
    
    // Constructor protegido para HERENCIA
    protected Usuario(String nombre, String email, String contrasena, 
                     String institucion, String secretariaEducacion) {
        this.id = generarId();
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.institucion = institucion;
        this.secretariaEducacion = secretariaEducacion; // NUEVO
    }
    
    // POLIMORFISMO: método abstracto
    public abstract String getTipoUsuario();
    
    // ENCAPSULACIÓN: getters públicos
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getInstitucion() { return institucion; }
    public String getSecretariaEducacion() { return secretariaEducacion; } // NUEVO
    
    // Método privado para encapsulación interna
    private String generarId() {
        return "USR_" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    // Validación de credenciales
    public boolean validarCredenciales(String email, String contrasena) {
        return this.email.equals(email) && this.contrasena.equals(contrasena);
    }
    
    // NUEVO: Validación de correo institucional (RF-01)
    public static boolean esCorreoInstitucionalValido(String email) {
        return email != null && 
               email.matches("^[A-Za-z0-9+_.-]+@(.+\\.)?(edu\\.co|educacion\\.gov\\.co|ies\\..+\\.edu)$");
    }
    
    @Override
    public String toString() {
        return nombre + " (" + getTipoUsuario() + ") - " + institucion + " - " + secretariaEducacion;
    }
}