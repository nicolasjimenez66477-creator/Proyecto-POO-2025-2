package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import modelo.*;
import servicio.ServicioUsuarios;

public class LoginFrame extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private JButton btnRegistro;
    private ServicioUsuarios servicioUsuarios;
    
    public LoginFrame() {
        servicioUsuarios = new ServicioUsuarios();
        inicializarComponentes();
        configurarVentana();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema Juridico - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal con padding
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("SISTEMA JURIDICO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, gbc);
        
        // Separador
        gbc.gridy = 1;
        panelPrincipal.add(new JSeparator(), gbc);
        
        // Email
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panelPrincipal.add(txtEmail, gbc);
        
        // Contraseña
        gbc.gridy = 3;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        txtContrasena = new JPasswordField(20);
        panelPrincipal.add(txtContrasena, gbc);
        
        // Botón Login
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setPreferredSize(new Dimension(150, 30));
        panelPrincipal.add(btnLogin, gbc);
        
        // Botón Registro
        gbc.gridy = 5;
        btnRegistro = new JButton("Registrarse");
        btnRegistro.setPreferredSize(new Dimension(150, 30));
        panelPrincipal.add(btnRegistro, gbc);
        
        // Usuarios de prueba
        gbc.gridy = 6;
        JLabel lblDemo = new JLabel("Demo: gerardo@colegio.edu.co / contra0597", SwingConstants.CENTER);
        lblDemo.setFont(new Font("Arial", Font.ITALIC, 10));
        panelPrincipal.add(lblDemo, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Configurar acción del botón
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
        
        // Permitir login con Enter
        txtContrasena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
    }
    
    private void configurarVentana() {
        pack();
        setLocationRelativeTo(null); // Centrar ventana
        setResizable(false);
    }
    
    private void realizarLogin() {
        String email = txtEmail.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        // Usar servicio para autenticar
        Usuario usuario = servicioUsuarios.autenticarUsuario(email, contrasena);
        
        if (usuario != null) {
            // RF-05: Verificar membresía vigente para rectores
            if (usuario instanceof Rector) {
                Rector rector = (Rector) usuario;
                if (rector.getMembresia() == null || !rector.getMembresia().esVigente()) {
                    JOptionPane.showMessageDialog(this, 
                        "Su membresía no está vigente. Por favor, renueve su suscripción.",
                        "Membresía Expirada", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            // Login exitoso
            abrirDashboard(usuario);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Cuenta no existente.\nSi no tienes cuenta regístrate.", 
                "Error de Login", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirDashboard(Usuario usuario) {
        DashboardFrame dashboard = new DashboardFrame(usuario);
        dashboard.setVisible(true);
        this.dispose(); // Cerrar ventana de login
    }
    
    private void abrirRegistro() {
        RegistroFrame registroFrame = new RegistroFrame();
        registroFrame.setVisible(true);
        this.dispose();
    }
}