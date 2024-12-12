package service;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Venta;
import model.LineaVenta;
import model.Producto;
import model.Usuario;

public class VentaService {
    private List<Venta> ventas;
    private ProductoService productoService;
    private UsuarioService usuarioService;

    public VentaService(ProductoService productoService, UsuarioService usuarioService) {
        this.ventas = new ArrayList<>();
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    public boolean crearVenta(Venta venta) {
        // Validar que el usuario existe
        if (usuarioService.buscarUsuario(venta.getUsuario().getUsername()) == null) {
            JOptionPane.showMessageDialog(null, "El usuario no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar stock suficiente para cada línea de venta
        for (LineaVenta linea : venta.getLineasVenta()) {
            Producto producto = productoService.buscarProducto(linea.getProducto().getId());
            if (producto == null) {
                JOptionPane.showMessageDialog(null, 
                    "El producto " + linea.getProducto().getNombre() + " no existe", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!productoService.hayStockSuficiente(producto.getId(), linea.getCantidad())) {
                JOptionPane.showMessageDialog(null, 
                    "Stock insuficiente para " + producto.getNombre(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Actualizar stock
        for (LineaVenta linea : venta.getLineasVenta()) {
            productoService.actualizarInventario(
                linea.getProducto().getId(), 
                -linea.getCantidad()
            );
        }

        ventas.add(venta);
        return true;
    }

    public Venta buscarVenta(int id) {
        return ventas.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Venta> buscarVentasPorUsuario(String username) {
        return ventas.stream()
                .filter(v -> v.getUsuario().getUsername().equals(username))
                .toList();
    }

    public boolean cancelarVenta(int id) {
        Venta venta = buscarVenta(id);
        if (venta == null) {
            JOptionPane.showMessageDialog(null, "Venta no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Restaurar stock
        for (LineaVenta linea : venta.getLineasVenta()) {
            productoService.actualizarInventario(
                linea.getProducto().getId(), 
                linea.getCantidad()
            );
        }

        ventas.remove(venta);
        return true;
    }

    public double calcularTotalVentas() {
        return ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();
    }

    public List<Venta> getVentas() {
        return new ArrayList<>(ventas);
    }

    public boolean validarVenta(Venta venta) {
        if (venta.getUsuario() == null) {
            JOptionPane.showMessageDialog(null, "La venta debe tener un usuario asignado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (venta.getLineasVenta() == null || venta.getLineasVenta().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La venta debe tener al menos una línea", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        for (LineaVenta linea : venta.getLineasVenta()) {
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