package service;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Producto;

public class ProductoService {
    private List<Producto> productos;

    public ProductoService() {
        this.productos = new ArrayList<>();
    }

    public boolean crearProducto(Producto producto) {
        // Verificar si ya existe un producto con el mismo ID
        if (productos.stream().anyMatch(p -> p.getId() == producto.getId())) {
            JOptionPane.showMessageDialog(null, "Ya existe un producto con ese ID", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        productos.add(producto);
        return true;
    }

    public Producto buscarProducto(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarNombre(int id, String nuevoNombre) {
        Producto producto = buscarProducto(id);
        if (producto == null) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        producto.setNombre(nuevoNombre);
        return true;
    }

    public boolean actualizarPrecio(int id, double nuevoPrecio) {
        if (nuevoPrecio < 0) {
            JOptionPane.showMessageDialog(null, "El precio no puede ser negativo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Producto producto = buscarProducto(id);
        if (producto == null) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        producto.setPrecio(nuevoPrecio);
        return true;
    }

    public boolean actualizarStock(int id, int nuevoStock) {
        if (nuevoStock < 0) {
            JOptionPane.showMessageDialog(null, "El stock no puede ser negativo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        Producto producto = buscarProducto(id);
        if (producto == null) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        producto.setStock(nuevoStock);
        return true;
    }

    public boolean eliminarProducto(int id) {
        Producto producto = buscarProducto(id);
        if (producto == null) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        productos.remove(producto);
        return true;
    }

    public boolean actualizarInventario(int id, int cantidad) {
        Producto producto = buscarProducto(id);
        if (producto == null) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int nuevoStock = producto.getStock() + cantidad;
        if (nuevoStock < 0) {
            JOptionPane.showMessageDialog(null, "Stock insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        producto.setStock(nuevoStock);
        return true;
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    public boolean hayStockSuficiente(int id, int cantidad) {
        Producto producto = buscarProducto(id);
        if (producto == null) {
            return false;
        }
        return producto.getStock() >= cantidad;
    }
}