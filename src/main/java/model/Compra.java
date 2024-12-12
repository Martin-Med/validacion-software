package model;

import java.util.Date;
import java.util.List;

public class Compra {
    private int id;
    private Date fecha;
    private String proveedor;
    private List<LineaCompra> lineasCompra;
    private double total;

    public Compra(int id, Date fecha, String proveedor, List<LineaCompra> lineasCompra) {
        this.id = id;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.lineasCompra = lineasCompra;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        return lineasCompra.stream().mapToDouble(LineaCompra::getSubtotal).sum();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public List<LineaCompra> getLineasCompra() { return lineasCompra; }
    public void setLineasCompra(List<LineaCompra> lineasCompra) {
        this.lineasCompra = lineasCompra;
        this.total = calcularTotal();
    }
    public double getTotal() { return total; }
}