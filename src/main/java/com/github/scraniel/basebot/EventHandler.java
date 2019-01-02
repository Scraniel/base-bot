package com.github.scraniel.basebot;

import com.github.scraniel.basebot.commands.ICommand;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * All this class will do is parse the input and route it to the applicable Command
 */
public class EventHandler {

    private Map<String, ICommand> messageCommandMap = null;
    private Map<String, ICommand> reactionCommandMap = null;
    public static final String BOT_PREFIX = "/";

    public EventHandler()
    {
        messageCommandMap = new HashMap<>();
        reactionCommandMap = new HashMap<>();
    }

    public void init(String token)
    {
        IDiscordClient discordContext = BotUtils.getBuiltDiscordClient(token);

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        discordContext.getDispatcher().registerListener(this);

        // Only login after all events are registered otherwise some may be missed.
        discordContext.login();
    }

    public void registerMessageEvent(String commandName, ICommand command)
    {
        registerEvent(messageCommandMap, BOT_PREFIX + commandName, command);
    }

    public void registerReactionEvent(String emojiName, ICommand command)
    {
        registerEvent(reactionCommandMap, emojiName, command);
    }

    private void registerEvent(Map<String, ICommand> map, String key, ICommand value)
    {
        map.put(key, value);
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
        String content = event.getMessage().getContent();

        // We only want to deal with bot commands
        if(content.startsWith(BOT_PREFIX)) {

            // Arguments to send will be following the command
            String[] splitContent = content.split(" ");
            String[] args = Arrays.copyOfRange(splitContent, 1, splitContent.length);
            String command = splitContent[0];

            if(messageCommandMap.containsKey(command)){
                messageCommandMap.get(command).run(event, args);
            }
        }
    }

    @EventSubscriber
    public void onReactionAdd(ReactionAddEvent event) {
        String emojiName = event.getReaction().getEmoji().getName();

        if(reactionCommandMap.containsKey(emojiName)){
            reactionCommandMap.get(emojiName).run(event, null);
        }
    }
}