package com.example.blue.models;

public class u_paciente {
    String ID_paciente, Nombre, APaterno, AMaterno, Diagnostico, Contrasena, Medicamento, Correo, Usuario, Telefono;

    public u_paciente()
    {

    }

    public u_paciente(String ID_paciente, String nombre, String APaterno, String AMaterno, String diagnostico, String contrasena, String medicamento, String correo, String usuario, String telefono) {
        this.ID_paciente = ID_paciente;
        Nombre = nombre;
        this.APaterno = APaterno;
        this.AMaterno = AMaterno;
        Diagnostico = diagnostico;
        Contrasena = contrasena;
        Medicamento = medicamento;
        Correo = correo;
        Usuario = usuario;
        Telefono = telefono;
    }

    public String getID_paciente() {
        return ID_paciente;
    }

    public void setID_paciente(String ID_paciente) {
        this.ID_paciente = ID_paciente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getAPaterno() {
        return APaterno;
    }

    public void setAPaterno(String APaterno) {
        this.APaterno = APaterno;
    }

    public String getAMaterno() {
        return AMaterno;
    }

    public void setAMaterno(String AMaterno) {
        this.AMaterno = AMaterno;
    }

    public String getDiagnostico() {
        return Diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        Diagnostico = diagnostico;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public String getMedicamento() {
        return Medicamento;
    }

    public void setMedicamento(String medicamento) {
        Medicamento = medicamento;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
