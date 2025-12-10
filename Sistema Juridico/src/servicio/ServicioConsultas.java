package servicio;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioConsultas {
    private List<Consulta> consultas = new ArrayList<>();
    
    public Consulta crearConsulta(String pregunta, Usuario usuario) {
        if (!(usuario instanceof Rector)) {
            throw new IllegalArgumentException("Solo rectores pueden crear consultas");
        }
        
        Rector rector = (Rector) usuario;
        if (!rector.puedeConsultar()) {
            throw new IllegalStateException("Límite de consultas excedido o membresía no vigente");
        }
        
        Consulta consulta = new Consulta(pregunta, usuario);
        consultas.add(consulta);
        
        // Registrar consulta en la membresía
        if (rector.getMembresia() != null) {
            rector.getMembresia().registrarConsulta();
        }
        
        // Simular respuesta de IA
        String respuesta = generarRespuestaIASimple(pregunta);
        consulta.setRespuesta(respuesta);
        
        return consulta;
    }
    
    private String generarRespuestaIASimple(String pregunta) {
        return "RESPUESTA GENERADA POR IA:\n\n" +
               "Para la consulta: " + pregunta + "\n\n" +
               "Según la Ley 115 de 1994, artículo 95...\n" +
               "Se recomienda seguir los siguientes pasos:\n" +
               "1. Revisar el manual de convivencia\n" +
               "2. Documentar el proceso\n" +
               "3. Notificar a las partes interesadas\n\n" +
               "ADVERTENCIA: Esta respuesta no constituye asesoría jurídica personalizada.";
    }
    
    public List<Consulta> getConsultasPorUsuario(Usuario usuario) {
        List<Consulta> resultado = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.getUsuario().equals(usuario)) {
                resultado.add(consulta);
            }
        }
        return resultado;
    }
    
    public List<Consulta> getTodasConsultas() {
        return new ArrayList<>(consultas);
    }
}