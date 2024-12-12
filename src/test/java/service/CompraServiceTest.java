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

import model.Compra;
import model.LineaCompra;
import model.Producto;

@RunWith(MockitoJUnitRunner.class)
public class CompraServiceTest {
    
    private CompraService compraService;
    
    @Mock
    private ProductoService productoService;
    
    private Producto producto1;
    private Producto producto2;
    private Compra compra;
    
    @Before
    public void setUp() {
        compraService = new CompraService(productoService);
        
        // Configurar datos de prueba
        producto1 = new Producto(1, "Laptop", 800.00, 10);
        producto2 = new Producto(2, "Mouse", 15.00, 50);
        
        List<LineaCompra> lineasCompra = new ArrayList<>();
        lineasCompra.add(new LineaCompra(producto1, 5, 800.00));
        lineasCompra.add(new LineaCompra(producto2, 10, 15.00));
        
        compra = new Compra(1, new Date(), "Proveedor Test", lineasCompra);
        
        // Configurar comportamiento de los mocks
        when(productoService.buscarProducto(1)).thenReturn(producto1);
        when(productoService.buscarProducto(2)).thenReturn(producto2);
    }
    
    @Test
    public void testCrearCompraExitosa() {
        // Act
        boolean resultado = compraService.crearCompra(compra);
        
        // Assert
        assertTrue("La compra debería crearse exitosamente", resultado);
        verify(productoService).actualizarInventario(1, 5);
        verify(productoService).actualizarInventario(2, 10);
    }
    
    @Test
    public void testCrearCompraProductoNoExistente() {
        // Arrange
        when(productoService.buscarProducto(1)).thenReturn(null);
        
        // Act
        boolean resultado = compraService.crearCompra(compra);
        
        // Assert
        assertFalse("La compra no debería crearse con producto inexistente", resultado);
        verify(productoService, never()).actualizarInventario(anyInt(), anyInt());
    }
    
    @Test
    public void testBuscarCompraPorId() {
        // Arrange
        compraService.crearCompra(compra);
        
        // Act
        Compra compraEncontrada = compraService.buscarCompra(1);
        
        // Assert
        assertNotNull("La compra debería encontrarse", compraEncontrada);
        assertEquals("El ID de la compra debería coincidir", 1, compraEncontrada.getId());
    }
    
    @Test
    public void testBuscarComprasPorProveedor() {
        // Arrange
        compraService.crearCompra(compra);
        
        // Act
        List<Compra> comprasProveedor = compraService.buscarComprasPorProveedor("Proveedor Test");
        
        // Assert
        assertFalse("La lista de compras no debería estar vacía", comprasProveedor.isEmpty());
        assertEquals("Debería haber una compra del proveedor", 1, comprasProveedor.size());
    }
    
    @Test
    public void testCancelarCompra() {
        // Arrange
        compraService.crearCompra(compra);
        
        // Act
        boolean resultado = compraService.cancelarCompra(1);
        
        // Assert
        assertTrue("La compra debería cancelarse exitosamente", resultado);
        verify(productoService).actualizarInventario(1, -5);
        verify(productoService).actualizarInventario(2, -10);
    }
    
    @Test
    public void testValidarCompraValida() {
        // Act
        boolean resultado = compraService.validarCompra(compra);
        
        // Assert
        assertTrue("La compra debería ser válida", resultado);
    }
    
    @Test
    public void testValidarCompraSinLineas() {
        // Arrange
        compra = new Compra(1, new Date(), "Proveedor Test", new ArrayList<>());
        
        // Act
        boolean resultado = compraService.validarCompra(compra);
        
        // Assert
        assertFalse("La compra no debería ser válida sin líneas", resultado);
    }
    
    @Test
    public void testValidarCompraSinProveedor() {
        // Arrange
        compra = new Compra(1, new Date(), "", compra.getLineasCompra());
        
        // Act
        boolean resultado = compraService.validarCompra(compra);
        
        // Assert
        assertFalse("La compra no debería ser válida sin proveedor", resultado);
    }
    
    @Test
    public void testCalcularTotalCompras() {
        // Arrange
        compraService.crearCompra(compra);
        double totalEsperado = (5 * 800.00) + (10 * 15.00); // 5 laptops + 10 mouse
        
        // Act
        double totalCalculado = compraService.calcularTotalCompras();
        
        // Assert
        assertEquals("El total calculado debería ser correcto", totalEsperado, totalCalculado, 0.01);
    }
}