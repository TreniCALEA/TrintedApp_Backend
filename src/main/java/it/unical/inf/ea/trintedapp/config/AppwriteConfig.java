package it.unical.inf.ea.trintedapp.config;

import io.appwrite.Client;
import io.appwrite.services.Account;
import io.appwrite.services.Users;

public class AppwriteConfig {

    public static final Client client = new Client("https://cloud.appwrite.io/v1", true)
            .setProject("645d4c2c39e030c6f6ba")
            .setKey("43fb629cf369eb584caa3eb93a5bb1654e9c7cc8047a326222c1f0a4f00e4688fb62f297f7e406479c129f74cae4d65f0b1d8ab4040c37914f604e5a65e89cee519495ca39d77d8693d1623f84cc0e6f84133d438300d63018151db44c22ab525b5aec715677f73d6cf2ab6e9c632899615ef9ffb8c827dde924d103b8a85174");

    public static final Users users = new Users(client);

    public static final Account account = new Account(client);

}
