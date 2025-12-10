spackage servicio;

import java.io.*;
import java.util.*;
import modelo.*;

public class ServicioDocumentos {
    private List<Documento> documentos;
    private List<PlantillaDocumento> plantillas;
    
    public ServicioDocumentos() {
        this.documentos = new ArrayList<>();
        this.plantillas = new ArrayList<>();
        inicializarPlantillas();
    }
    
    private void inicializarPlantillas() {
        // RF-20: Catálogo de plantillas jurídicas educativas
        
        // Plantilla 1: Acto Administrativo
        List<CampoFormulario> camposActo = new ArrayList<>();
        camposActo.add(new CampoFormulario("numero", "Número del Acto", "text", true));
        camposActo.add(new CampoFormulario("institucion", "Institución Educativa", "text", true));
        camposActo.add(new CampoFormulario("estudiante", "Nombre del Estudiante", "text", true));
        camposActo.add(new CampoFormulario("grado", "Grado", "text", true));
        camposActo.add(new CampoFormulario("fecha", "Fecha de Matrícula", "date", true));
        camposActo.add(new CampoFormulario("representante", "Nombre del Representante", "text", true));
        
        plantillas.add(new PlantillaDocumento(
            "Acto Administrativo de Matrícula",
            "Documento oficial para registrar la matrícula de un estudiante",
            "ACTO_ADMINISTRATIVO",
            camposActo
        ));
        
        // Plantilla 2: Derecho de Petición
        List<CampoFormulario> camposPeticion = new ArrayList<>();
        camposPeticion.add(new CampoFormulario("numero", "Número de Respuesta", "text", true));
        camposPeticion.add(new CampoFormulario("institucion", "Institución", "text", true));
        camposPeticion.add(new CampoFormulario("solicitante", "Nombre del Solicitante", "text", true));
        camposPeticion.add(new CampoFormulario("fecha_solicitud", "Fecha de la Solicitud", "date", true));
        camposPeticion.add(new CampoFormulario("asunto", "Asunto de la Petición", "textarea", true));
        camposPeticion.add(new CampoFormulario("respuesta", "Respuesta Institucional", "textarea", true));
        
        plantillas.add(new PlantillaDocumento(
            "Respuesta a Derecho de Petición",
            "Documento para responder formalmente a un derecho de petición",
            "DERECHO_PETICION",
            camposPeticion
        ));
        
        // Plantilla 3: Informe Disciplinario
        List<CampoFormulario> camposInforme = new ArrayList<>();
        camposInforme.add(new CampoFormulario("numero", "Número de Informe", "text", true));
        camposInforme.add(new CampoFormulario("estudiante", "Nombre del Estudiante", "text", true));
        camposInforme.add(new CampoFormulario("grado", "Grado", "text", true));
        camposInforme.add(new CampoFormulario("fecha_incidente", "Fecha del Incidente", "date", true));
        camposInforme.add(new CampoFormulario("descripcion", "Descripción del Incidente", "textarea", true));
        camposInforme.add(new CampoFormulario("medidas", "Medidas Adoptadas", "textarea", true));
        camposInforme.add(new CampoFormulario("testigos", "Nombres de Testigos", "text", false));
        
        plantillas.add(new PlantillaDocumento(
            "Informe Disciplinario de Estudiante",
            "Documento para registrar un proceso disciplinario",
            "INFORME_DISCIPLINARIO",
            camposInforme
        ));
    }
    
    // RF-21: Generar documento a partir de plantilla
    public Documento generarDocumento(String tipoPlantilla, Map<String, String> datos, 
                                      Usuario creador, Consulta consultaRelacionada) {
        
        PlantillaDocumento plantilla = buscarPlantillaPorTipo(tipoPlantilla);
        if (plantilla == null) {
            throw new IllegalArgumentException("Plantilla no encontrada: " + tipoPlantilla);
        }
        
        // RF-21: Completar formulario estructurado
        validarDatosFormulario(datos, plantilla);
        
        // Crear documento según el tipo
        Documento documento;
        switch (tipoPlantilla) {
            case "ACTO_ADMINISTRATIVO":
                documento = new ActoAdministrativo(
                    "Acto Administrativo " + datos.get("numero"),
                    creador,
                    datos.get("numero"),
                    datos.get("institucion"),
                    datos.get("estudiante"),
                    datos.get("grado"),
                    datos.get("fecha"),
                    datos.get("representante")
                );
                break;
                
            case "DERECHO_PETICION":
                documento = new DerechoPeticion(
                    "Respuesta a Derecho de Petición " + datos.get("numero"),
                    creador,
                    datos.get("numero"),
                    datos.get("institucion"),
                    datos.get("solicitante"),
                    datos.get("fecha_solicitud"),
                    datos.get("asunto"),
                    datos.get("respuesta")
                );
                break;
                
            case "INFORME_DISCIPLINARIO":
                documento = new InformeDisciplinario(
                    "Informe Disciplinario " + datos.get("numero"),
                    creador,
                    datos.get("numero"),
                    datos.get("estudiante"),
                    datos.get("grado"),
                    datos.get("fecha_incidente"),
                    datos.get("descripcion"),
                    datos.get("medidas"),
                    datos.get("testigos")
                );
                break;
                
            default:
                throw new IllegalArgumentException("Tipo de documento no soportado: " + tipoPlantilla);
        }
        
        // RF-24: Vincular con consulta jurídica original
        documento.setConsultaRelacionada(consultaRelacionada);
        
        documentos.add(documento);
        return documento;
    }
    
