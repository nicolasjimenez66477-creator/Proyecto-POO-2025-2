package servicio;

import java.util.ArrayList;
import java.util.List;
import modelo.*;

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
        return "Aquí tienes la respuesta generada por la IA:\n\n" +
               "Sobre tu pregunta: " + pregunta + "\n\n" +
               "Según lo indicado en la Ley 115 de 1994, artículo 95…\n" +
               "Te sugiero tener en cuenta estos pasos:\n" +
               "1. Dale una mirada al manual de convivencia\n" +
               "2. Deja registro del proceso\n" +
               "3. Informa a las personas involucrada\n\n" +
               "Nota: Esta respuesta es solo orientativa y no reemplaza una asesoría jurídica profesional.";
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