package Aerolina;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Pasajero {
    private int idPasajero;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private Boleto boleto;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; // Formato para fecha de nacimiento

    public Pasajero(int idPasajero, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad) {
        this.idPasajero = idPasajero;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
    }

    // Getters
    public int getIdPasajero() { return idPasajero; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getNacionalidad() { return nacionalidad; }

    // Setters
    public void setIdPasajero(int idPasajero) { this.idPasajero = idPasajero; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    @Override
    public String toString() {
        return idPasajero + "|" + nombre + "|" + apellido + "|" + fechaNacimiento.format(FORMATTER) + "|" + nacionalidad;
    }

    public static Pasajero fromString(String line) {
        String[] parts = line.split("\\|");
        int idPasajero = Integer.parseInt(parts[0]);
        String nombre = parts[1];
        String apellido = parts[2];
        LocalDate fechaNacimiento = LocalDate.parse(parts[3], FORMATTER);
        String nacionalidad = parts[4];
        return new Pasajero(idPasajero, nombre, apellido, fechaNacimiento, nacionalidad);
    }
}
    
    
    

