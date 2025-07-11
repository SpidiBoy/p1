/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cuenta;

import Aerolina.Boleto;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author BIENVENIDO
 */
public class Compra {
    private int idCompra;
    private Usuario usuario; // quién realiza la compra
    private List<Boleto> boletos; // boletos comprados
    private LocalDateTime fechaCompra;
    private double montoTotal;
    private String metodoPago; // opcional

    public Compra(int idCompra, Usuario usuario, List<Boleto> boletos, double montoTotal, String metodoPago) {
        this.idCompra = idCompra;
        this.usuario = usuario;
        this.boletos = boletos;
        this.fechaCompra = LocalDateTime.now();
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    
}
