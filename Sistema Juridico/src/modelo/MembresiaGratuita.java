package modelo;

import java.util.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MembresiaGratuita implements Membresia {
    private int consultasRealizadas = 0;
    private int documentosGenerados = 0;
    private static final int LIMITE_CONSULTAS = 5;
    private static final int LIMITE_DOCUMENTOS = 2;
    private static final String DESCRIPCION = "Acceso limitado a consultas básicas";
    private Date fechaVencimiento;
    private boolean vigente;
    
    // Tipos de documentos permitidos (RF-03)
    private Set<String> documentosPermitidos;
    
    public MembresiaGratuita() {
        this.consultasRealizadas = 0;
        this.documentosGenerados = 0;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1); // Vence en 1 mes
        this.fechaVencimiento = cal.getTime();
        this.vigente = true;
        
        // Documentos permitidos para membresía gratuita
        this.documentosPermitidos = new HashSet<>();
        documentosPermitidos.add("ACTO_ADMINISTRATIVO");
        documentosPermitidos.add("SOLICITUD_SIMPLE");
    }
    
    @Override
    public boolean esVigente() {
        // RF-05: Verificar fecha de vencimiento
        Date hoy = new Date();
        this.vigente = hoy.before(fechaVencimiento);
        return vigente;
    }
    
    @Override
    public boolean puedeConsultar() {
        return esVigente() && consultasRealizadas < LIMITE_CONSULTAS;
    }
    
    @Override
    public boolean puedeGenerarDocumento(String tipoDocumento) {
        // RF-03: Verificar si el tipo de documento está permitido
        return esVigente() && 
               documentosGenerados < LIMITE_DOCUMENTOS && 
               documentosPermitidos.contains(tipoDocumento);
    }
    
    @Override
    public int getLimiteConsultas() {
        return LIMITE_CONSULTAS;
    }
    
    @Override
    public int getConsultasRealizadas() {
        return consultasRealizadas;
    }
    
    @Override
    public int getLimiteDocumentos() {
        return LIMITE_DOCUMENTOS;
    }
    
    @Override
    public int getDocumentosGenerados() {
        return documentosGenerados;
    }
    
    @Override
    public void registrarConsulta() {
        consultasRealizadas++;
    }
    
    @Override
    public void registrarDocumento() {
        documentosGenerados++;
    }
    
    @Override
    public String getTipo() {
        return "GRATUITA";
    }
    
    @Override
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    @Override
    public void setFechaVencimiento(Date fecha) {
        this.fechaVencimiento = fecha;
    }
    
    @Override
    public String getDescripcion() {
        return DESCRIPCION + " | Consultas: " + consultasRealizadas + "/" + LIMITE_CONSULTAS +
               " | Documentos: " + documentosGenerados + "/" + LIMITE_DOCUMENTOS;
    }
}