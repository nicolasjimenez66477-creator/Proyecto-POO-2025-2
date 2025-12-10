package modelo;

import java.util.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MembresiaInstitucional implements Membresia {
    private int consultasRealizadas = 0;
    private int documentosGenerados = 0;
    private static final int LIMITE_CONSULTAS = 50;
    private static final int LIMITE_DOCUMENTOS = 20;
    private static final String DESCRIPCION = "Para instituciones educativas";
    private Date fechaVencimiento;
    private boolean vigente;
    private int usuariosPermitidos;
    
    private Set<String> documentosPermitidos;
    
    public MembresiaInstitucional(int usuariosPermitidos) {
        this.usuariosPermitidos = usuariosPermitidos;
        this.consultasRealizadas = 0;
        this.documentosGenerados = 0;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1); // Vence en 1 año
        this.fechaVencimiento = cal.getTime();
        this.vigente = true;
        
        this.documentosPermitidos = new HashSet<>();
        documentosPermitidos.add("ACTO_ADMINISTRATIVO");
        documentosPermitidos.add("DERECHO_PETICION");
        documentosPermitidos.add("INFORME_DISCIPLINARIO");
        documentosPermitidos.add("CONTRATO_EDUCATIVO");
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
        return "INSTITUCIONAL";
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
    
    public int getUsuariosPermitidos() {
        return usuariosPermitidos;
    }
}