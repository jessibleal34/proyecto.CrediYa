/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crediya.modelo.clases;

/**
 *
 * @author jessica urrego
 */
public class Cliente {
    

    private int id;
    private String nombre;
    private String documento;
    private String correo;
    private String telefono;
    
    public Cliente() {
}
    public Cliente(int id, String nombre, String documento,
               String correo, String telefono) {

    this.id = id;
    this.nombre = nombre;
    this.documento = documento;
    this.correo = correo;
    this.telefono = telefono;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", documento=" + documento + ", correo=" + correo + ", telefono=" + telefono + '}';
    }
    

}
    

