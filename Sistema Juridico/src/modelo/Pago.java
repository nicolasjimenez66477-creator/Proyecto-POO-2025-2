package modelo;

import java.util.Date;
import java.util.UUID;

// RF-04: Registrar pagos de membresía
public class Pago {
    // ENCAPSULACIÓN
    private String id;
    private Usuario usuario;
    private double monto;
    private Date fecha;
    private String estado; // "COMPLETADO", "PENDIENTE", "RECHAZADO"
    private String tipoMembresia;
    private String metodoPago;
    
    // ENUM para estados (concepto POO: enumeración)
    public enum EstadoPago {
        COMPLETADO, PENDIENTE, RECHAZADO, REEMBOLSADO
    }
    
    // Constructor
    public Pago(Usuario usuario, double monto, String tipoMembresia, String metodoPago) {
        this.id = "PAGO_" + UUID.randomUUID().toString().substring(0, 8);
        this.usuario = usuario;
        this.monto = monto;
        this.fecha = new Date();
        this.estado = EstadoPago.COMPLETADO.toString();
        this.tipoMembresia = tipoMembresia;
        this.metodoPago = metodoPago;
    }
    
    // Getters
    public String getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public double getMonto() { return monto; }
    public Date getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public String getTipoMembresia() { return tipoMembresia; }
    public String getMetodoPago() { return metodoPago; }
    
    // Setters
    public void setEstado(String estado) { this.estado = estado; }
    
    @Override
    public String toString() {
        return "Pago #" + id + " - $" + monto + " - " + tipoMembresia + " - " + estado;
    }
}