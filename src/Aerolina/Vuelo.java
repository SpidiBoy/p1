package Aerolina;
import Aerolina.Asiento;
import Viaje.Departamento;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Vuelo {
    private int idVuelo;
    private String numeroVuelo;
    private Departamento departamentoOrigen;
    private Departamento departamentoDestino;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaLlegada;
    private double distanciaMillas;
    private List<Asiento> asientos; // Esta lista es para manejo en memoria, no para el archivo de vuelos
    private List<Boleto> boletos;   // Esta lista es para manejo en memoria, no para el archivo de vuelos

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // "yyyy-MM-dd'T'HH:mm:ss"

    public Vuelo(int idVuelo, String numeroVuelo, Departamento departamentoOrigen,
                 Departamento departamentoDestino, LocalDateTime fechaSalida,
                 LocalDateTime fechaLlegada, double distanciaMillas) {
        this.idVuelo = idVuelo;
        this.numeroVuelo = numeroVuelo;
        this.departamentoOrigen = departamentoOrigen;
        this.departamentoDestino = departamentoDestino;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.distanciaMillas = distanciaMillas;
        this.asientos = new ArrayList<>();
        this.boletos = new ArrayList<>();
    }

    // Getters
    public int getIdVuelo() { return idVuelo; }
    public String getNumeroVuelo() { return numeroVuelo; }
    public Departamento getDepartamentoOrigen() { return departamentoOrigen; }
    public Departamento getDepartamentoDestino() { return departamentoDestino; }
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public LocalDateTime getFechaLlegada() { return fechaLlegada; }
    public double getDistanciaMillas() { return distanciaMillas; }
    public List<Asiento> getAsientos() { return asientos; }
    public List<Boleto> getBoletos() { return boletos; }

    // Setters
    public void setIdVuelo(int idVuelo) { this.idVuelo = idVuelo; }
    public void setNumeroVuelo(String numeroVuelo) { this.numeroVuelo = numeroVuelo; }
    public void setDepartamentoOrigen(Departamento departamentoOrigen) { this.departamentoOrigen = departamentoOrigen; }
    public void setDepartamentoDestino(Departamento departamentoDestino) { this.departamentoDestino = departamentoDestino; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }
    public void setFechaLlegada(LocalDateTime fechaLlegada) { this.fechaLlegada = fechaLlegada; }
    public void setDistanciaMillas(double distanciaMillas) { this.distanciaMillas = distanciaMillas; }
    public void setAsientos(List<Asiento> asientos) { this.asientos = asientos; }
    public void setBoletos(List<Boleto> boletos) { this.boletos = boletos; }

    // Métodos para añadir elementos a las listas en memoria
    public void addAsiento(Asiento asiento) {
        if (asiento != null && !this.asientos.contains(asiento)) {
            this.asientos.add(asiento);
        }
    }

    public void addBoleto(Boleto boleto) {
        if (boleto != null && !this.boletos.contains(boleto)) {
            this.boletos.add(boleto);
        }
    }

    @Override
    public String toString() {
        // Formato para guardar en archivo: solo los atributos base y IDs de relaciones directas
        return idVuelo + "|" +
               numeroVuelo + "|" +
               (departamentoOrigen != null ? departamentoOrigen.getIdDepartamento() : "") + "|" +
               (departamentoDestino != null ? departamentoDestino.getIdDepartamento() : "") + "|" +
               fechaSalida.format(FORMATTER) + "|" +
               fechaLlegada.format(FORMATTER) + "|" +
               distanciaMillas;
    }

    public static Vuelo fromString(String line, Map<Integer, Departamento> departamentosMap) {
        String[] parts = line.split("\\|");
        int idVuelo = Integer.parseInt(parts[0]);
        String numeroVuelo = parts[1];
        int idOrigen = Integer.parseInt(parts[2]);
        int idDestino = Integer.parseInt(parts[3]);
        LocalDateTime fechaSalida = LocalDateTime.parse(parts[4], FORMATTER);
        LocalDateTime fechaLlegada = LocalDateTime.parse(parts[5], FORMATTER);
        double distanciaMillas = Double.parseDouble(parts[6]);

        Departamento origen = departamentosMap.get(idOrigen);
        Departamento destino = departamentosMap.get(idDestino);

        return new Vuelo(idVuelo, numeroVuelo, origen, destino, fechaSalida, fechaLlegada, distanciaMillas);
    }
}
