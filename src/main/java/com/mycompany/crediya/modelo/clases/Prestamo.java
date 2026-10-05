/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crediya.modelo.clases;

import java.time.LocalDate;

/**
 *
 * @author jessica urrego
 */
public class Prestamo {
    

    private int id;
    private int clienteId;
    private int empleadoId;
    private double monto;
    private double interes;
    private int cuotas;
    private LocalDate fechaInicio;
    private String estado;
    private double saldoPendiente;
    
    public Prestamo() {
}

   public Prestamo(int id, int clienteId, int empleadoId,
                double monto, double interes, int cuotas,
                LocalDate fechaInicio, String estado) {

    this.id = id;
    this.clienteId = clienteId;
    this.empleadoId = empleadoId;
    this.monto = monto;
    this.interes = interes;
    this.cuotas = cuotas;
    this.fechaInicio = fechaInicio;
    this.estado = estado;
}
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", clienteId=" + clienteId + ", empleadoId=" + empleadoId + ", monto=" + monto + ", interes=" + interes + ", cuotas=" + cuotas + ", fechaInicio=" + fechaInicio + ", estado=" + estado + '}';
    }
    public double calcularMontoTotal() {
    return monto + (monto * interes / 100);
}
    public double calcularCuotaMensual() {
    return calcularMontoTotal() / cuotas;
}

   public double getSaldoPendiente() {
    return saldoPendiente;
}
    public void setSaldoPendiente(double saldoPendiente) {
    this.saldoPendiente = saldoPendiente;
}

}
    

