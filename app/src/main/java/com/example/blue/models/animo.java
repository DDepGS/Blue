package com.example.blue.models;

public class animo {
    String ID_animo, Animo, Fecha, ID_paciente;

    public animo() {
    }

    public animo(String ID_animo, String animo, String fecha, String ID_paciente) {
        this.ID_animo = ID_animo;
        Animo = animo;
        Fecha = fecha;
        this.ID_paciente = ID_paciente;
    }

    public String getID_animo() {
        return ID_animo;
    }

    public void setID_animo(String ID_animo) {
        this.ID_animo = ID_animo;
    }

    public String getAnimo() {
        return Animo;
    }

    public void setAnimo(String animo) {
        Animo = animo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getID_paciente() {
        return ID_paciente;
    }

    public void setID_paciente(String ID_paciente) {
        this.ID_paciente = ID_paciente;
    }
}
