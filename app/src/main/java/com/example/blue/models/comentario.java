package com.example.blue.models;

public class comentario {

    String ID_comentario, Contenido, ID_paciente, ID_especialista, Fecha, ID_publicacion;

    public comentario() {
    }

    public comentario(String ID_comentario, String contenido, String ID_paciente, String ID_especialista, String fecha, String ID_publicacion) {
        this.ID_comentario = ID_comentario;
        Contenido = contenido;
        this.ID_paciente = ID_paciente;
        this.ID_especialista = ID_especialista;
        Fecha = fecha;
        this.ID_publicacion = ID_publicacion;
    }

    public String getID_comentario() {
        return ID_comentario;
    }

    public void setID_comentario(String ID_comentario) {
        this.ID_comentario = ID_comentario;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String contenido) {
        Contenido = contenido;
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

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getID_publicacion() {
        return ID_publicacion;
    }

    public void setID_publicacion(String ID_publicacion) {
        this.ID_publicacion = ID_publicacion;
    }
}
