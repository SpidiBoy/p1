/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viaje;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author win12
 */
public class Pais {
    private int id;
    private String nombre;
    private List<Departamento> departamentos; // Agregado para la relación bidireccional

    public Pais(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.departamentos = new ArrayList<>(); // Inicializar la lista de departamentos
    }


    


    @Override
    public String toString() {
        return "Pais{" + "id=" + id + ", nombre=" + nombre + ", departamentos=" + departamentos + '}';
    }
    
    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the departamentos
     */
    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    /**
     * @param departamentos the departamentos to set
     */
    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
     }




   

 
    

