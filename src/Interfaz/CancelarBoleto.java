/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import Aerolina.Asiento;
import Aerolina.Boleto;
import Aerolina.Pasajero;
import Aerolina.Vuelo;
import Viaje.Departamento;
import Viaje.Pais;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author BIENVENIDO
 */
public class CancelarBoleto extends JFrame{



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

    public CancelarBoleto() {
        initializeData();
        initializeUI();
        loadTablesData();
    }

    private void initializeData() {
        cargarPaises();
        cargarDepartamentos();
        cargarVuelos();
        cargarAsientos();
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

        tabbedPane.addTab("Cancelar Boleto", createCancelacionPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    

    private JPanel createCancelacionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel superior con búsqueda
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(COLOR_PANEL);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(5,5,5,5)
        ));

        JLabel codigoLabel = new JLabel("Código de Reserva:");
        codigoLabel.setForeground(COLOR_TEXTO_PRINCIPAL);
        topPanel.add(codigoLabel);
        
        codigoReservaField = new JTextField(15);
        styleTextField(codigoReservaField);
        topPanel.add(codigoReservaField);

        JButton buscarButton = new JButton("Buscar");
        styleButton(buscarButton, COLOR_AZUL_PRINCIPAL, Color.WHITE);
        buscarButton.addActionListener(e -> buscarBoleto());
        topPanel.add(buscarButton);
        
        JButton actualizarButton = new JButton("Mostrar Todos");
        styleButton(actualizarButton, COLOR_GRIS_INFO, Color.WHITE);
        actualizarButton.addActionListener(e -> actualizarTablaBoletos());
        topPanel.add(actualizarButton);

        // Panel central con tabla de boletos
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(COLOR_PANEL);
        centerPanel.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        
        boletosTable = new JTable();
        styleTable(boletosTable);
        JScrollPane scrollPane = new JScrollPane(boletosTable);
        scrollPane.setBorder(null);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.setBackground(COLOR_FONDO);

        JButton cancelarButton = new JButton("Cancelar Boleto Seleccionado");
        styleButton(cancelarButton, COLOR_ROJO_PELIGRO, Color.WHITE);
        cancelarButton.addActionListener(e -> cancelarBoleto());
        bottomPanel.add(cancelarButton);
        
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

        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
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

    private void cancelarBoleto() {
        int selectedRow = boletosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un boleto para cancelar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int boletoId = (Integer) boletosTable.getValueAt(selectedRow, 0);
        Boleto boleto = boletosMap.get(boletoId);
        if ("Cancelado".equals(boleto.getEstado())) {
            JOptionPane.showMessageDialog(this, "Este boleto ya ha sido cancelado.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea cancelar el boleto " + boleto.getCodigoReserva() + "?", "Confirmar Cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            boleto.setEstado("Cancelado");
            boleto.getVuelo().getAsientos().stream().filter(a -> !a.isDisponible()).findFirst().ifPresent(a -> a.setDisponible(true));
            guardarDatos();
            JOptionPane.showMessageDialog(this, "Boleto cancelado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTablaBoletos();
        }
    }

    private void buscarBoleto() {
        String codigo = codigoReservaField.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un código de reserva para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Boleto> boletosEncontrados = new ArrayList<>();
        boletosMap.values().forEach(boleto -> {
            if (boleto.getCodigoReserva().equalsIgnoreCase(codigo)) boletosEncontrados.add(boleto);
        });
        if (boletosEncontrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron boletos con el código: " + codigo, "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
        } else {
            actualizarTablaBoletos(boletosEncontrados);
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

  


    private void guardarDatos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/pasajeros.txt"))) { pasajerosMap.values().forEach(p -> pw.println(p.toString())); } catch (IOException e) { e.printStackTrace(); }
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/boletos.txt"))) { boletosMap.values().forEach(b -> pw.println(b.toString())); } catch (IOException e) { e.printStackTrace(); }
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/asientos.txt"))) { asientosMap.values().forEach(a -> pw.println(a.getIdAsiento() + "|" + a.getNumeroAsiento() + "|" + a.getClase() + "|" + a.isDisponible() + "|" + a.getVuelo().getIdVuelo())); } catch (IOException e) { e.printStackTrace(); }
    }
}
    

