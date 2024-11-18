package myconstruccion.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    // Detalles de conexión a la base de datos
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mi_base_de_datos";
    private static final String DB_USER = "root"; // Cambia según tu usuario
    private static final String DB_PASSWORD = "ruta"; // Cambia según tu contraseña

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Conexión a la base de datos
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Consulta para validar usuario y contraseña
            String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Usuario encontrado
                response.getWriter().println("Login exitoso");
            } else {
                // Usuario no encontrado
                response.getWriter().println("Login fallido");
            }

            // Cerrar conexión
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }
}
