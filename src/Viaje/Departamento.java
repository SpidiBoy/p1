    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viaje;

import java.util.Map;

/**
 *
 * @author win12
 */
    public class Departamento {
    private int idDepartamento;
    private String nombre;
    private Pais pais;

    public Departamento(int idDepartamento, String nombre, Pais pais) {
    this.idDepartamento = idDepartamento;
    this.nombre = nombre;
    this.pais = pais;
    }

    @Override
    public String toString() {
        return "Departamento{" + "idDepartamento=" + idDepartamento + ", nombre=" + nombre + ", pais=" + pais + '}';
    }

     

    /**
     * @return the idDepartamento
     */
    public int getIdDepartamento() {
        return idDepartamento;
    }

    /**
     * @param idDepartamento the idDepartamento to set
     */
    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the pais
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }
     
    
    
    
    
}
