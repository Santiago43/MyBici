package modelo.dto;


import java.util.*;

/**
 * 
 */
public class Calle {

    /**
     * Default constructor
     */
    public Calle() {
    }

    /**
     * 
     */
    private int idCalle;

    /**
     * 
     */
    private int numeroCalle;

    /**
     * 
     */
    private char letraCalle;

    /**
     * 
     */
    private boolean bis;

    /**
     * 
     */
    private boolean sur;

    public int getIdCalle() {
        return idCalle;
    }

    public void setIdCalle(int idCalle) {
        this.idCalle = idCalle;
    }

    public int getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(int numeroCalle) {
        this.numeroCalle = numeroCalle;
    }

    public char getLetraCalle() {
        return letraCalle;
    }

    public void setLetraCalle(char letraCalle) {
        this.letraCalle = letraCalle;
    }

    public boolean isBis() {
        return bis;
    }

    public void setBis(boolean bis) {
        this.bis = bis;
    }

    public boolean isSur() {
        return sur;
    }

    public void setSur(boolean sur) {
        this.sur = sur;
    }



}