package modelo;

import java.util.Date;

public interface Membresia {
    // ABSTRACCIÓN: contrato que todas las membresías deben cumplir
    boolean esVigente(); // RF-05
    boolean puedeConsultar();
    boolean puedeGenerarDocumento(String tipoDocumento); // RF-03: límites por tipo de documento
    int getLimiteConsultas();
    int getConsultasRealizadas();
    int getLimiteDocumentos(); // RF-03: límite de documentos
    int getDocumentosGenerados();
    void registrarConsulta();
    void registrarDocumento(); // RF-03: contador de documentos
    String getTipo();
    Date getFechaVencimiento(); // RF-05
    void setFechaVencimiento(Date fecha); // RF-04
    String getDescripcion();
}