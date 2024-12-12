package service;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Compra;
import model.LineaCompra;
import model.Producto;

public class CompraService {
    private List<Compra> compras;
    private ProductoService productoService;

    public CompraService(ProductoService productoService) {
        this.compras = new ArrayList<>();
        this.productoService = productoService;
    }

    public boolean crearCompra(Compra compra) {
        // Validar la compra
        if (!validarCompra(compra)) {
            return false;
        }

        // Verificar que los productos existen
        for (LineaCompra linea : compra.getLineasCompra()) {
            Producto producto = productoService.buscarProducto(linea.getProducto().getId());
            if (producto == null) {
                JOptionPane.showMessageDialog(null, 
                    "El producto " + linea.getProducto().getNombre() + " no existe", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Actualizar inventario
        for (LineaCompra linea : compra.getLineasCompra()) {
            productoService.actualizarInventario(
                linea.getProducto().getId(), 
                linea.getCantidad()
            );
        }

        compras.add(compra);
        return true;
    }

    public Compra buscarCompra(int id) {
        return compras.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Compra> buscarComprasPorProveedor(String proveedor) {
        return compras.stream()
                .filter(c -> c.getProveedor().equals(proveedor))
                .toList();
    }

    public boolean cancelarCompra(int id) {
        Compra compra = buscarCompra(id);
        if (compra == null) {
            JOptionPane.showMessageDialog(null, "Compra no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Revertir el inventario
        for (LineaCompra linea : compra.getLineasCompra()) {
            productoService.actualizarInventario(
                linea.getProducto().getId(), 
                -linea.getCantidad()
            );
        }

        compras.remove(compra);
        return true;
    }

    public double calcularTotalCompras() {
        return compras.stream()
                .mapToDouble(Compra::getTotal)
                .sum();
    }

    public List<Compra> getCompras() {
        return new ArrayList<>(compras);
    }

    public boolean validarCompra(Compra compra) {
        if (compra.getProveedor() == null || compra.getProveedor().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La compra debe tener un proveedor", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (compra.getLineasCompra() == null || compra.getLineasCompra().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La compra debe tener al menos una línea", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        for (LineaCompra linea : compra.getLineasCompra()) {
            if (linea.getCantidad() <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (linea.getPrecioUnitario() <= 0) {
                JOptionPane.showMessageDialog(null, "El precio unitario debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }
}