package service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import model.LineaVenta;
import model.Producto;
import model.Usuario;
import model.Venta;

@RunWith(MockitoJUnitRunner.class)
public class VentaServiceTest {
    
    private VentaService ventaService;
    
    @Mock
    private ProductoService productoService;
    
    @Mock
    private UsuarioService usuarioService;
    
    private Usuario usuario;
    private Producto producto1;
    private Producto producto2;
    private Venta venta;
    
    @Before
    public void setUp() {
        ventaService = new VentaService(productoService, usuarioService);
        
        // Configurar datos de prueba
        usuario = new Usuario(1, "Juan", "Pérez", "jperez", "password123", "vendedor");
        producto1 = new Producto(1, "Laptop", 999.99, 10);
        producto2 = new Producto(2, "Mouse", 19.99, 50);
        
        List<LineaVenta> lineasVenta = new ArrayList<>();
        lineasVenta.add(new LineaVenta(producto1, 1, producto1.getPrecio()));
        lineasVenta.add(new LineaVenta(producto2, 2, producto2.getPrecio()));
        
        venta = new Venta(1, new Date(), usuario, lineasVenta);
        
        // Configurar comportamiento de los mocks
        when(usuarioService.buscarUsuario("jperez")).thenReturn(usuario);
        when(productoService.buscarProducto(1)).thenReturn(producto1);
        when(productoService.buscarProducto(2)).thenReturn(producto2);
        when(productoService.hayStockSuficiente(1, 1)).thenReturn(true);
        when(productoService.hayStockSuficiente(2, 2)).thenReturn(true);
    }
    
    @Test
    public void testCrearVentaExitosa() {
        // Act
        boolean resultado = ventaService.crearVenta(venta);
        
        // Assert
        assertTrue("La venta debería crearse exitosamente", resultado);
        verify(productoService).actualizarInventario(1, -1);
        verify(productoService).actualizarInventario(2, -2);
    }
    
    @Test
    public void testCrearVentaUsuarioNoExistente() {
        // Arrange
        when(usuarioService.buscarUsuario("jperez")).thenReturn(null);
        
        // Act
        boolean resultado = ventaService.crearVenta(venta);
        
        // Assert
        assertFalse("La venta no debería crearse con usuario inexistente", resultado);
        verify(productoService, never()).actualizarInventario(anyInt(), anyInt());
    }
    
    @Test
    public void testCrearVentaSinStockSuficiente() {
        // Arrange
        when(productoService.hayStockSuficiente(1, 1)).thenReturn(false);
        
        // Act
        boolean resultado = ventaService.crearVenta(venta);
        
        // Assert
        assertFalse("La venta no debería crearse sin stock suficiente", resultado);
        verify(productoService, never()).actualizarInventario(anyInt(), anyInt());
    }
    
    @Test
    public void testBuscarVentaPorId() {
        // Arrange
        ventaService.crearVenta(venta);
        
        // Act
        Venta ventaEncontrada = ventaService.buscarVenta(1);
        
        // Assert
        assertNotNull("La venta debería encontrarse", ventaEncontrada);
        assertEquals("El ID de la venta debería coincidir", 1, ventaEncontrada.getId());
    }
    
    @Test
    public void testBuscarVentasPorUsuario() {
        // Arrange
        ventaService.crearVenta(venta);
        
        // Act
        List<Venta> ventasUsuario = ventaService.buscarVentasPorUsuario("jperez");
        
        // Assert
        assertFalse("La lista de ventas no debería estar vacía", ventasUsuario.isEmpty());
        assertEquals("Debería haber una venta del usuario", 1, ventasUsuario.size());
    }
    
    @Test
    public void testCancelarVenta() {
        // Arrange
        ventaService.crearVenta(venta);
        
        // Act
        boolean resultado = ventaService.cancelarVenta(1);
        
        // Assert
        assertTrue("La venta debería cancelarse exitosamente", resultado);
        verify(productoService).actualizarInventario(1, 1);
        verify(productoService).actualizarInventario(2, 2);
    }
    
    @Test
    public void testValidarVentaValida() {
        // Act
        boolean resultado = ventaService.validarVenta(venta);
        
        // Assert
        assertTrue("La venta debería ser válida", resultado);
    }
    
    @Test
    public void testValidarVentaSinLineas() {
        // Arrange
        venta = new Venta(1, new Date(), usuario, new ArrayList<>());
        
        // Act
        boolean resultado = ventaService.validarVenta(venta);
        
        // Assert
        assertFalse("La venta no debería ser válida sin líneas", resultado);
    }
    
    @Test
    public void testCalcularTotalVentas() {
        // Arrange
        ventaService.crearVenta(venta);
        double totalEsperado = 999.99 + (19.99 * 2); // 1 laptop + 2 mouse
        
        // Act
        double totalCalculado = ventaService.calcularTotalVentas();
        
        // Assert
        assertEquals("El total calculado debería ser correcto", totalEsperado, totalCalculado, 0.01);
    }
}