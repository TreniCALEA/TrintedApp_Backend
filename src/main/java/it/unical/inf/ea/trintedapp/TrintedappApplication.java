package it.unical.inf.ea.trintedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.appwrite.Client;

@SpringBootApplication
public class TrintedappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrintedappApplication.class, args);
		Client client = new Client()
							.setEndpoint("https://localhost/v1")
							.setProject("644e3d16419ad266eab9")
							.setKey("7dd57e524e88dc24c574314e0337705cc9dd8aff07c3ad5d2c93935d7e984489fcd2dd8dcde8c7336b4c640603eb279e20883542821cdf54ba474e51aee2b84a65ee48ae49a97d38267ebd5a5f4a520932ac7ae493b23796f820c2414039e3e11b02bfe3567f2a9c539b1f1de468942d9a5ab03498d3a35f420cfa06a4a49700")
							.setSelfSigned(true);
	}

}
