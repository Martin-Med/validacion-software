import java.util.Date;
import java.util.List;

public class Venta {
    private int id;
    private Date fecha;
    private Usuario usuario;
    private List<LineaVenta> lineasVenta;
    private double total;

    public Venta(int id, Date fecha, Usuario usuario, List<LineaVenta> lineasVenta) {
        this.id = id;
        this.fecha = fecha;
        this.usuario = usuario;
        this.lineasVenta = lineasVenta;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        return lineasVenta.stream().mapToDouble(LineaVenta::getSubtotal).sum();
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<LineaVenta> getLineasVenta() { return lineasVenta; }
    public void setLineasVenta(List<LineaVenta> lineasVenta) {
        this.lineasVenta = lineasVenta;
        this.total = calcularTotal();
    }
    public double getTotal() { return total; }
}

class LineaVenta {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public LineaVenta(Producto producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    // Getters y setters
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}