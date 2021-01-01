package at.spengergasse.IShop;

import at.spengergasse.IShop.domain.*;
import at.spengergasse.IShop.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class IShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(IShopApplication.class, args);
	}
}
