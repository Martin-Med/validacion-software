package service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.swing.JOptionPane;
import model.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {
    
    private UsuarioService usuarioService;
    
    @Mock
    private JOptionPane mockJOptionPane;
    
    @Before
    public void setUp() {
        usuarioService = new UsuarioService();
        // Crear un usuario inicial para las pruebas
        Usuario usuarioInicial = new Usuario(1, "Test", "User", "testuser", "password123", "usuario");
        usuarioService.crearUsuario(usuarioInicial);
    }
    
    @Test
    public void testCrearUsuarioExistente() {
        // Arrange
        Usuario usuarioDuplicado = new Usuario(2, "Test2", "User2", "testuser", "password456", "usuario");
        
        // Act
        boolean resultado = usuarioService.crearUsuario(usuarioDuplicado);
        
        // Assert
        assertFalse("Debería fallar al crear un usuario con username existente", resultado);
    }
    
    @Test
    public void testBuscarUsuarioNoExistente() {
        // Act
        Usuario usuarioEncontrado = usuarioService.buscarUsuario("noexiste");
        
        // Assert
        assertNull("Debería retornar null para un usuario que no existe", usuarioEncontrado);
    }
    
    @Test
    public void testActualizarNombre() {
        // Arrange
        String nuevoNombre = "NuevoNombre";
        
        // Act
        boolean resultado = usuarioService.actualizarNombre("testuser", nuevoNombre);
        Usuario usuarioActualizado = usuarioService.buscarUsuario("testuser");
        
        // Assert
        assertTrue("La actualización del nombre debería ser exitosa", resultado);
        assertEquals("El nombre debería estar actualizado", nuevoNombre, usuarioActualizado.getNombre());
    }
    
    @Test
    public void testActualizarPassword() {
        // Arrange
        String nuevaPassword = "nuevaPassword123";
        
        // Act
        boolean resultado = usuarioService.actualizarPassword("testuser", nuevaPassword);
        Usuario usuarioActualizado = usuarioService.buscarUsuario("testuser");
        
        // Assert
        assertTrue("La actualización de la contraseña debería ser exitosa", resultado);
        assertEquals("La contraseña debería estar actualizada", nuevaPassword, usuarioActualizado.getPassword());
    }
    
    @Test
    public void testActualizarRol() {
        // Arrange
        String nuevoRol = "administrador";
        
        // Act
        boolean resultado = usuarioService.actualizarRol("testuser", nuevoRol);
        Usuario usuarioActualizado = usuarioService.buscarUsuario("testuser");
        
        // Assert
        assertTrue("La actualización del rol debería ser exitosa", resultado);
        assertEquals("El rol debería estar actualizado", nuevoRol, usuarioActualizado.getRol());
    }
    
    @Test
    public void testActualizarUsuarioNoExistente() {
        // Act
        boolean resultadoNombre = usuarioService.actualizarNombre("noexiste", "NuevoNombre");
        boolean resultadoPassword = usuarioService.actualizarPassword("noexiste", "nuevaPass");
        boolean resultadoRol = usuarioService.actualizarRol("noexiste", "nuevoRol");
        
        // Assert
        assertFalse("Debería fallar al actualizar nombre de usuario no existente", resultadoNombre);
        assertFalse("Debería fallar al actualizar password de usuario no existente", resultadoPassword);
        assertFalse("Debería fallar al actualizar rol de usuario no existente", resultadoRol);
    }
}