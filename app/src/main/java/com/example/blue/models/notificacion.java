package com.example.blue.models;

public class notificacion {
    String ID_notificacion, Nombre, Descripcion, Estado, ID_paciente, ID_especialista, Tipo;

    public notificacion() {
    }

    public notificacion(String ID_notificacion, String nombre, String descripcion, String estado, String ID_paciente, String ID_especialista, String tipo) {
        this.ID_notificacion = ID_notificacion;
        Nombre = nombre;
        Descripcion = descripcion;
        Estado = estado;
        this.ID_paciente = ID_paciente;
        this.ID_especialista = ID_especialista;
        Tipo = tipo;
    }

    public String getID_notificacion() {
        return ID_notificacion;
    }

    public void setID_notificacion(String ID_notificacion) {
        this.ID_notificacion = ID_notificacion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getID_paciente() {
        return ID_paciente;
    }

    public void setID_paciente(String ID_paciente) {
        this.ID_paciente = ID_paciente;
    }

    public String getID_especialista() {
        return ID_especialista;
    }

    public void setID_especialista(String ID_especialista) {
        this.ID_especialista = ID_especialista;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}
