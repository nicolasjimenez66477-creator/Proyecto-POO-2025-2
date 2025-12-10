package modelo;

import java.util.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MembresiaPremium implements Membresia {
    private int consultasRealizadas = 0;
    private int documentosGenerados = 0;
    private static final int LIMITE_CONSULTAS = 100;
    private static final int LIMITE_DOCUMENTOS = 50;
    private static final String DESCRIPCION = "Acceso completo con revisión humana incluida";
    private Date fechaVencimiento;
    private boolean vigente;
    private boolean incluyeRevisionHumana = true;
    
    // Todos los documentos permitidos (RF-03)
    private Set<String> documentosPermitidos;
    
    public MembresiaPremium() {
        this.consultasRealizadas = 0;
        this.documentosGenerados = 0;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1); // Vence en 1 año
        this.fechaVencimiento = cal.getTime();
        this.vigente = true;
        
        // Todos los documentos permitidos para premium
        this.documentosPermitidos = new HashSet<>();
        documentosPermitidos.add("ACTO_ADMINISTRATIVO");
        documentosPermitidos.add("DERECHO_PETICION");
        documentosPermitidos.add("INFORME_DISCIPLINARIO");
        documentosPermitidos.add("CONTRATO_EDUCATIVO");
        documentosPermitidos.add("INFORME_LEGAL");
    }
    
    @Override
    public boolean esVigente() {
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
        return "PREMIUM";
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
    
    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }
    
    public boolean incluyeRevisionHumana() {
        return incluyeRevisionHumana;
    }
}