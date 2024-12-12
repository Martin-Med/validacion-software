package service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import model.Compra;
import model.LineaCompra;
import model.LineaVenta;
import model.Producto;
import model.Reporte;
import model.Usuario;
import model.Venta;

@RunWith(MockitoJUnitRunner.class)
public class ReporteServiceTest {
    
    private ReporteService reporteService;
    
    @Mock
    private VentaService ventaService;
    
    @Mock
    private CompraService compraService;
    
    @Mock
    private ProductoService productoService;
    
    private Date fechaInicio;
    private Date fechaFin;
    private List<Venta> ventas;
    private List<Compra> compras;
    private List<Producto> productos;
    
    @Before
    public void setUp() {
        reporteService = new ReporteService(ventaService, compraService, productoService);
        
        // Configurar fechas
        fechaInicio = new Date();
        fechaFin = new Date();
        
        // Configurar productos
        Producto producto1 = new Producto(1, "Laptop", 999.99, 10);
        Producto producto2 = new Producto(2, "Mouse", 19.99, 50);
        productos = Arrays.asList(producto1, producto2);
        
        // Configurar ventas
        Usuario vendedor = new Usuario(1, "Juan", "Pérez", "jperez", "password123", "vendedor");
        List<LineaVenta> lineasVenta = Arrays.asList(
            new LineaVenta(producto1, 1, producto1.getPrecio()),
            new LineaVenta(producto2, 2, producto2.getPrecio())
        );
        Venta venta = new Venta(1, fechaInicio, vendedor, lineasVenta);
        ventas = Arrays.asList(venta);
        
        // Configurar compras
        List<LineaCompra> lineasCompra = Arrays.asList(
            new LineaCompra(producto1, 5, 800.00),
            new LineaCompra(producto2, 10, 15.00)
        );
        Compra compra = new Compra(1, fechaInicio, "Proveedor Test", lineasCompra);
        compras = Arrays.asList(compra);
        
        // Configurar comportamiento de los mocks
        when(ventaService.getVentas()).thenReturn(ventas);
        when(compraService.getCompras()).thenReturn(compras);
        when(productoService.getProductos()).thenReturn(productos);
    }
    
    @Test
    public void testGenerarReporteVentas() {
        // Act
        Reporte reporte = reporteService.generarReporteVentas(fechaInicio, fechaFin);
        
        // Assert
        assertNotNull("El reporte no debería ser null", reporte);
        assertEquals("El tipo de reporte debería ser Ventas", "Ventas", reporte.getTipo());
        assertTrue("El reporte debería contener datos", reporte.getDatos().size() > 0);
    }
    
    @Test
    public void testGenerarReporteCompras() {
        // Act
        Reporte reporte = reporteService.generarReporteCompras(fechaInicio, fechaFin);
        
        // Assert
        assertNotNull("El reporte no debería ser null", reporte);
        assertEquals("El tipo de reporte debería ser Compras", "Compras", reporte.getTipo());
        assertTrue("El reporte debería contener datos", reporte.getDatos().size() > 0);
    }
    
    @Test
    public void testGenerarReporteInventario() {
        // Act
        Reporte reporte = reporteService.generarReporteInventario();
        
        // Assert
        assertNotNull("El reporte no debería ser null", reporte);
        assertEquals("El tipo de reporte debería ser Inventario", "Inventario", reporte.getTipo());
        assertTrue("El reporte debería contener datos", reporte.getDatos().size() > 0);
    }
    
    @Test
    public void testBuscarReportePorId() {
        // Arrange
        Reporte reporteGenerado = reporteService.generarReporteVentas(fechaInicio, fechaFin);
        
        // Act
        Reporte reporteEncontrado = reporteService.buscarReporte(reporteGenerado.getId());
        
        // Assert
        assertNotNull("El reporte debería encontrarse", reporteEncontrado);
        assertEquals("Los IDs deberían coincidir", reporteGenerado.getId(), reporteEncontrado.getId());
    }
    
    @Test
    public void testBuscarReportesPorTipo() {
        // Arrange
        reporteService.generarReporteVentas(fechaInicio, fechaFin);
        reporteService.generarReporteVentas(fechaInicio, fechaFin);
        
        // Act
        List<Reporte> reportesVentas = reporteService.buscarReportesPorTipo("Ventas");
        
        // Assert
        assertFalse("La lista de reportes no debería estar vacía", reportesVentas.isEmpty());
        assertEquals("Deberían haber dos reportes de ventas", 2, reportesVentas.size());
        assertTrue("Todos los reportes deberían ser de tipo Ventas",
            reportesVentas.stream().allMatch(r -> r.getTipo().equals("Ventas")));
    }
    
    @Test
    public void testEliminarReporte() {
        // Arrange
        Reporte reporte = reporteService.generarReporteVentas(fechaInicio, fechaFin);
        
        // Act
        boolean resultado = reporteService.eliminarReporte(reporte.getId());
        Reporte reporteEliminado = reporteService.buscarReporte(reporte.getId());
        
        // Assert
        assertTrue("La eliminación debería ser exitosa", resultado);
        assertNull("El reporte no debería existir después de eliminarlo", reporteEliminado);
    }
    
    @Test
    public void testEliminarReporteNoExistente() {
        // Act
        boolean resultado = reporteService.eliminarReporte(999);
        
        // Assert
        assertFalse("No debería ser posible eliminar un reporte que no existe", resultado);
    }
}