package com.github.scraniel.basebot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BotUtils {

    // Handles the creation and getting of a IDiscordClient object for a token
    static IDiscordClient getBuiltDiscordClient(String token){

        // The ClientBuilder object is where you will attach your params for configuring the instance of your bot.
        // Such as withToken, setDaemon etc
        return new ClientBuilder()
                .withToken(token)
                .build();
    }

    // Helper functions to make certain aspects of the bot easier to use.
    public static void sendMessage(IChannel channel, String message){

        RequestBuffer.request(() -> {
            try{
                channel.sendMessage(message);
            } catch (DiscordException e){
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    // Takes an InputStream and concatenates it all into one String
    public static String convertInputStreamToString(InputStream stream)
    {
        // Dumb stream reading
        String inputLine;
        StringBuffer stringBuffer = new StringBuffer();

        try(BufferedReader in = new BufferedReader(new InputStreamReader(stream)))
        {
            while ((inputLine = in.readLine()) != null) {
                stringBuffer.append(inputLine);
            }
        }catch (IOException e) {}

        return stringBuffer.toString();
    }
}
