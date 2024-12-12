package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

import model.Reporte;
import model.Venta;
import model.Compra;
import model.Producto;

public class ReporteService {
    private List<Reporte> reportes;
    private VentaService ventaService;
    private CompraService compraService;
    private ProductoService productoService;

    public ReporteService(VentaService ventaService, CompraService compraService, ProductoService productoService) {
        this.reportes = new ArrayList<>();
        this.ventaService = ventaService;
        this.compraService = compraService;
        this.productoService = productoService;
    }

    public Reporte generarReporteVentas(Date fechaInicio, Date fechaFin) {
        List<Venta> ventas = ventaService.getVentas().stream()
                .filter(v -> !v.getFecha().before(fechaInicio) && !v.getFecha().after(fechaFin))
                .collect(Collectors.toList());

        List<Object> datos = new ArrayList<>();
        double totalVentas = 0;

        for (Venta venta : ventas) {
            datos.add(String.format("Venta #%d - %s - Total: $%.2f", 
                venta.getId(), venta.getFecha(), venta.getTotal()));
            totalVentas += venta.getTotal();
        }

        datos.add(String.format("\nTotal del período: $%.2f", totalVentas));

        Reporte reporte = new Reporte(
            reportes.size() + 1,
            "Ventas",
            fechaInicio,
            fechaFin,
            datos
        );

        reportes.add(reporte);
        return reporte;
    }

    public Reporte generarReporteCompras(Date fechaInicio, Date fechaFin) {
        List<Compra> compras = compraService.getCompras().stream()
                .filter(c -> !c.getFecha().before(fechaInicio) && !c.getFecha().after(fechaFin))
                .collect(Collectors.toList());

        List<Object> datos = new ArrayList<>();
        double totalCompras = 0;

        for (Compra compra : compras) {
            datos.add(String.format("Compra #%d - %s - Proveedor: %s - Total: $%.2f",
                compra.getId(), compra.getFecha(), compra.getProveedor(), compra.getTotal()));
            totalCompras += compra.getTotal();
        }

        datos.add(String.format("\nTotal del período: $%.2f", totalCompras));

        Reporte reporte = new Reporte(
            reportes.size() + 1,
            "Compras",
            fechaInicio,
            fechaFin,
            datos
        );

        reportes.add(reporte);
        return reporte;
    }

    public Reporte generarReporteInventario() {
        List<Producto> productos = productoService.getProductos();
        List<Object> datos = new ArrayList<>();
        double valorTotal = 0;

        for (Producto producto : productos) {
            double valorInventario = producto.getPrecio() * producto.getStock();
            datos.add(String.format("Producto: %s - Stock: %d - Valor unitario: $%.2f - Valor total: $%.2f",
                producto.getNombre(), producto.getStock(), producto.getPrecio(), valorInventario));
            valorTotal += valorInventario;
        }

        datos.add(String.format("\nValor total del inventario: $%.2f", valorTotal));

        Reporte reporte = new Reporte(
            reportes.size() + 1,
            "Inventario",
            new Date(),
            new Date(),
            datos
        );

        reportes.add(reporte);
        return reporte;
    }

    public Reporte buscarReporte(int id) {
        return reportes.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Reporte> buscarReportesPorTipo(String tipo) {
        return reportes.stream()
                .filter(r -> r.getTipo().equals(tipo))
                .collect(Collectors.toList());
    }

    public List<Reporte> getReportes() {
        return new ArrayList<>(reportes);
    }

    public boolean eliminarReporte(int id) {
        Reporte reporte = buscarReporte(id);
        if (reporte == null) {
            JOptionPane.showMessageDialog(null, "Reporte no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        reportes.remove(reporte);
        return true;
    }
}