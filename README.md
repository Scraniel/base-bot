# base-bot
Intended to be a jumping point for simple command based bots. The main idea is that you won't have to deal with setting up a command map or hooking up the discord events directly. This isn't meant to completely remove the need for [discord4j](https://discord4j.com/), but does let you focus on creating commands for your bot instead of integrating the bot with discord. 

## How to integrate into your project

### 1. Maven dependencies
First step is to set up the maven dependency. Add the following to the repositories and dependencies sections in your pom.xml:

``` XML
<!-- this github repo -->
<repositories>
    <repository>
        <id>base-bot-mvn-repo</id>
        <url>https://raw.github.com/Scraniel/base-bot/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>

<!-- the current available jar -->
<dependencies>
    <dependency>
        <groupId>com.github.scraniel</groupId>
        <artifactId>base-bot</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies
```

As mentioned above, you will also need the discord4j repo. This is because in order to implement any of the commands, you need at minimum use of the IDiscordClient object. This is done because if you want to do anything that requires knowledge of the discord server (for example, the channels, users, etc.), you'll need this IDiscordClient object. To include discord4j:

``` XML
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.austinv11</groupId>
        <artifactId>Discord4J</artifactId>
        <version>2.10.1</version>
    </dependency>
</dependencies
```

### 2. Basic code structures
Now for the fun part, integrating the base bot into your code!

#### EventHandler
This is the thing that controls which events actually get run. All all you have to do is create it, initialize it using your discord bot token (if you do not know how to get this, follow the steps found [here](https://github.com/reactiflux/discord-irc/wiki/Creating-a-discord-bot-&-getting-a-token)), and tell it which events you want to run. A simple example for a command that will be invoked when someone types `/joke` in the chat:

``` Java
package com.github.scraniel.example;

// The event handler from this repo!
import com.github.scraniel.basebot.EventHandler;

// This is an imaginary implementation of an AbstractMessageCommand
import com.github.scraniel.example.commands.JokeMessageCommand;

public class MainRunner {

    public static void main(String[] args){

        if(args == null || args.length != 1){
            System.out.println("Please enter the bots token as the first argument e.g java -jar thisjar.jar tokenhere");
            return;
        }

        EventHandler handler = new EventHandler();
        handler.init(args[0]);

        // Runs the logic in JokeMessageCommand when someone types '/joke' in chat
        handler.registerMessageCommand("joke", new JokeMessageCommand());
    }
}
```

Simply register all the commands you want and the EventHandler will take care of the rest for you.

#### ICommand and children
This is basically where you'll implement the logic of your bot. Simply extend one of the abstract children and code away. To continue our joke example above, here is what JokeMessageCommand might look like:

``` Java
package com.github.scraniel.example.commands;

import com.github.scraniel.basebot.commands.AbstractMessageCommand;
import com.github.scraniel.basebot.BotUtils;
import sx.blah.discord.api.IDiscordClient;

public class JokeMessageCommand extends AbstractMessageCommand {

    public JokeMessageCommand(IDiscordClient context)
    {
        super(context);
    }

    public JokeMessageCommand()
    {
        super();
    }

    @Override
    public String getMessage() {
        // All credit for this joke goes to Steven Wright 
        String joke = "It's a small world, but I wouldn't want to have to paint it.";

        return joke;
    }
}
```

Boom, simple as that. Right now there are only a couple of available commands (namely message commands and reaction commands), but I'll be working on adding more. 

#### Running and using the bot
Run `java -jar thisjar.jar <discordbottoken>` and you're off to the races. To interact with the bot in chat, the message commands use `/command` to call the command, and the reaction commands work based on the name of a given emoji, without colons.
