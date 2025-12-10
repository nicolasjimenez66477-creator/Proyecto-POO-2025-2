package modelo;

public class ActoAdministrativo extends Documento {
    private String numeroActo;
    private String institucionEmisora;
    private String destinatario;
    private String grado;
    private String fechaMatricula;
    private String representante;
    
    public ActoAdministrativo(String titulo, Usuario creador, String numeroActo,
                             String institucionEmisora, String destinatario, 
                             String grado, String fechaMatricula, String representante) {
        super(titulo, creador, "ACTO_ADMINISTRATIVO");
        this.numeroActo = numeroActo;
        this.institucionEmisora = institucionEmisora;
        this.destinatario = destinatario;
        this.grado = grado;
        this.fechaMatricula = fechaMatricula;
        this.representante = representante;
        generarContenidoAutomatico();
    }
    
    @Override
    public void generarContenidoAutomatico() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("ACTO ADMINISTRATIVO DE MATRÍCULA\n");
        contenido.append("===============================\n\n");
        contenido.append("Número: ").append(numeroActo).append("\n");
        contenido.append("Institución: ").append(institucionEmisora).append("\n");
        contenido.append("ESTUDIANTE: ").append(destinatario).append("\n");
        contenido.append("GRADO: ").append(grado).append("\n");
        contenido.append("FECHA DE MATRÍCULA: ").append(fechaMatricula).append("\n");
        contenido.append("REPRESENTANTE LEGAL: ").append(representante).append("\n\n");
        contenido.append("CONSIDERANDO:\n");
        contenido.append("1. Que el estudiante ha cumplido con los requisitos establecidos.\n");
        contenido.append("2. Que se han verificado los documentos correspondientes.\n");
        contenido.append("3. Que existe cupo disponible en el grado solicitado.\n\n");
        contenido.append("RESUELVE:\n");
        contenido.append("Artículo 1. Matricular al estudiante ").append(destinatario);
        contenido.append(" en el grado ").append(grado).append(".\n");
        contenido.append("Artículo 2. Esta matrícula se efectúa bajo las normas del Manual de Convivencia.\n");
        contenido.append("Artículo 3. Notificar al representante legal para la firma del contrato.\n\n");
        contenido.append("Fecha: ").append(new java.util.Date()).append("\n");
        contenido.append("EL RECTOR\n\n");
        contenido.append("Firma: _________________________\n");
        contenido.append("Sello Institucional\n");
        
        setContenido(contenido.toString());
    }
    
    public String getNumeroActo() { return numeroActo; }
    public String getInstitucionEmisora() { return institucionEmisora; }
    public String getDestinatario() { return destinatario; }
    public String getGrado() { return grado; }
    public String getFechaMatricula() { return fechaMatricula; }
    public String getRepresentante() { return representante; }
}