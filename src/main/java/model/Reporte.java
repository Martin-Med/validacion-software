package model;

import java.util.Date;
import java.util.List;

public class Reporte {
    private int id;
    private String tipo;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Object> datos;

    public Reporte(int id, String tipo, Date fechaInicio, Date fechaFin, List<Object> datos) {
        this.id = id;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.datos = datos;
    }

    public String generarReporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reporte ").append(tipo).append("\n");
        sb.append("Período: ").append(fechaInicio).append(" - ").append(fechaFin).append("\n");
        sb.append("Datos:\n");
        for (Object dato : datos) {
            sb.append(dato.toString()).append("\n");
        }
        return sb.toString();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public List<Object> getDatos() { return datos; }
    public void setDatos(List<Object> datos) { this.datos = datos; }
}