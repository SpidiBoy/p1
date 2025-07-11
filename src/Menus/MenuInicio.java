/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menus;

import Cuenta.DataLector;
import Cuenta.Usuario;

/**
 *
 * @author win12
 */
public class MenuInicio {
    
    private final Usuario[] usuarios;

    public MenuInicio() {
        this.usuarios = DataLector.getUsuarios();
    }
    
    
    public boolean verificarUsuario(String usuario, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(usuario) && u.getContrasena().equals(contrasena)) return true; // Verifica las credenciales
        }
        return false; // Devuelve falso si no se encuentran credenciales válidas
    }

}

    

