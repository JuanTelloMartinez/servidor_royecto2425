package es.ieslavereda.proyecto2425_servidor.connection;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class MyDataSource {
    @Bean(name="mysqlDataSource")
    public static DataSource getMyDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/proyectoprg_2425");
        dataSource.setUser("proyecto2425");
        dataSource.setPassword("1111");
        return dataSource;
    }

    public static Connection conectarMySQL(){
        Connection connection = null;

        DataSource dataSource = getMyDataSource();

        try {
            connection = dataSource.getConnection();
            if (connection != null)
                System.out.println("Conexión establecidad correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

}
