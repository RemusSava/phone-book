package com.app.phone_book;

import com.app.phone_book.migrations.DBInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PhoneBookApplication {

	@Autowired
	private DBInit dbInit;

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(PhoneBookApplication.class, args);
		PhoneBookApplication application = context.getBean(PhoneBookApplication.class);
		application.runMigrations();
	}

	public void runMigrations() {
		dbInit.runSqlScripts();
	}
}
