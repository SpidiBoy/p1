package Aerolina;
import Cuenta.Usuario;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Boleto {
    private int idBoleto;
    private double precio;
    private String estado;
    private String codigoReserva;
    private LocalDateTime fechaCompra;
    private String tipo; 
    private Vuelo vuelo;
    private Pasajero pasajero; 

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Boleto(int idBoleto, double precio, String estado, String codigoReserva,
                  LocalDateTime fechaCompra, String tipo, Vuelo vuelo,
                  Pasajero pasajero) {
        this.idBoleto = idBoleto;
        this.precio = precio;
        this.estado = estado;
        this.codigoReserva = codigoReserva;
        this.fechaCompra = fechaCompra;
        this.tipo = tipo;
        this.vuelo = vuelo;
        
        this.pasajero = pasajero;
    }

    // Getters
    public int getIdBoleto() { return idBoleto; }
    public double getPrecio() { return precio; }
    public String getEstado() { return estado; }
    public String getCodigoReserva() { return codigoReserva; }
    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public String getTipo() { return tipo; }

    public Vuelo getVuelo() { return vuelo; }
  
    public Pasajero getPasajero() { return pasajero; }

    // Setters
    public void setIdBoleto(int idBoleto) { this.idBoleto = idBoleto; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCodigoReserva(String codigoReserva) { this.codigoReserva = codigoReserva; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public void setVuelo(Vuelo vuelo) { this.vuelo = vuelo; }
    public void setPasajero(Pasajero pasajero) { this.pasajero = pasajero; }

    @Override
    public String toString() {
        // Almacenamos los IDs de los objetos relacionados
        return idBoleto + "|" +
               precio + "|" +
               estado + "|" +
               codigoReserva + "|" +
               fechaCompra.format(FORMATTER) + "|" +
               tipo + "|" +
               (vuelo != null ? vuelo.getIdVuelo() : "") + "|" +
               (pasajero != null ? pasajero.getIdPasajero() : "");
    }

    // Método estático para construir un Boleto desde una línea de texto
    
}