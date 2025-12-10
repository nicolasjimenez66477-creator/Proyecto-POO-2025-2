package gui;

import modelo.*;
import servicio.ServicioConsultas;
import servicio.ServicioDocumentos;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFrame extends JFrame {
    private Usuario usuarioLogueado;
    private ServicioConsultas servicioConsultas;
    private ServicioDocumentos servicioDocumentos;
    private JTextArea areaConsulta;
    private JTextArea areaRespuesta;
    
    public DashboardFrame(Usuario usuario) {
        this.usuarioLogueado = usuario;
        this.servicioConsultas = new ServicioConsultas();
        this.servicioDocumentos = new ServicioDocumentos();
        inicializarComponentes();
        configurarVentana();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema Juridico - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior con info del usuario
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        if (usuarioLogueado instanceof Rector) {
            tabbedPane.addTab("📝 Consultas Juridicas", crearPanelConsultas());
            tabbedPane.addTab("📋 Historial de Datos", crearPanelHistorial());
            tabbedPane.addTab("📄 Documentos Cargados", crearPanelDocumentos());
        } else if (usuarioLogueado instanceof Abogado) {
            tabbedPane.addTab("⚖️ Revisión", crearPanelRevision());
        } else if (usuarioLogueado instanceof Administrador) {
            tabbedPane.addTab("📊 Reportes", crearPanelReportes());
            tabbedPane.addTab("👥 Usuarios", crearPanelUsuarios());
        }
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Panel inferior
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblBienvenida = new JLabel("Bienvenido: " + usuarioLogueado.getNombre());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel lblTipo = new JLabel("Tipo: " + usuarioLogueado.getTipoUsuario());
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Info de membresía si es rector
        JLabel lblMembresia = new JLabel("");
        if (usuarioLogueado instanceof Rector) {
            Rector rector = (Rector) usuarioLogueado;
            if (rector.getMembresia() != null) {
                lblMembresia.setText("Membresía: " + rector.getMembresia().getTipo() + 
                                   " | Consultas: " + rector.getMembresia().getConsultasRealizadas() + 
                                   "/" + rector.getMembresia().getLimiteConsultas());
            } else {
                lblMembresia.setText("No tiene una membresia asignada");
            }
            lblMembresia.setFont(new Font("Arial", Font.ITALIC, 11));
            lblMembresia.setForeground(Color.BLUE);
        }
        
        panel.add(lblBienvenida, BorderLayout.WEST);
        
        JPanel panelDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelDerecha.add(lblTipo);
        panelDerecha.add(Box.createHorizontalStrut(10));
        panelDerecha.add(lblMembresia);
        panel.add(panelDerecha, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(btnSalir);
        return panel;
    }
    
    private JPanel crearPanelConsultas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Área para escribir consulta
        JLabel lblConsulta = new JLabel("Escribe tu consulta :");
        lblConsulta.setFont(new Font("Arial", Font.BOLD, 14));
        areaConsulta = new JTextArea(5, 50);
        areaConsulta.setLineWrap(true);
        areaConsulta.setWrapStyleWord(true);
        areaConsulta.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollConsulta = new JScrollPane(areaConsulta);
        
        // Botón para consultar
        JButton btnConsultar = new JButton("Consultar con IA");
        btnConsultar.setFont(new Font("Arial", Font.BOLD, 12));
        btnConsultar.setBackground(new Color(70, 130, 180));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarConsulta();
            }
        });
        
        // Área para mostrar respuesta
        JLabel lblRespuesta = new JLabel("Respuesta:");
        lblRespuesta.setFont(new Font("Arial", Font.BOLD, 14));
        areaRespuesta = new JTextArea(10, 50);
        areaRespuesta.setLineWrap(true);
        areaRespuesta.setWrapStyleWord(true);
        areaRespuesta.setEditable(false);
        areaRespuesta.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollRespuesta = new JScrollPane(areaRespuesta);
        
        // Organizar componentes
        JPanel panelConsulta = new JPanel(new BorderLayout());
        panelConsulta.add(lblConsulta, BorderLayout.NORTH);
        panelConsulta.add(scrollConsulta, BorderLayout.CENTER);
        
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnConsultar);
        
        JPanel panelRespuesta = new JPanel(new BorderLayout());
        panelRespuesta.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelRespuesta.add(lblRespuesta, BorderLayout.NORTH);
        panelRespuesta.add(scrollRespuesta, BorderLayout.CENTER);
        
        panel.add(panelConsulta, BorderLayout.NORTH);
        panel.add(panelBoton, BorderLayout.CENTER);
        panel.add(panelRespuesta, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void realizarConsulta() {
        try {
            String pregunta = areaConsulta.getText().trim();
            
            if (pregunta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor escribe una consulta", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Consulta consulta = servicioConsultas.crearConsulta(pregunta, usuarioLogueado);
            areaRespuesta.setText(consulta.getRespuesta());
            
            if (usuarioLogueado instanceof Rector) {
                Rector rector = (Rector) usuarioLogueado;
                JOptionPane.showMessageDialog(this, 
                    "✅ Consulta realizada exitosamente\n\n" +
                    "Consultas utilizadas: " + rector.getMembresia().getConsultasRealizadas() + 
                    " de " + rector.getMembresia().getLimiteConsultas() + "\n" +
                    "Documentos disponibles: " + rector.getMembresia().getDocumentosGenerados() + 
                    " de " + rector.getMembresia().getLimiteDocumentos(),
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
                
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaConsultas = new JList<>(modeloLista);
        listaConsultas.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Cargar consultas del usuario
        for (Consulta consulta : servicioConsultas.getConsultasPorUsuario(usuarioLogueado)) {
            modeloLista.addElement(consulta.toString());
        }
        
        JScrollPane scrollPane = new JScrollPane(listaConsultas);
        panel.add(new JLabel("Historial de consultas:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelDocumentos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea areaInfo = new JTextArea(
            "Funcionalidad de documentos en desarrollo:\n\n" +
            "Próximamente podrás:\n" +
            "• Generar actos administrativos\n" +
            "• Crear derechos de petición\n" +
            "• Elaborar informes disciplinarios\n" +
            "• Exportar a PDF y DOCX\n" +
            "• Sistema de versionado\n\n" +
            "Esta funcionalidad estará disponible en la próxima actualización."
        );
        areaInfo.setEditable(false);
        areaInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        areaInfo.setLineWrap(true);
        areaInfo.setWrapStyleWord(true);
        
        panel.add(new JScrollPane(areaInfo), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel crearPanelRevision() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lbl = new JLabel("Panel de revisión para abogados (en desarrollo)", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lbl, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lbl = new JLabel("Panel de reportes para administradores (en desarrollo)", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lbl, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lbl = new JLabel("Gestión de usuarios (en desarrollo)", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lbl, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void configurarVentana() {
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);
    }
}