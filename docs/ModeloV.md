# Modelo V - Sistema de Punto de Venta

## 1. Portada
**Universidad [Universidad Cuahutemoc Plantel Guadalajara]**  
**Carrera [Licenciatura en ingenieria en sistemas computacionales]**  
**Materia [Validación de Software]**
**Docente [Juan Pedro Valenzuela Godoy**

**Sistema de Punto de Venta**  
**Documentación del Modelo V**

**Fecha:** 12/12/2024

## 2. Introducción
El presente documento detalla la implementación del Modelo V en el desarrollo y validación de un Sistema de Punto de Venta. El Modelo V es una extensión del modelo en cascada que enfatiza la importancia de la validación y verificación en cada etapa del desarrollo de software, estableciendo una relación directa entre las fases de desarrollo y las fases de prueba correspondientes.

## 3. Planteamiento del Problema / Análisis de Requisitos

### Problema
El sistema actual requiere una mejora en su estructura y funcionalidades básicas, implementando un sistema de pruebas robusto que garantice su correcto funcionamiento.

### Requisitos Funcionales
1. Gestión de Usuarios:
   - Crear, leer y actualizar usuarios
   - Validar usuarios existentes
   - Gestionar roles de usuario

2. Gestión de Productos:
   - Mantener inventario actualizado
   - Registrar productos
   - Actualizar precios y stock

3. Gestión de Ventas:
   - Registrar ventas
   - Calcular totales
   - Actualizar inventario automáticamente

4. Gestión de Compras:
   - Registrar compras a proveedores
   - Actualizar inventario
   - Mantener registro de proveedores

5. Generación de Reportes:
   - Reportes de ventas
   - Reportes de compras
   - Reportes de inventario

## 4. Diseño de Software

### Arquitectura
El sistema sigue una arquitectura en capas:
1. Capa de Presentación (UI)
   - Interfaz basada en JOptionPane para interacción con usuario

2. Capa de Servicios
   - Lógica de negocio
   - Validaciones
   - Gestión de operaciones

3. Capa de Modelo
   - Entidades del negocio
   - Lógica de dominio

### Patrones de Diseño
- Singleton para servicios
- DTO para transferencia de datos
- Service Layer para separación de responsabilidades

## 5. Diseño de Componentes

### Modelos
1. Usuario
   ```java
   public class Usuario {
       private int id;
       private String nombre;
       private String apellido;
       private String username;
       private String password;
       private String rol;
   }
   ```

2. Producto
   ```java
   public class Producto {
       private int id;
       private String nombre;
       private double precio;
       private int stock;
   }
   ```

### Servicios
1. UsuarioService
   - Gestión de usuarios
   - Validaciones de usuario
   - Operaciones CRUD

2. ProductoService
   - Gestión de inventario
   - Validaciones de producto
   - Control de stock

## 7. Pruebas Unitarias

### Framework de Pruebas
- JUnit para pruebas unitarias
- Mockito para simulación de dependencias

### Casos de Prueba
1. Usuario
   ```java
   @Test
   public void testCrearUsuarioExistente() {
       // Verificar que no se pueden crear usuarios duplicados
   }

   @Test
   public void testBuscarUsuarioNoExistente() {
       // Verificar manejo de usuarios no existentes
   }

   @Test
   public void testActualizarUsuario() {
       // Verificar actualización de nombre, contraseña y rol
   }
   ```

2. Producto
   ```java
   @Test
   public void testActualizarStock() {
       // Verificar gestión de inventario
   }
   ```

## 8. Pruebas de Integración

### Escenarios de Prueba
1. Flujo de Venta Completo
   - Verificar integración entre Usuario, Producto y Venta
   - Validar actualización de inventario
   - Confirmar generación de reportes

2. Flujo de Compra
   - Verificar integración con proveedores
   - Validar actualización de inventario
   - Confirmar registro en reportes

### Resultados
- Integración exitosa entre componentes
- Manejo correcto de transacciones
- Actualización consistente de datos

## 9. Conclusión
La implementación del Modelo V en el desarrollo del Sistema de Punto de Venta ha permitido:

1. Estructura Organizada:
   - Clara separación de responsabilidades
   - Código mantenible y escalable
   - Fácil integración de nuevas funcionalidades

2. Calidad del Software:
   - Pruebas exhaustivas en cada nivel
   - Detección temprana de errores
   - Mayor confiabilidad del sistema

3. Mejoras Futuras:
   - Base sólida para nuevas funcionalidades
   - Facilidad para implementar nuevos requerimientos
   - Estructura preparada para escalabilidad

El sistema cumple con los requisitos establecidos y proporciona una base sólida para futuras mejoras y expansiones.