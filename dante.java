package jdbc;

import java.sql.*;

/**
 * Full CRUD demo using JDBC and an H2 in-memory database.
 *
 * Each database operation is isolated in its own method so the
 * flow in main() reads like a plain-English description of what
 * the program does.
 *
 * Dependency: add H2 to your project pom.xml file under dependencies before running.
 *   Maven:
 *     <dependency>
 *       <groupId>com.h2database</groupId>
 *       <artifactId>h2</artifactId>
 *       <version>2.2.224</version>
 *     </dependency>
 */
public class JdbcDemo {

    static final String URL      = "jdbc:h2:mem:studentdb;DB_CLOSE_DELAY=-1";
    static final String USERNAME = "sa";
    static final String PASSWORD = "";

    // ------------------------------------------------------------------ main

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName());

            createTable(conn);
            insertStudents(conn);

            System.out.println("\n--- All students (initial) ---");
            readStudents(conn);

            updateGrade(conn, 2, 97.0);
            System.out.println("\n--- After updating student 2's grade to 97.0 ---");
            readStudents(conn);

            deleteStudent(conn, 3);
            System.out.println("\n--- After deleting student 3 ---");
            readStudents(conn);

            System.out.println("\n--- Students with grade above 85 ---");
            readStudentsAboveGrade(conn, 85.0);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    // --------------------------------------------------------- DDL: create

    static void createTable(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE students (
                    id    INT          PRIMARY KEY AUTO_INCREMENT,
                    name  VARCHAR(100) NOT NULL,
                    email VARCHAR(150) UNIQUE,
                    grade DOUBLE
                )
                """;
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
            System.out.println("Table 'students' created.");
        }
    }









