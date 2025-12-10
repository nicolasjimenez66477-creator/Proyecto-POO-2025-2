package modelo;

public class InformeDisciplinario extends Documento {
    private String numeroInforme;
    private String estudiante;
    private String grado;
    private String fechaIncidente;
    private String descripcionIncidente;
    private String medidasAdoptadas;
    private String testigos;
    
    public InformeDisciplinario(String titulo, Usuario creador, String numeroInforme,
                               String estudiante, String grado, String fechaIncidente,
                               String descripcionIncidente, String medidasAdoptadas, 
                               String testigos) {
        super(titulo, creador, "INFORME_DISCIPLINARIO");
        this.numeroInforme = numeroInforme;
        this.estudiante = estudiante;
        this.grado = grado;
        this.fechaIncidente = fechaIncidente;
        this.descripcionIncidente = descripcionIncidente;
        this.medidasAdoptadas = medidasAdoptadas;
        this.testigos = testigos;
        generarContenidoAutomatico();
    }
    
    @Override
    public void generarContenidoAutomatico() {
        StringBuilder contenido = new StringBuilder();
        contenido.append("INFORME DISCIPLINARIO\n");
        contenido.append("=====================\n\n");
        contenido.append("Número: ").append(numeroInforme).append("\n");
        contenido.append("Estudiante: ").append(estudiante).append("\n");
        contenido.append("Grado: ").append(grado).append("\n");
        contenido.append("Fecha del Incidente: ").append(fechaIncidente).append("\n\n");
        contenido.append("DESCRIPCIÓN DEL INCIDENTE:\n");
        contenido.append(descripcionIncidente).append("\n\n");
        contenido.append("MEDIDAS ADOPTADAS:\n");
        contenido.append(medidasAdoptadas).append("\n\n");
        
        if (testigos != null && !testigos.isEmpty()) {
            contenido.append("TESTIGOS:\n").append(testigos).append("\n\n");
        }
        
        contenido.append("FUNDAMENTO LEGAL:\n");
        contenido.append("De acuerdo con el Manual de Convivencia Institucional y la Ley 115 de 1994,\n");
        contenido.append("Artículo 87, sobre el proceso disciplinario estudiantil.\n\n");
        contenido.append("Fecha: ").append(new java.util.Date()).append("\n");
        contenido.append("Elaborado por: ").append(getCreador().getNombre()).append("\n");
        contenido.append("Cargo: Rector\n");
        contenido.append("Firma: _________________________\n");
        
        setContenido(contenido.toString());
    }
    
    public String getNumeroInforme() { return numeroInforme; }
    public String getEstudiante() { return estudiante; }
    public String getGrado() { return grado; }
    public String getFechaIncidente() { return fechaIncidente; }
    public String getDescripcionIncidente() { return descripcionIncidente; }
    public String getMedidasAdoptadas() { return medidasAdoptadas; }
    public String getTestigos() { return testigos; }
}