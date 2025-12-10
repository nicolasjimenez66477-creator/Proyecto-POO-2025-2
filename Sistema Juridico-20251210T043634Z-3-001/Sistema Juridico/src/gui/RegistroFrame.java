package gui;

import modelo.*;
import servicio.ServicioUsuarios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroFrame extends JFrame {
    private JTextField txtNombre, txtEmail, txtInstitucion, txtSecretaria;
    private JPasswordField txtContrasena, txtConfirmarContrasena;
    private JComboBox<String> comboTipoUsuario;
    private JButton btnRegistrar, btnVolver;
    private ServicioUsuarios servicioUsuarios;
    
    public RegistroFrame() {
        servicioUsuarios = new ServicioUsuarios();
        inicializarComponentes();
        configurarVentana();
    }
    
    private void inicializarComponentes() {
        setTitle("Registro - Sistema Juridico");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("REGISTRO DE USUARIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, gbc);
        
        // Campos del formulario
        String[] campos = {"Nombre:", "Email:", "Contraseña:", "Confirmar:", 
                          "Institución:", "Secretaría Educación:", "Tipo Usuario:"};
        
        for (int i = 0; i < campos.length; i++) {
            gbc.gridwidth = 1;
            gbc.gridy = i + 1;
            gbc.gridx = 0;
            panelPrincipal.add(new JLabel(campos[i]), gbc);
            
            gbc.gridx = 1;
            if (i == 0) { // Nombre
                txtNombre = new JTextField(20);
                panelPrincipal.add(txtNombre, gbc);
            } else if (i == 1) { // Email
                txtEmail = new JTextField(20);
                panelPrincipal.add(txtEmail, gbc);
            } else if (i == 2) { // Contraseña
                txtContrasena = new JPasswordField(20);
                panelPrincipal.add(txtContrasena, gbc);
            } else if (i == 3) { // Confirmar contraseña
                txtConfirmarContrasena = new JPasswordField(20);
                panelPrincipal.add(txtConfirmarContrasena, gbc);
            } else if (i == 4) { // Institución
                txtInstitucion = new JTextField(20);
                panelPrincipal.add(txtInstitucion, gbc);
            } else if (i == 5) { // Secretaría
                txtSecretaria = new JTextField(20);
                panelPrincipal.add(txtSecretaria, gbc);
            } else if (i == 6) { // Tipo Usuario
                String[] tipos = {"RECTOR", "ABOGADO", "ADMINISTRADOR"};
                comboTipoUsuario = new JComboBox<>(tipos);
                panelPrincipal.add(comboTipoUsuario, gbc);
            }
        }
        
        // Botones
        gbc.gridy = campos.length + 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverALogin();
            }
        });
        panelPrincipal.add(btnVolver, gbc);
        
        gbc.gridx = 1;
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        panelPrincipal.add(btnRegistrar, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private void configurarVentana() {
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void registrarUsuario() {
        try {
            // Validaciones
            if (txtNombre.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                new String(txtContrasena.getPassword()).isEmpty()) {
                
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!new String(txtContrasena.getPassword()).equals(
                new String(txtConfirmarContrasena.getPassword()))) {
                
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // RF-01: Validar correo institucional
            if (!Usuario.esCorreoInstitucionalValido(txtEmail.getText().trim())) {
                JOptionPane.showMessageDialog(this, 
                    "El correo debe ser institucional (ejemplo: usuario@institucion.edu.co)", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Registrar usuario
            String tipoUsuario = (String) comboTipoUsuario.getSelectedItem();
            Usuario nuevoUsuario = servicioUsuarios.registrarUsuario(
                tipoUsuario,
                txtNombre.getText().trim(),
                txtEmail.getText().trim(),
                new String(txtContrasena.getPassword()),
                txtInstitucion.getText().trim(),
                txtSecretaria.getText().trim()
            );
            
            JOptionPane.showMessageDialog(this, 
                "✅ Usuario registrado exitosamente!\n\n" +
                "Tipo: " + nuevoUsuario.getTipoUsuario() + "\n" +
                "Nombre: " + nuevoUsuario.getNombre() + "\n" +
                "Institución: " + nuevoUsuario.getInstitucion() + "\n" +
                "Secretaría: " + nuevoUsuario.getSecretariaEducacion(),
                "Registro Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            volverALogin();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                "Error en Registro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void volverALogin() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }
}