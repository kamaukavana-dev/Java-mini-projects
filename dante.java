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












