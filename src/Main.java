import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private static final String FONT_FAMILY = "Arial";
    private static final int FONT_SIZE = 14;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // Configurar el estilo de los JOptionPane
            UIManager.put("OptionPane.messageFont", new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE));
            UIManager.put("OptionPane.buttonFont", new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE));

            // Crear algunos usuarios
            Usuario vendedor = new Usuario(1, "Juan", "Pérez", "jperez", "password123", "vendedor");
            Usuario admin = new Usuario(2, "María", "Gómez", "mgomez", "admin456", "administrador");

            // Crear algunos productos
            Producto producto1 = new Producto(1, "Laptop", 999.99, 10);
            Producto producto2 = new Producto(2, "Mouse", 19.99, 50);
            Producto producto3 = new Producto(3, "Teclado", 49.99, 30);

            // Menú principal
            while (true) {
                String[] opciones = {"Realizar Venta", "Realizar Compra", "Ver Reporte", "Actualizar Inventario", "Salir"};
                int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una opción:",
                        "Sistema de Punto de Venta", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, opciones, opciones[0]);

                switch (seleccion) {
                    case 0:
                        realizarVenta(vendedor, producto1, producto2);
                        break;
                    case 1:
                        realizarCompra(producto1, producto3);
                        break;
                    case 2:
                        mostrarReporte();
                        break;
                    case 3:
                        actualizarInventario(producto1, producto2, producto3);
                        break;
                    case 4:
                        System.exit(0);
                    default:
                        return;
                }
            }
        });
    }

    private static void realizarVenta(Usuario vendedor, Producto producto1, Producto producto2) {
        List<LineaVenta> lineasVenta = new ArrayList<>();
        lineasVenta.add(new LineaVenta(producto1, 1, producto1.getPrecio()));
        lineasVenta.add(new LineaVenta(producto2, 2, producto2.getPrecio()));

        Venta venta = new Venta(1, new Date(), vendedor, lineasVenta);

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Venta Realizada\n\n");
        mensaje.append("ID: ").append(venta.getId()).append("\n");
        mensaje.append("Fecha: ").append(venta.getFecha()).append("\n");
        mensaje.append("Vendedor: ").append(venta.getUsuario().getNombre()).append(" ").append(venta.getUsuario().getApellido()).append("\n");
        mensaje.append("Total: $").append(String.format("%.2f", venta.getTotal()));

        mostrarMensaje(mensaje.toString(), "Detalles de la Venta");
    }

    private static void realizarCompra(Producto producto1, Producto producto3) {
        List<LineaCompra> lineasCompra = new ArrayList<>();
        lineasCompra.add(new LineaCompra(producto1, 5, 800.00));
        lineasCompra.add(new LineaCompra(producto3, 10, 40.00));

        Compra compra = new Compra(1, new Date(), "Proveedor TecnoSuministros", lineasCompra);

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Compra Realizada\n\n");
        mensaje.append("ID: ").append(compra.getId()).append("\n");
        mensaje.append("Fecha: ").append(compra.getFecha()).append("\n");
        mensaje.append("Proveedor: ").append(compra.getProveedor()).append("\n");
        mensaje.append("Total: $").append(String.format("%.2f", compra.getTotal()));

        mostrarMensaje(mensaje.toString(), "Detalles de la Compra");
    }

    private static void mostrarReporte() {
        List<Object> datosReporte = new ArrayList<>();
        datosReporte.add("Venta 1: $1039.97");
        datosReporte.add("Venta 2: $549.99");
        Reporte reporteVentas = new Reporte(1, "Ventas", new Date(), new Date(), datosReporte);

        mostrarMensaje(reporteVentas.generarReporte(), "Reporte de Ventas");
    }

    private static void actualizarInventario(Producto producto1, Producto producto2, Producto producto3) {
        producto1.setStock(producto1.getStock() - 1);
        producto2.setStock(producto2.getStock() - 2);

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Inventario Actualizado\n\n");
        mensaje.append(producto1.getNombre()).append(": ").append(producto1.getStock()).append(" unidades\n");
        mensaje.append(producto2.getNombre()).append(": ").append(producto2.getStock()).append(" unidades\n");
        mensaje.append(producto3.getNombre()).append(": ").append(producto3.getStock()).append(" unidades");

        mostrarMensaje(mensaje.toString(), "Inventario Actualizado");
    }

    private static void mostrarMensaje(String mensaje, String titulo) {
        JTextArea textArea = new JTextArea(mensaje);
        textArea.setFont(new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE));
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.PLAIN_MESSAGE);
    }
}