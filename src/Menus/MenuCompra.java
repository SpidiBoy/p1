/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menus;

import Aerolina.Boleto;
import Cuenta.DataLector;
import Cuenta.Usuario;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author BIENVENIDO
 */
public class MenuCompra {
    
    
    
    private static int generarIdBoleto() {
        return (int) (Math.random() * 10000);
    }
    
      private static String generarCodigo() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) sb.append(letras.charAt((int) (Math.random() * letras.length())));
        sb.append((int) (Math.random() * 900 + 100));
        return sb.toString();
    }


    public static void guardarBoletoEnArchivo(Boleto boleto) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/boletos.txt", true))) {
        String linea = boleto.getIdBoleto() + "|" +
                       boleto.getPrecio() + "|" +
                       boleto.getEstado() + "|" +
                       boleto.getCodigoReserva() + "|" +
                       boleto.getFechaCompra().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "|" +
                       boleto.getTipo() + "|" +
                       boleto.getVuelo().getIdVuelo() + "|" +
                       boleto.getPasajero().getIdPasajero();

        bw.write(linea);
        bw.newLine();
    } catch (IOException e) {
        System.err.println("Error al guardar el boleto: " + e.getMessage());
    }
}
    
}
