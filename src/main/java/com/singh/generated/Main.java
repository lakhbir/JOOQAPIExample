/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.singh.generated;

import com.singh.generated.tables.Customers;
import com.singh.generated.tables.records.CustomersRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

/**
 *
 * @author tsingh
 */
public class Main {
	
	public static void main(String[] args) {
        String userName = "test";
        String password = "test";
        String url = "jdbc:mysql://localhost:3306/test";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            System.out.println("Database Version Number : " + conn.getMetaData().getDatabaseMajorVersion());
						
						DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
           //Result<CustomersRecord> result = create.select(Customers.CUSTOMERS.LAST_NAME).from(Customers).fetch();
						
						
        } 

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
