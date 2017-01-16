/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soul;


import static com.soul.generated.Tables.ADDRESS;
import static com.soul.generated.tables.Customer.CUSTOMER;
import com.soul.generated.tables.records.AddressRecord;
import com.soul.generated.tables.records.CustomerRecord;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import static org.jooq.impl.DSL.row;



public class Main {
	
	
	private void print(String s){
		System.out.println(s);
	}
	//== SELECT ================================================================================================
	//Fetch custom bean type
	private void singleCustomBeanRecord(DSLContext create){
		print ("Single Custom Bean");
		CustomerRecord cr = create.selectFrom(CUSTOMER).where(CUSTOMER.CUSTOMER_ID.equal(1)).fetchOne();
		print("Email ID : " + cr.getEmail());
	}
	//----------------------------------------------------------------------------------------------------------
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
	//----------------------------------------------------------------------------------------------------------
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
	//----------------------------------------------------------------------------------------------------------
	//Join table and Fetch
	private void joinGenericBeanRecord(DSLContext create){
		print ("\nMultiple Join Generic Bean");
		Result<Record> records = 
						create.select().from(CUSTOMER).
										join(ADDRESS).on(CUSTOMER.CUSTOMER_ID.equal(ADDRESS.CUSTOMER_ID)).
										fetch();
		
		print ("\nAll Records");
		for(Record record : records)	print (record.toString());
		
		Result<CustomerRecord> customers = records.into(CUSTOMER);
		Result<AddressRecord> addresses = records.into(ADDRESS);
		
		print ("\nCustomers");
		for(CustomerRecord customer : customers) print (customer.toString());
		
		print ("\nAddress");
		for(AddressRecord address : addresses) print (address.toString());
		
	}
	//== UPDATE ================================================================================================
	private void simpleUpdate(DSLContext create){
		print ("\nUpdating Customer Email.");
		int rowsUpdate = create.update(CUSTOMER)
						.set(CUSTOMER.EMAIL,"Jack6@yahoo.com")
						.set(CUSTOMER.LAST_NAME,"Falafal")
						.where(CUSTOMER.CUSTOMER_ID.equal(223))
						.execute();
		print(rowsUpdate + " rows Updated");
	}
	//----------------------------------------------------------------------------------------------------------
	private void simpleSubQueryUpdate(DSLContext create){
		print ("\nUpdating Customer Last Name.");
		int rowsUpdate = create.update(CUSTOMER)
						.set(CUSTOMER.LAST_NAME,
									create.select(ADDRESS.CITY)
													.from(ADDRESS)
													.where(ADDRESS.CUSTOMER_ID.equal(CUSTOMER.CUSTOMER_ID))
									)
						.where(CUSTOMER.CUSTOMER_ID.in(29))
						.execute();
		print(rowsUpdate + " rows Updated");
	}
	//----------------------------------------------------------------------------------------------------------
	//Row Level Update - NOt SUPPORTED IN MYSQL
	private void rowLevelUpdate(DSLContext create){
		print ("\nUpdating Customer Email.");
		int rowsUpdate = create.update(CUSTOMER)
						.set(row(CUSTOMER.EMAIL,CUSTOMER.LAST_NAME),
								 row("Singh@yahoo.com","ChangedTOSIngh")
						)		
						.where(CUSTOMER.CUSTOMER_ID.equal(223))
						.execute();
		print(rowsUpdate + " rows Updated");
	}
	//== INSERT ================================================================================================
	private void singleInsert(DSLContext create){
			print ("\nInserting Customer.");
		create.insertInto(CUSTOMER,CUSTOMER.FIRST_NAME,CUSTOMER.LAST_NAME,CUSTOMER.EMAIL,CUSTOMER.BIRTHDATE)
						.values("Sue","Pan","sue@yahoo.com",Date.valueOf("1987-09-18"))
						.execute();
	}	
	//----------------------------------------------------------------------------------------------------------
	private void multipleInsert(DSLContext create){
			print ("\nInserting Multiple Customers.");
			create.insertInto(CUSTOMER,CUSTOMER.FIRST_NAME,CUSTOMER.LAST_NAME,CUSTOMER.EMAIL,CUSTOMER.BIRTHDATE)
						.values("Soniuo","Pan","3@yahoo.com",Date.valueOf("1987-09-18"))
						.values("Oue","Pani","2@yahoo.com",Date.valueOf("1957-09-01"))
						.values("Pue","Jan","1@yahoo.com",Date.valueOf("1937-02-18"))
						.execute();
	}	
	//----------------------------------------------------------------------------------------------------------
	private void alternativeMultipleInsert(DSLContext create){
			print ("\nInserting Multiple Customers.");
			create.insertInto(CUSTOMER)
							.set(CUSTOMER.FIRST_NAME,"F1")
							.set(CUSTOMER.LAST_NAME,"L1")
							.set(CUSTOMER.EMAIL,"yaho@gamil.com")
							.set(CUSTOMER.BIRTHDATE,Date.valueOf("1957-09-01"))
							.newRecord()
							.set(CUSTOMER.FIRST_NAME,"F2")
							.set(CUSTOMER.LAST_NAME,"L3")
							.set(CUSTOMER.EMAIL,"yahoo1@gamil.com")
							.set(CUSTOMER.BIRTHDATE,Date.valueOf("1905-09-01"))
							.execute();
	}	
	//==========================================================================================================
	private void insertOrUpdate(DSLContext create){
			print ("\nInserting Multiple Customers.");
			create.insertInto(CUSTOMER,CUSTOMER.CUSTOMER_ID,CUSTOMER.FIRST_NAME,CUSTOMER.LAST_NAME,CUSTOMER.EMAIL,CUSTOMER.BIRTHDATE)
						.values(1,"Soniuo","Pan","3@yahoo.com",Date.valueOf("1987-09-18"))
						.onDuplicateKeyUpdate()
							.set(CUSTOMER.FIRST_NAME,"F2")
							.set(CUSTOMER.LAST_NAME,"L3")
							.set(CUSTOMER.EMAIL,"yahoo2@gamil.com")
							.set(CUSTOMER.BIRTHDATE,Date.valueOf("1905-09-01"))
						.execute();
	}
	//==========================================================================================================
	private void insertOrIgnore(DSLContext create){
			print ("\nInserting Multiple Customers.");
			create.insertInto(CUSTOMER,CUSTOMER.CUSTOMER_ID,CUSTOMER.FIRST_NAME,CUSTOMER.LAST_NAME,CUSTOMER.EMAIL,CUSTOMER.BIRTHDATE)
						.values(1,"Soniuo","Pan","3@yahoo.com",Date.valueOf("1987-09-18"))
						.onDuplicateKeyIgnore()
						.execute();
	}
	//==========================================================================================================
	private void select(DSLContext dsl){
		//=== SELECT Statements
		//singleCustomBeanRecord(dsl);
		//multipleCustomBeanRecord(dsl);
		//multipleGenericBeanRecord (dsl);
		//joinGenericBeanRecord(dsl);
	}
	private void update(DSLContext dsl){
		//=== UPDATE Statements
		//simpleUpdate(dsl);
		simpleSubQueryUpdate(dsl);
		//rowLevelUpdate(dsl); //NOt SUPPORTED IN MYSQL
	}
	private void insert(DSLContext dsl){
		//=== INSERT Statements
		//singleInsert(dsl);
		//multipleInsert(dsl);
		//alternativeMultipleInsert(dsl);
		//insertOrUpdate(dsl);
		//insertOrIgnore(dsl);
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
					
					main.select(dsl);
					main.update(dsl);
					main.insert(dsl);
					
        } 
        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
    }
 //==========================================================================================================
}
