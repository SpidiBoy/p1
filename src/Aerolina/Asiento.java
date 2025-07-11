package Aerolina;

public class Asiento {
     private int idAsiento; // ID único para el asiento
    private String numeroAsiento;
    private String clase;
    private boolean disponible;
    private Vuelo vuelo; // ID del vuelo al que pertenece este asiento

    public Asiento(int idAsiento, String numeroAsiento, String clase, boolean disponible, Vuelo vuelo) {
        this.idAsiento = idAsiento;
        this.numeroAsiento = numeroAsiento;
        this.clase = clase;
        this.disponible = disponible;
        this.vuelo = vuelo;
    }

    /**
     * @return the idAsiento
     */
    public int getIdAsiento() {
        return idAsiento;
    }

    /**
     * @param idAsiento the idAsiento to set
     */
    public void setIdAsiento(int idAsiento) {
        this.idAsiento = idAsiento;
    }

    /**
     * @return the numeroAsiento
     */
    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    /**
     * @param numeroAsiento the numeroAsiento to set
     */
    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    /**
     * @return the clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @return the disponible
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * @param disponible the disponible to set
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * @return the vuelo
     */
    public Vuelo getVuelo() {
        return vuelo;
    }

    /**
     * @param vuelo the vuelo to set
     */
    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }
    
    
    
    
    
    
    }

   