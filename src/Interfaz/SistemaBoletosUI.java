package Interfaz;

import Aerolina.*;
import Cuenta.*;
import Viaje.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class SistemaBoletosUI extends JFrame {
    // --- Nueva Paleta de Colores (Tema Claro y Profesional) ---
    private final Color COLOR_AZUL_PRINCIPAL = new Color(13, 110, 253);
    private final Color COLOR_VERDE_EXITO = new Color(25, 135, 84);
    private final Color COLOR_ROJO_PELIGRO = new Color(220, 53, 69);
    private final Color COLOR_GRIS_INFO = new Color(108, 117, 125);
    
    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_PANEL = Color.WHITE;
    private final Color COLOR_TEXTO_PRINCIPAL = new Color(33, 37, 41);
    private final Color COLOR_BORDE = new Color(222, 226, 230);
    
    private JTabbedPane tabbedPane;
    private JTable vuelosTable;
    private JTable boletosTable;
    private JTextField nombreField, apellidoField, nacionalidadField;
    private JSpinner fechaNacimientoSpinner;
    private JComboBox<String> claseComboBox;
    private JLabel totalLabel;
    private JTextField codigoReservaField;

   
    private Map<Integer, Pais> paisesMap = new HashMap<>();
    private Map<Integer, Departamento> departamentosMap = new HashMap<>();
    private Map<Integer, Vuelo> vuelosMap = new HashMap<>();
    private Map<Integer, Asiento> asientosMap = new HashMap<>();
    private Map<Integer, Boleto> boletosMap = new HashMap<>();
    private Map<Integer, Pasajero> pasajerosMap = new HashMap<>();

    private int nextBoletoId = 1;
    private int nextPasajeroId = 1;

    public SistemaBoletosUI() {
        initializeData();
        initializeUI();
        loadTablesData();
    }

    private void initializeData() {
      paisesMap = DataLector.getPaises(); // O cargarPaises()
      departamentosMap = DataLector.getDepartamentos(paisesMap);
      vuelosMap = DataLector.getVuelos(departamentosMap);  
      asientosMap = DataLector.getAsientos(vuelosMap);
        cargarBoletos();
        cargarPasajeros();
    }

    private void initializeUI() {
        setTitle("Sistema de Gestión de Boletos de Avión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(COLOR_FONDO);
        tabbedPane.setForeground(COLOR_TEXTO_PRINCIPAL);

        tabbedPane.addTab("Comprar Boleto", createCompraPanel());
        

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createCompraPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel superior con tabla de vuelos
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(COLOR_PANEL);
        topPanel.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        
        vuelosTable = new JTable();
        styleTable(vuelosTable);
        JScrollPane scrollPane = new JScrollPane(vuelosTable);
        scrollPane.setBorder(null); 
        topPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Título del panel de vuelos
        JLabel vuelosTitle = new JLabel("Vuelos Disponibles");
        vuelosTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        vuelosTitle.setForeground(COLOR_TEXTO_PRINCIPAL);
        vuelosTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(vuelosTitle, BorderLayout.NORTH);

        // Panel central con datos del pasajero
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(COLOR_PANEL);
        TitledBorder titledBorderDatos = BorderFactory.createTitledBorder("Datos del Pasajero");
        titledBorderDatos.setTitleColor(COLOR_TEXTO_PRINCIPAL);
        titledBorderDatos.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                titledBorderDatos
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Campos de formulario ---
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(new JLabel("Nombre:"), gbc);
        nombreField = new JTextField(20);
        styleTextField(nombreField);
        gbc.gridx = 1;
        centerPanel.add(nombreField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        centerPanel.add(new JLabel("Apellido:"), gbc);
        apellidoField = new JTextField(20);
        styleTextField(apellidoField);
        gbc.gridx = 1;
        centerPanel.add(apellidoField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        centerPanel.add(new JLabel("Nacionalidad:"), gbc);
        nacionalidadField = new JTextField(20);
        styleTextField(nacionalidadField);
        gbc.gridx = 1;
        centerPanel.add(nacionalidadField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        centerPanel.add(new JLabel("Fecha Nacimiento:"), gbc);
        fechaNacimientoSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(fechaNacimientoSpinner, "yyyy-MM-dd");
        fechaNacimientoSpinner.setEditor(editor);
        gbc.gridx = 1;
        centerPanel.add(fechaNacimientoSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        centerPanel.add(new JLabel("Clase:"), gbc);
        claseComboBox = new JComboBox<>(new String[]{"Economica", "Negocios", "Primera"});
        gbc.gridx = 1;
        centerPanel.add(claseComboBox, gbc);

        // Panel inferior con total y botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(COLOR_FONDO);
        
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setForeground(COLOR_TEXTO_PRINCIPAL);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        bottomPanel.add(totalLabel);

        JButton calcularButton = new JButton("Calcular Precio");
        styleButton(calcularButton, COLOR_GRIS_INFO, Color.WHITE);
        calcularButton.addActionListener(e -> calcularPrecio());
        bottomPanel.add(calcularButton);

        JButton comprarButton = new JButton("Comprar Boleto");
        styleButton(comprarButton, COLOR_VERDE_EXITO, Color.WHITE);
        comprarButton.addActionListener(e -> comprarBoleto());
        bottomPanel.add(comprarButton);
        
             JButton regresarButton= new JButton("Regresar");
        styleButton(regresarButton, COLOR_ROJO_PELIGRO, Color.WHITE);
        regresarButton.addActionListener(e ->{
    Menu a = new Menu();
    a.setVisible(true);

    Window window = SwingUtilities.getWindowAncestor(panel);
    if (window != null) {
        window.dispose();
    }});
        bottomPanel.add(regresarButton);

        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.WEST);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

  

    // --- Métodos de estilo reutilizables ---
    private void styleButton(JButton button, Color backgroundColor, Color foregroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Color hoverColor = backgroundColor.brighter();
        Color pressColor = backgroundColor.darker();

        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { button.setBackground(hoverColor); }
            @Override public void mouseExited(MouseEvent e) { button.setBackground(backgroundColor); }
            @Override public void mousePressed(MouseEvent e) { button.setBackground(pressColor); }
            @Override public void mouseReleased(MouseEvent e) { button.setBackground(hoverColor); }
        });
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBackground(Color.WHITE);
        textField.setForeground(COLOR_TEXTO_PRINCIPAL);
        textField.setCaretColor(COLOR_AZUL_PRINCIPAL);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private void styleTable(JTable table) {
        table.setBackground(COLOR_PANEL);
        table.setForeground(COLOR_TEXTO_PRINCIPAL);
        table.setGridColor(COLOR_BORDE);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(COLOR_AZUL_PRINCIPAL.brighter());
        table.setSelectionForeground(Color.WHITE);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(COLOR_AZUL_PRINCIPAL);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setReorderingAllowed(false);
        header.setBorder(null);
    }
    
    // --- Lógica de la aplicación (sin cambios funcionales) ---
    private void cargarPaises() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/paises.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                paisesMap.put(Integer.parseInt(parts[0]), new Pais(Integer.parseInt(parts[0]), parts[1]));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void cargarDepartamentos() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/departamentos.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                departamentosMap.put(Integer.parseInt(parts[0]), new Departamento(Integer.parseInt(parts[0]), parts[1], paisesMap.get(Integer.parseInt(parts[2]))));
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void cargarVuelos() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/vuelos.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Vuelo vuelo = Vuelo.fromString(line, departamentosMap);
                vuelosMap.put(vuelo.getIdVuelo(), vuelo);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void cargarAsientos() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/asientos.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                int vueloId = Integer.parseInt(parts[4]);
                Vuelo vuelo = vuelosMap.get(vueloId);
                Asiento asiento = new Asiento(Integer.parseInt(parts[0]), parts[1], parts[2], Boolean.parseBoolean(parts[3]), vuelo);
                asientosMap.put(asiento.getIdAsiento(), asiento);
                if (vuelo != null) vuelo.addAsiento(asiento);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void cargarBoletos() { /* Implementar si es necesario */ }

    private void cargarPasajeros() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/pasajeros.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Pasajero pasajero = Pasajero.fromString(line);
                pasajerosMap.put(pasajero.getIdPasajero(), pasajero);
                nextPasajeroId = Math.max(nextPasajeroId, pasajero.getIdPasajero() + 1);
            }
        } catch (IOException e) { /* Archivo no existe */ }
    }

    private void loadTablesData() {
        String[] columnasVuelos = {"ID", "Número", "Origen", "Destino", "Salida", "Llegada", "Distancia"};
        DefaultTableModel modeloVuelos = new DefaultTableModel(columnasVuelos, 0){
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        vuelosMap.values().forEach(vuelo -> modeloVuelos.addRow(new Object[]{
            vuelo.getIdVuelo(), vuelo.getNumeroVuelo(), vuelo.getDepartamentoOrigen().getNombre(),
            vuelo.getDepartamentoDestino().getNombre(), vuelo.getFechaSalida().format(formatter),
            vuelo.getFechaLlegada().format(formatter), vuelo.getDistanciaMillas() + " mi"
        }));
        vuelosTable.setModel(modeloVuelos);
       if (boletosTable != null) {
    actualizarTablaBoletos();
}
    }

    private void calcularPrecio() {
        int selectedRow = vuelosTable.getSelectedRow();
        if (selectedRow  == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un vuelo de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int vueloId = (Integer) vuelosTable.getValueAt(selectedRow, 0);
        Vuelo vuelo = vuelosMap.get(vueloId);
        String clase = (String) claseComboBox.getSelectedItem();
        double precio = calcularPrecioPorClase(vuelo.getDistanciaMillas(), clase);
        totalLabel.setText(String.format("Total: $%.2f", precio));
    }

    private double calcularPrecioPorClase(double distancia, String clase) {
        double precioPorMilla = 0.10, multiplicador = 1.0;
        if ("Negocios".equals(clase)) multiplicador = 1.5;
        else if ("Primera".equals(clase)) multiplicador = 2.0;
        return distancia * precioPorMilla * multiplicador;
    }

    private void comprarBoleto() {
        if (!validarCampos()) return;
        int selectedRow = vuelosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un vuelo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int vueloId = (Integer) vuelosTable.getValueAt(selectedRow, 0);
        Vuelo vuelo = vuelosMap.get(vueloId);
        String clase = (String) claseComboBox.getSelectedItem();
        Asiento asientoDisponible = vuelo.getAsientos().stream().filter(a -> a.isDisponible() && a.getClase().equals(clase)).findFirst().orElse(null);
        if (asientoDisponible == null) {
            JOptionPane.showMessageDialog(this, "No hay asientos disponibles en clase " + clase, "Sin Asientos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        LocalDate fechaNacimiento = ((Date) fechaNacimientoSpinner.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        Pasajero pasajero = new Pasajero(nextPasajeroId++, nombreField.getText(), apellidoField.getText(), fechaNacimiento, nacionalidadField.getText());
        double precio = calcularPrecioPorClase(vuelo.getDistanciaMillas(), clase);
        String codigoReserva = "RES" + System.currentTimeMillis() % 100000;
        Boleto boleto = new Boleto(nextBoletoId++, precio, "Confirmado", codigoReserva, LocalDateTime.now(), "Regular", vuelo, pasajero);
        asientoDisponible.setDisponible(false);
        pasajerosMap.put(pasajero.getIdPasajero(), pasajero);
        boletosMap.put(boleto.getIdBoleto(), boleto);
        guardarDatos();
        JOptionPane.showMessageDialog(this, "¡Boleto comprado exitosamente!\nCódigo de reserva: " + codigoReserva + "\nAsiento: " + asientoDisponible.getNumeroAsiento(), "Compra Confirmada", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
    if (boletosTable != null) {
    actualizarTablaBoletos();
}
    }

    
    

    private void actualizarTablaBoletos() {
        actualizarTablaBoletos(new ArrayList<>(boletosMap.values()));
    }

    private void actualizarTablaBoletos(List<Boleto> listaBoletos) {
        String[] columnas = {"ID", "Código", "Precio", "Estado", "Vuelo", "Pasajero", "Fecha Compra"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        listaBoletos.forEach(boleto -> modelo.addRow(new Object[]{
            boleto.getIdBoleto(), boleto.getCodigoReserva(), String.format("$%.2f", boleto.getPrecio()),
            boleto.getEstado(), boleto.getVuelo().getNumeroVuelo(), boleto.getPasajero().getNombre() + " " + boleto.getPasajero().getApellido(),
            boleto.getFechaCompra().format(formatter)
        }));
        boletosTable.setModel(modelo);
    }

    private boolean validarCampos() {
        if (nombreField.getText().trim().isEmpty() || apellidoField.getText().trim().isEmpty() || nacionalidadField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos del pasajero son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        nombreField.setText("");
        apellidoField.setText("");
        nacionalidadField.setText("");
        fechaNacimientoSpinner.setValue(new Date());
        claseComboBox.setSelectedIndex(0);
        totalLabel.setText("Total: $0.00");
        if (vuelosTable.getSelectedRow() != -1) vuelosTable.clearSelection();
    }

    private void guardarDatos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/pasajeros.txt"))) { pasajerosMap.values().forEach(p -> pw.println(p.toString())); } catch (IOException e) { e.printStackTrace(); }
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/boletos.txt"))) { boletosMap.values().forEach(b -> pw.println(b.toString())); } catch (IOException e) { e.printStackTrace(); }
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/asientos.txt"))) { asientosMap.values().forEach(a -> pw.println(a.getIdAsiento() + "|" + a.getNumeroAsiento() + "|" + a.getClase() + "|" + a.isDisponible() + "|" + a.getVuelo().getIdVuelo())); } catch (IOException e) { e.printStackTrace(); }
    }
}