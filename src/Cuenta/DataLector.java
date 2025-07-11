    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cuenta;

import Aerolina.Asiento;
import Aerolina.Vuelo;
import Viaje.Departamento;
import Viaje.Pais;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author win12
 */
public class DataLector {

    
  public static Usuario[] getUsuarios() {

        // Variable para contar el número de usuarios en el archivo
        int cantUsuarios = 0;

        // Primer bloque try-with-resources para contar el número de líneas (usuarios) en el archivo
        try (FileReader fr = new FileReader("src/usuarios.txt")) {

            // BufferedReader para leer el archivo
            BufferedReader br = new BufferedReader(fr);
            

            // Leer cada línea del archivo y contar el número de líneas
            while(br.readLine() != null) cantUsuarios++;
            

        } catch(Exception e) {
            // Manejo de excepciones en caso de error al leer el archivo
            e.printStackTrace(); 
        }

        // Crear un arreglo de Usuario con el tamaño determinado anteriormente
        Usuario[] usuarios = new Usuario[cantUsuarios];

        // Reiniciar el contador de usuarios
        cantUsuarios = 0;

        // Segundo bloque try-with-resources para leer las líneas y crear objetos Usuario
        try (FileReader fr = new FileReader("src/usuarios.txt")) {

            // BufferedReader para leer el archivo
            BufferedReader br = new BufferedReader(fr);

            String linea;
            // Leer cada línea del archivo y crear un nuevo objeto Usuario para cada línea
            while((linea = br.readLine()) != null) {
                // Separar la línea por el delimitador "|" y crear un nuevo objeto Usuario
                usuarios[cantUsuarios++] = new Usuario(linea.split("\\|")[0], linea.split("\\|")[1]);
            }

        } catch(Exception e) {
            // Manejo de excepciones en caso de error al leer el archivo
            e.printStackTrace();
        }

        // Retornar el arreglo de usuarios
        return usuarios;
    }


  
    public static Map<Integer, Pais> getPaises  (){

        // Variable para contar el número de usuarios en el archivo
        int cantPaises = 0;

        // Primer bloque try-with-resources para contar el número de líneas (usuarios) en el archivo
        try (FileReader fr = new FileReader("src/paises.txt")) {

            // BufferedReader para leer el archivo
            BufferedReader br = new BufferedReader(fr);
            

            // Leer cada línea del archivo y contar el número de líneas
            while(br.readLine() != null) cantPaises++;
            

        } catch(Exception e) {
            // Manejo de excepciones en caso de error al leer el archivo
            e.printStackTrace(); 
        }

        // Crear un arreglo de Usuario con el tamaño determinado anteriormente
         Map<Integer, Pais> paises = new HashMap<>();

        // Reiniciar el contador de usuarios
        cantPaises = 0;

        // Segundo bloque try-with-resources para leer las líneas y crear objetos Usuario
        try (FileReader fr = new FileReader("src/paises.txt")) {

            // BufferedReader para leer el archivo
            BufferedReader br = new BufferedReader(fr);

            String linea;
            int i=0;    
                
            // Leer cada línea del archivo y crear un nuevo objeto Usuario para cada línea
            while((linea = br.readLine()) != null) {
                // Separar la línea por el delimitador "|" y crear un nuevo objeto Usuario
                String[] parts = linea.split("\\|");
                if (parts.length >= 2) {
                int id = Integer.parseInt(parts[0].trim());
                String nombre = parts[1].trim();
                paises.put(id, new Pais(id, nombre));

            }else {
                System.out.println("Línea inválida: " + linea);
                }
                
            }

        } catch(Exception e) {
            // Manejo de excepciones en caso de error al leer el archivo
            e.printStackTrace();
        }

        // Retornar el arreglo de usuarios
        return paises;
    }
    

    
  public static Map<Integer, Departamento> getDepartamentos(Map<Integer, Pais> paisesMap) {
    Map<Integer, Departamento> departamentosMap = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader("src/departamentos.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split("\\|");
            if (partes.length >= 3) {
                int idDep = Integer.parseInt(partes[0].trim());
                String nombre = partes[1].trim();
                int idPais = Integer.parseInt(partes[2].trim());

                Pais pais = paisesMap.get(idPais);
                if (pais != null) {
                    Departamento dep = new Departamento(idDep, nombre, pais);
                    departamentosMap.put(idDep, dep);
                    pais.getDepartamentos().add(dep);
                } else {
                    System.out.println("País con id " + idPais + " no encontrado.");
                }
            } else {
                System.out.println("Línea inválida: " + linea);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return departamentosMap;
}



public static Map<Integer, Vuelo> getVuelos (Map<Integer, Departamento> departamentosMap){

          Map<Integer, Vuelo> vuelos = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader("src/vuelos.txt"))) {
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] parts = linea.split("\\|");

            if (parts.length == 7) {
                int idVuelo = Integer.parseInt(parts[0].trim());
                String numeroVuelo = parts[1].trim();
                int idDeptoOrigen = Integer.parseInt(parts[2].trim());
                int idDeptoDestino = Integer.parseInt(parts[3].trim());
                LocalDateTime fechaSalida = LocalDateTime.parse(parts[4].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                LocalDateTime fechaLlegada = LocalDateTime.parse(parts[5].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                double distanciaMillas = Double.parseDouble(parts[6].trim());

                Departamento deptoOrigen = departamentosMap.get(idDeptoOrigen);
                Departamento deptoDestino = departamentosMap.get(idDeptoDestino);

                if (deptoOrigen != null && deptoDestino != null) {
                    Vuelo vuelo = new Vuelo(idVuelo, numeroVuelo, deptoOrigen, deptoDestino, fechaSalida, fechaLlegada, distanciaMillas);
                    vuelos.put(idVuelo, vuelo);
                    
                } else {
                    System.out.println("Departamentos no encontrados para la línea: " + linea);
                }
            } else {
                System.out.println("Línea inválida: " + linea);
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    return vuelos;
}


public static Map<Integer, Asiento> getAsientos(Map<Integer, Vuelo> vuelosMap) {
    Map<Integer, Asiento> asientosMap = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader("src/asientos.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] parts = linea.split("\\|");
            if (parts.length == 5) {
                int idAsiento = Integer.parseInt(parts[0].trim());
                String codigo = parts[1].trim();
                String clase = parts[2].trim();
                boolean disponible = Boolean.parseBoolean(parts[3].trim());
                int idVuelo = Integer.parseInt(parts[4].trim());

                Vuelo vuelo = vuelosMap.get(idVuelo);
                if (vuelo != null) {
                    Asiento asiento = new Asiento(idAsiento, codigo, clase, disponible, vuelo);
                    vuelo.getAsientos().add(asiento);
                    asientosMap.put(idAsiento, asiento); // lo agregamos al mapa
                } else {
                    System.out.println("Vuelo con id " + idVuelo + " no encontrado para asiento: " + linea);
                }
            } else {
                System.out.println("Línea inválida en asientos.txt: " + linea);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return asientosMap;
}
}

    













    
   



    
