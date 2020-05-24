package modelo.dto;

/**
 * 
 */

public class Empleado extends Persona {

    
    public Empleado() {
    }
   
    private String profesion;
    private String cargo;   
    private double salario;
    private int idSede;
    private int cedula;

    public String getProfesion() {
        return profesion;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    @Override
    public int getCedula() {
        return cedula;
    }

    @Override
    public void setCedula(int cedula) {
        this.cedula = cedula;
    }
    
    
}