    private PlantillaDocumento buscarPlantillaPorTipo(String tipo) {
        for (PlantillaDocumento plantilla : plantillas) {
            if (plantilla.getTipoDocumento().equals(tipo)) {
                return plantilla;
            }
        }
        return null;
    }
    
    private void validarDatosFormulario(Map<String, String> datos, PlantillaDocumento plantilla) {
        for (CampoFormulario campo : plantilla.getCampos()) {
            if (campo.isObligatorio()) {
                String valor = datos.get(campo.getId());
                if (valor == null || valor.trim().isEmpty()) {
                    throw new IllegalArgumentException("Falta el campo obligatorio: " + campo.getEtiqueta());
                }
            }
        }
    }
    
    // RF-23: Exportar documento a formato específico
    public void exportarDocumento(Documento documento, String formato, String rutaArchivo) throws IOException {
        String contenido = documento.getContenido();
        
        switch (formato.toUpperCase()) {
            case "TXT":
                exportarTXT(contenido, rutaArchivo);
                break;
            case "PDF":
                exportarPDFSimulado(contenido, rutaArchivo);
                break;
            case "DOCX":
                exportarDOCXSimulado(contenido, rutaArchivo);
                break;
            default:
                throw new IllegalArgumentException("Formato no soportado: " + formato);
        }
    }
    
    private void exportarTXT(String contenido, String rutaArchivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
            writer.write(contenido);
        }
    }
    
    private void exportarPDFSimulado(String contenido, String rutaArchivo) throws IOException {
        // Simulación de exportación a PDF
        String contenidoConFormato = "=== DOCUMENTO PDF ===\n" + contenido + "\n=== FIN DEL DOCUMENTO ===";
        String rutaSimulada = rutaArchivo.replace(".pdf", "_simulado.txt");
        if (!rutaArchivo.endsWith(".pdf")) {
            rutaSimulada = rutaArchivo + "_pdf_simulado.txt";
        }
        exportarTXT(contenidoConFormato, rutaSimulada);
        System.out.println("Documento PDF simulado guardado en: " + rutaSimulada);
    }
    
    private void exportarDOCXSimulado(String contenido, String rutaArchivo) throws IOException {
        // Simulación de exportación a DOCX
        String contenidoConFormato = "=== DOCUMENTO DOCX ===\n" + contenido + "\n=== FIN DEL DOCUMENTO ===";
        String rutaSimulada = rutaArchivo.replace(".docx", "_simulado.txt");
        if (!rutaArchivo.endsWith(".docx")) {
            rutaSimulada = rutaArchivo + "_docx_simulado.txt";
        }
        exportarTXT(contenidoConFormato, rutaSimulada);
        System.out.println("Documento DOCX simulado guardado en: " + rutaSimulada);
    }
    
    // RF-25: Obtener historial de versiones
    public List<VersionDocumento> obtenerHistorialVersiones(String documentoId) {
        for (Documento doc : documentos) {
            if (doc.getId().equals(documentoId)) {
                return doc.getVersiones();
            }
        }
        return new ArrayList<>();
    }
    
    // Métodos para obtener plantillas y documentos
    public List<PlantillaDocumento> getPlantillas() {
        return new ArrayList<>(plantillas);
    }
    
    public List<Documento> getDocumentosPorUsuario(Usuario usuario) {
        List<Documento> resultado = new ArrayList<>();
        for (Documento doc : documentos) {
            if (doc.getCreador().equals(usuario)) {
                resultado.add(doc);
            }
        }
        return resultado;
    }
    
    public List<Documento> getTodosDocumentos() {
        return new ArrayList<>(documentos);
    }
    
    // Método para buscar documento por ID
    public Documento buscarDocumentoPorId(String id) {
        for (Documento doc : documentos) {
            if (doc.getId().equals(id)) {
                return doc;
            }
        }
        return null;
    }
}