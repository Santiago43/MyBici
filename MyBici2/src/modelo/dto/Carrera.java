package modelo.dto;


import java.util.*;

/**
 * 
 */
public class Carrera {

    /**
     * Default constructor
     */
    public Carrera() {
    }

    /**
     * 
     */
    private int idCarrera;

    /**
     * 
     */
    private int numeroCarrera;

    /**
     * 
     */
    private char letraCarrera;

    /**
     * 
     */
    private boolean bis;

    /**
     * 
     */
    private boolean este;

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public int getNumeroCarrera() {
        return numeroCarrera;
    }

    public void setNumeroCarrera(int numeroCarrera) {
        this.numeroCarrera = numeroCarrera;
    }

    public char getLetraCarrera() {
        return letraCarrera;
    }

    public void setLetraCarrera(char letraCarrera) {
        this.letraCarrera = letraCarrera;
    }

    public boolean isBis() {
        return bis;
    }

    public void setBis(boolean bis) {
        this.bis = bis;
    }

    public boolean isEste() {
        return este;
    }

    public void setEste(boolean este) {
        this.este = este;
    }

    @Override
    public String toString() {
        String parteCarrera=numeroCarrera+ " "+letraCarrera;
        if(bis){
            parteCarrera+="bis";
        }
        if(este){
            parteCarrera+="este";
        }
        else{
            parteCarrera+="oeste";
        }
        return parteCarrera;
    }




}