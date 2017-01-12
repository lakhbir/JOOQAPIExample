/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soul;

import static com.soul.generated.tables.Customer.CUSTOMER;
import com.soul.generated.tables.records.CustomerRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.impl.DSL;

public class Main {
	
	
	private void print(String s){
		System.out.println(s);
	}
	//==========================================================================================================
	//Fetch custom bean type
	private void singleCustomBeanRecord(DSLContext create){
		print ("Single Custom Bean");
		CustomerRecord cr = create.selectFrom(CUSTOMER).where(CUSTOMER.CUSTOMER_ID.equal(1)).fetchOne();
		print("Email ID : " + cr.getEmail());
	}
	//==========================================================================================================
	//Fetch Custom bean type
	private void multipleCustomBeanRecord(DSLContext create){
		print ("\nMultiple Custom Bean");
		Result<CustomerRecord> customers = create.selectFrom(CUSTOMER).fetch();
		for(CustomerRecord customer : customers){
			print("Name : " + customer.getFirstName() + " " + customer.getLastName());
			print("Birth Date: " + customer.getBirthdate());
			print("Email : " + customer.getEmail());
			
		}
	}
	//==========================================================================================================
	//Fetch NON Custom bean type
	private void multipleGenericBeanRecord(DSLContext create){
		print ("\nMultiple Generic Bean");
		Result<Record> records = create.select().from(CUSTOMER).fetch();
		for(Record record : records){
			print("Name : " + record.get(CUSTOMER.FIRST_NAME) + " " + record.get(CUSTOMER.LAST_NAME));
			print("Birth Date: " + record.get(CUSTOMER.BIRTHDATE));
			print("Email Date: " + record.get(CUSTOMER.EMAIL));
		}
	}
	//==========================================================================================================
	public static void main(String[] args) {
				Main main = new Main();		
        String userName = "test";
        String password = "test";
        String url = "jdbc:mysql://localhost:3306/test";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
           System.out.println("Database Version Number : " + conn.getMetaData().getDatabaseMajorVersion());
					 DSLContext dsl = DSL.using(conn, SQLDialect.MYSQL);

					 main.singleCustomBeanRecord(dsl);
					 main.multipleCustomBeanRecord(dsl);
					 main.multipleGenericBeanRecord (dsl);
        } 
        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }
 //==========================================================================================================
}
