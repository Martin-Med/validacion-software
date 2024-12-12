package service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Producto;

public class ProductoServiceTest {
    
    private ProductoService productoService;
    private Producto productoInicial;
    
    @Before
    public void setUp() {
        productoService = new ProductoService();
        productoInicial = new Producto(1, "Laptop", 999.99, 10);
        productoService.crearProducto(productoInicial);
    }
    
    @Test
    public void testCrearProductoExistente() {
        // Arrange
        Producto productoDuplicado = new Producto(1, "Mouse", 19.99, 20);
        
        // Act
        boolean resultado = productoService.crearProducto(productoDuplicado);
        
        // Assert
        assertFalse("Debería fallar al crear un producto con ID existente", resultado);
    }
    
    @Test
    public void testBuscarProductoNoExistente() {
        // Act
        Producto productoEncontrado = productoService.buscarProducto(999);
        
        // Assert
        assertNull("Debería retornar null para un producto que no existe", productoEncontrado);
    }
    
    @Test
    public void testActualizarNombre() {
        // Arrange
        String nuevoNombre = "Laptop Gaming";
        
        // Act
        boolean resultado = productoService.actualizarNombre(1, nuevoNombre);
        Producto productoActualizado = productoService.buscarProducto(1);
        
        // Assert
        assertTrue("La actualización del nombre debería ser exitosa", resultado);
        assertEquals("El nombre debería estar actualizado", nuevoNombre, productoActualizado.getNombre());
    }
    
    @Test
    public void testActualizarPrecio() {
        // Arrange
        double nuevoPrecio = 1299.99;
        
        // Act
        boolean resultado = productoService.actualizarPrecio(1, nuevoPrecio);
        Producto productoActualizado = productoService.buscarProducto(1);
        
        // Assert
        assertTrue("La actualización del precio debería ser exitosa", resultado);
        assertEquals("El precio debería estar actualizado", nuevoPrecio, productoActualizado.getPrecio(), 0.01);
    }
    
    @Test
    public void testActualizarStock() {
        // Arrange
        int nuevoStock = 15;
        
        // Act
        boolean resultado = productoService.actualizarStock(1, nuevoStock);
        Producto productoActualizado = productoService.buscarProducto(1);
        
        // Assert
        assertTrue("La actualización del stock debería ser exitosa", resultado);
        assertEquals("El stock debería estar actualizado", nuevoStock, productoActualizado.getStock());
    }
    
    @Test
    public void testActualizarProductoNoExistente() {
        // Act & Assert
        assertFalse("Debería fallar al actualizar nombre de producto no existente", 
            productoService.actualizarNombre(999, "Nuevo Nombre"));
        assertFalse("Debería fallar al actualizar precio de producto no existente", 
            productoService.actualizarPrecio(999, 100.0));
        assertFalse("Debería fallar al actualizar stock de producto no existente", 
            productoService.actualizarStock(999, 50));
    }
    
    @Test
    public void testEliminarProducto() {
        // Act
        boolean resultado = productoService.eliminarProducto(1);
        Producto productoEliminado = productoService.buscarProducto(1);
        
        // Assert
        assertTrue("La eliminación debería ser exitosa", resultado);
        assertNull("El producto debería ser null después de eliminarlo", productoEliminado);
    }
    
    @Test
    public void testActualizarInventario() {
        // Act
        boolean resultadoIncremento = productoService.actualizarInventario(1, 5);
        boolean resultadoDecremento = productoService.actualizarInventario(1, -3);
        Producto productoActualizado = productoService.buscarProducto(1);
        
        // Assert
        assertTrue("El incremento de inventario debería ser exitoso", resultadoIncremento);
        assertTrue("El decremento de inventario debería ser exitoso", resultadoDecremento);
        assertEquals("El stock final debería ser correcto", 12, productoActualizado.getStock());
    }
    
    @Test
    public void testHayStockSuficiente() {
        // Act & Assert
        assertTrue("Debería haber stock suficiente para 5 unidades", 
            productoService.hayStockSuficiente(1, 5));
        assertFalse("No debería haber stock suficiente para 15 unidades", 
            productoService.hayStockSuficiente(1, 15));
        assertFalse("Debería fallar para un producto que no existe", 
            productoService.hayStockSuficiente(999, 1));
    }
}