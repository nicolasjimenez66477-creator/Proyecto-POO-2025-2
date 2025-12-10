package modelo;

public class DerechoPeticion extends Documento {
    private String numeroRespuesta;
    private String institucion;
    private String solicitante;
    private String fechaSolicitud;
    private String asunto;
    private String respuestaInstitucional;
    
    public DerechoPeticion(String titulo, Usuario creador, String numeroRespuesta,
                          String institucion, String solicitante, String fechaSolicitud,
                          String asunto, String respuestaInstitucional) {
        super(titulo, creador, "DERECHO_PETICION");
        this.numeroRespuesta = numeroRespuesta;
        this.institucion = institucion;
        this.solicitante = solicitante;
        this.fechaSolicitud = fechaSolicitud;
        this.asunto = asunto;
        this.respuestaInstitucional = respuestaInstitucional;
        generarContenidoAutomatico();
    }
    
    @Override
    public void generarContenidoAutomatico() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("RESPUESTA A DERECHO DE PETICIÓN\n");
        contenido.append("================================\n\n");
        contenido.append("Número: ").append(numeroRespuesta).append("\n");
        contenido.append("Institución: ").append(institucion).append("\n");
        contenido.append("Solicitante: ").append(solicitante).append("\n");
        contenido.append("Fecha de Solicitud: ").append(fechaSolicitud).append("\n\n");
        contenido.append("ASUNTO:\n").append(asunto).append("\n\n");
        contenido.append("RESPUESTA INSTITUCIONAL:\n");
        contenido.append(respuestaInstitucional).append("\n\n");
        contenido.append("En cumplimiento de lo establecido en la Ley 1755 de 2015,\n");
        contenido.append("se responde al derecho de petición dentro de los términos legales.\n\n");
        contenido.append("Fecha: ").append(new java.util.Date()).append("\n");
        contenido.append("Firma: _________________________\n");
        contenido.append("Sello: _________________________\n");
        
        setContenido(contenido.toString());
    }
    
    public String getNumeroRespuesta() { return numeroRespuesta; }
    public String getInstitucion() { return institucion; }
    public String getSolicitante() { return solicitante; }
    public String getFechaSolicitud() { return fechaSolicitud; }
    public String getAsunto() { return asunto; }
    public String getRespuestaInstitucional() { return respuestaInstitucional; }
}