package servicio;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioUsuarios {
    private List<Usuario> usuariosRegistrados = new ArrayList<>();
    
    public ServicioUsuarios() {
        // Crear usuarios de prueba con secretaría
        Rector rectorDemo = new Rector("Carlos Ruiz", "rector@colegio.edu.co", 
                                       "pass123", "Colegio Nacional", 
                                       "Secretaría de Educación de Bogotá");
        asignarMembresia(rectorDemo, "PREMIUM");
        usuariosRegistrados.add(rectorDemo);
        
        // Agregar más usuarios de prueba
        usuariosRegistrados.add(new Abogado("Ana Gómez", "ana@legal.edu.co", 
                                           "pass456", "Firma Legal", 
                                           "Secretaría Nacional"));
        usuariosRegistrados.add(new Administrador("Admin Sistema", "admin@sistema.edu.co", 
                                                 "admin123", "Sistema Legal", 
                                                 "General"));
    }
    
    // RF-01: Registro con validación de correo institucional
    public Usuario registrarUsuario(String tipo, String nombre, String email, 
                                   String contrasena, String institucion, 
                                   String secretariaEducacion) throws Exception {
        
        // Validar correo institucional
        if (!Usuario.esCorreoInstitucionalValido(email)) {
            throw new Exception("El correo debe ser institucional (termina en .edu.co o similar)");
        }
        
        // Verificar si el correo ya existe
        for (Usuario u : usuariosRegistrados) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                throw new Exception("El correo ya está registrado");
            }
        }
        
        // Crear usuario según tipo (FACTORY METHOD - patrón de diseño)
        Usuario nuevoUsuario;
        switch (tipo.toUpperCase()) {
            case "RECTOR":
                nuevoUsuario = new Rector(nombre, email, contrasena, institucion, secretariaEducacion);
                asignarMembresia((Rector) nuevoUsuario, "GRATUITA"); // Por defecto
                break;
            case "ABOGADO":
                nuevoUsuario = new Abogado(nombre, email, contrasena, institucion, secretariaEducacion);
                break;
            case "ADMINISTRADOR":
                nuevoUsuario = new Administrador(nombre, email, contrasena, institucion, secretariaEducacion);
                break;
            default:
                throw new Exception("Tipo de usuario no válido: " + tipo);
        }
        
        usuariosRegistrados.add(nuevoUsuario);
        return nuevoUsuario;
    }
    
    // RF-01: Login mejorado
    public Usuario autenticarUsuario(String email, String contrasena) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.validarCredenciales(email, contrasena)) {
                return usuario;
            }
        }
        return null;
    }
    
    // RF-03: Asignar membresía
    public void asignarMembresia(Rector rector, String tipoMembresia) {
        Membresia membresia;
        switch (tipoMembresia.toUpperCase()) {
            case "GRATUITA":
                membresia = new MembresiaGratuita();
                break;
            case "PREMIUM":
                membresia = new MembresiaPremium();
                break;
            case "INSTITUCIONAL":
                membresia = new MembresiaInstitucional(10);
                break;
            default:
                throw new IllegalArgumentException("Tipo de membresía no válido: " + tipoMembresia);
        }
        rector.setMembresia(membresia);
    }
    
    // RF-04: Sistema de pagos simulado
    public boolean procesarPago(Rector rector, String tipoMembresia, double monto) {
        // Simulación de pago
        System.out.println("Procesando pago de $" + monto + " para membresía " + tipoMembresia);
        
        // Actualizar membresía
        asignarMembresia(rector, tipoMembresia);
        
        return true; // Pago exitoso
    }
    
    // Getter para usuarios registrados
    public List<Usuario> getUsuariosRegistrados() {
        return new ArrayList<>(usuariosRegistrados);
    }
}