package com.example.blue.models;

public class recordatorio {
    String ID_recordatorio, NombreRec, Descripcion, ID_paciente, TiempoEj, Repeticion;

    public recordatorio()
    {

    }

    public recordatorio(String ID_recordatorio, String nombreRec, String descripcion, String ID_paciente, String tiempoEj, String repeticion) {
        this.ID_recordatorio = ID_recordatorio;
        NombreRec = nombreRec;
        Descripcion = descripcion;
        this.ID_paciente = ID_paciente;
        TiempoEj = tiempoEj;
        Repeticion = repeticion;
    }

    public String getID_recordatorio() {
        return ID_recordatorio;
    }

    public void setID_recordatorio(String ID_recordatorio) {
        this.ID_recordatorio = ID_recordatorio;
    }

    public String getNombreRec() {
        return NombreRec;
    }

    public void setNombreRec(String nombreRec) {
        NombreRec = nombreRec;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getID_paciente() {
        return ID_paciente;
    }

    public void setID_paciente(String ID_paciente) {
        this.ID_paciente = ID_paciente;
    }

    public String getTiempoEj() {
        return TiempoEj;
    }

    public void setTiempoEj(String tiempoEj) {
        TiempoEj = tiempoEj;
    }

    public String getRepeticion() {
        return Repeticion;
    }

    public void setRepeticion(String repeticion) {
        Repeticion = repeticion;
    }
}
