package com.example.banque_service;

import com.example.banque_service.Entities.Compte;
import com.example.banque_service.Entities.Transaction;
import com.example.banque_service.Entities.TypeCompte;
import com.example.banque_service.Entities.TypeTransaction;
import com.example.banque_service.Repositories.CompteRepository;
import com.example.banque_service.Repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class BanqueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanqueServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CompteRepository compteRepository, TransactionRepository transactionRepository) {
		return args -> {
			// Créer quelques comptes de test
			Compte c1 = compteRepository.save(new Compte(null, 5000.0, new Date(), TypeCompte.COURANT, null));
			Compte c2 = compteRepository.save(new Compte(null, 12000.0, new Date(), TypeCompte.EPARGNE, null));
			Compte c3 = compteRepository.save(new Compte(null, 3500.0, new Date(), TypeCompte.COURANT, null));

			// Créer quelques transactions de test
			transactionRepository.save(new Transaction(null, 1000.0, new Date(), TypeTransaction.DEPOT, c1));
			transactionRepository.save(new Transaction(null, 500.0, new Date(), TypeTransaction.RETRAIT, c1));
			transactionRepository.save(new Transaction(null, 2000.0, new Date(), TypeTransaction.DEPOT, c2));
			transactionRepository.save(new Transaction(null, 800.0, new Date(), TypeTransaction.DEPOT, c3));

			System.out.println("=================================");
			System.out.println("✅ Données de test insérées avec succès!");
			System.out.println("📊 Comptes créés: " + compteRepository.count());
			System.out.println("💰 Transactions créées: " + transactionRepository.count());
			System.out.println("=================================");
			System.out.println("🌐 Console H2: http://localhost:8082/h2-console");
			System.out.println("   JDBC URL: jdbc:h2:mem:banque");
			System.out.println("   Username: sa");
			System.out.println("   Password: (vide)");
			System.out.println("=================================");
			System.out.println("🚀 GraphiQL: http://localhost:8082/graphiql");
			System.out.println("=================================");
		};
	}
}
