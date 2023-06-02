package it.unical.inf.ea.trintedapp.config;

import io.appwrite.Client;
import io.appwrite.services.Users;

public class AppwriteConfig {

    public static final Client client = new Client("https://cloud.appwrite.io/v1", true)
            .setProject("645d4c2c39e030c6f6ba")
            .setKey("bd00e06e50e7ff7727e053058b678909b02db0a2fb42d27efa97d9ade9fdf6616512ad56ce6373a1a2220c55909c810adb7bc709e9277f2f9886618b17a1fdec16f3665330d6095ebdcbbb284becce80ef9020adc589321e8049fdd4c2e8e8ba1f6b400186221c0ed5eec2a9f6949795a63fce513f9bd8d8c3f7f91030035f4e");

    public static final Users users = new Users(client);

}
