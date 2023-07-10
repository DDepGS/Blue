package com.example.blue.models;

public class publicacion {

    String ID_publicacion, Titulo, Contenido, ID_paciente, ID_especialista;

    public publicacion() {
    }

    public publicacion(String ID_publicacion, String titulo, String contenido, String ID_paciente, String ID_especialista) {
        this.ID_publicacion = ID_publicacion;
        Titulo = titulo;
        Contenido = contenido;
        this.ID_paciente = ID_paciente;
        this.ID_especialista = ID_especialista;
    }

    public String getID_publicacion() {
        return ID_publicacion;
    }

    public void setID_publicacion(String ID_publicacion) {
        this.ID_publicacion = ID_publicacion;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
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
}
