package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Usuario;
import model.Producto;
import service.UsuarioService;

public class Main {
    private static final String FONT_FAMILY = "Arial";
    private static final int FONT_SIZE = 14;
    private static final UsuarioService usuarioService = new UsuarioService();

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

            // Crear usuarios iniciales
            inicializarUsuarios();

            // Crear algunos productos
            Producto producto1 = new Producto(1, "Laptop", 999.99, 10);
            Producto producto2 = new Producto(2, "Mouse", 19.99, 50);
            Producto producto3 = new Producto(3, "Teclado", 49.99, 30);

            // Menú principal
            while (true) {
                String[] opciones = {
                    "Realizar Venta", 
                    "Realizar Compra", 
                    "Ver Reporte", 
                    "Actualizar Inventario", 
                    "Gestionar Usuarios",
                    "Salir"
                };
                
                int seleccion = JOptionPane.showOptionDialog(
                    null, 
                    "Seleccione una opción:",
                    "Sistema de Punto de Venta", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.PLAIN_MESSAGE,
                    null, 
                    opciones, 
                    opciones[0]
                );

                switch (seleccion) {
                    case 0:
                        realizarVenta(producto1, producto2);
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
                        gestionarUsuarios();
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        return;
                }
            }
        });
    }

    private static void inicializarUsuarios() {
        Usuario vendedor = new Usuario(1, "Juan", "Pérez", "jperez", "password123", "vendedor");
        Usuario admin = new Usuario(2, "María", "Gómez", "mgomez", "admin456", "administrador");
        
        usuarioService.crearUsuario(vendedor);
        usuarioService.crearUsuario(admin);
    }

    private static void gestionarUsuarios() {
        String[] opciones = {
            "Crear Usuario",
            "Buscar Usuario",
            "Actualizar Usuario",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(
            null,
            "Gestión de Usuarios",
            "Sistema de Punto de Venta",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        switch (seleccion) {
            case 0:
                crearNuevoUsuario();
                break;
            case 1:
                buscarUsuario();
                break;
            case 2:
                actualizarUsuario();
                break;
            default:
                return;
        }
    }

    private static void crearNuevoUsuario() {
        String username = JOptionPane.showInputDialog("Ingrese el nombre de usuario:");
        if (username == null) return;

        if (usuarioService.buscarUsuario(username) != null) {
            JOptionPane.showMessageDialog(null, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
        if (nombre == null) return;

        String apellido = JOptionPane.showInputDialog("Ingrese el apellido:");
        if (apellido == null) return;

        String password = JOptionPane.showInputDialog("Ingrese la contraseña:");
        if (password == null) return;

        String rol = JOptionPane.showInputDialog("Ingrese el rol (vendedor/administrador):");
        if (rol == null) return;

        Usuario nuevoUsuario = new Usuario(
            usuarioService.getUsuarios().size() + 1,
            nombre,
            apellido,
            username,
            password,
            rol
        );

        if (usuarioService.crearUsuario(nuevoUsuario)) {
            JOptionPane.showMessageDialog(null, "Usuario creado exitosamente");
        }
    }

    private static void buscarUsuario() {
        String username = JOptionPane.showInputDialog("Ingrese el nombre de usuario a buscar:");
        if (username == null) return;

        Usuario usuario = usuarioService.buscarUsuario(username);
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        mostrarInformacionUsuario(usuario);
    }

    private static void actualizarUsuario() {
        String username = JOptionPane.showInputDialog("Ingrese el nombre de usuario a actualizar:");
        if (username == null) return;

        Usuario usuario = usuarioService.buscarUsuario(username);
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] opciones = {
            "Actualizar Nombre",
            "Actualizar Contraseña",
            "Actualizar Rol",
            "Volver"
        };

        int seleccion = JOptionPane.showOptionDialog(
            null,
            "Seleccione qué desea actualizar",
            "Actualizar Usuario",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        switch (seleccion) {
            case 0:
                actualizarNombreUsuario(username);
                break;
            case 1:
                actualizarPasswordUsuario(username);
                break;
            case 2:
                actualizarRolUsuario(username);
                break;
            default:
                return;
        }
    }

    private static void actualizarNombreUsuario(String username) {
        String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
        if (nuevoNombre == null) return;

        if (usuarioService.actualizarNombre(username, nuevoNombre)) {
            JOptionPane.showMessageDialog(null, "Nombre actualizado exitosamente");
        }
    }

    private static void actualizarPasswordUsuario(String username) {
        String nuevaPassword = JOptionPane.showInputDialog("Ingrese la nueva contraseña:");
        if (nuevaPassword == null) return;

        if (usuarioService.actualizarPassword(username, nuevaPassword)) {
            JOptionPane.showMessageDialog(null, "Contraseña actualizada exitosamente");
        }
    }

    private static void actualizarRolUsuario(String username) {
        String nuevoRol = JOptionPane.showInputDialog("Ingrese el nuevo rol (vendedor/administrador):");
        if (nuevoRol == null) return;

        if (usuarioService.actualizarRol(username, nuevoRol)) {
            JOptionPane.showMessageDialog(null, "Rol actualizado exitosamente");
        }
    }

    private static void mostrarInformacionUsuario(Usuario usuario) {
        StringBuilder info = new StringBuilder();
        info.append("Información del Usuario:\n\n");
        info.append("ID: ").append(usuario.getId()).append("\n");
        info.append("Nombre: ").append(usuario.getNombre()).append("\n");
        info.append("Apellido: ").append(usuario.getApellido()).append("\n");
        info.append("Username: ").append(usuario.getUsername()).append("\n");
        info.append("Rol: ").append(usuario.getRol());

        mostrarMensaje(info.toString(), "Detalles del Usuario");
    }

    private static void realizarVenta(Producto producto1, Producto producto2) {
        List<LineaVenta> lineasVenta = new ArrayList<>();
        lineasVenta.add(new LineaVenta(producto1, 1, producto1.getPrecio()));
        lineasVenta.add(new LineaVenta(producto2, 2, producto2.getPrecio()));

        Usuario vendedor = usuarioService.buscarUsuario("jperez");
        Venta venta = new Venta(1, new Date(), vendedor, lineasVenta);

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Venta Realizada\n\n");
        mensaje.append("ID: ").append(venta.getId()).append("\n");
        mensaje.append("Fecha: ").append(venta.getFecha()).append("\n");
        mensaje.append("Vendedor: ").append(venta.getUsuario().getNombre())
               .append(" ").append(venta.getUsuario().getApellido()).append("\n");
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