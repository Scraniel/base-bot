package com.github.scraniel.basebot.commands;

import com.github.scraniel.milliondollarsbutbot.BotUtils;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelEvent;
import sx.blah.discord.handle.obj.IChannel;

/*
 * A command that sends a message to the channel
 */
public abstract class AbstractMessageCommand extends AbstractCommand {

    IChannel currentChannel;

    public AbstractMessageCommand()
    {
        super();
    }
    public AbstractMessageCommand(IDiscordClient context)
    {
        super(context);
    }

    @Override
    boolean setUp()
    {
        // We only handle ChannelEvents here (we need to be able to get which channel to post to if none provided)
        if(!(currentEvent instanceof ChannelEvent)) {
            return false;
        }

        currentChannel = ((ChannelEvent)currentEvent).getChannel();
        return true;
    }

    @Override
    void doCommand() {
        BotUtils.sendMessage(currentChannel, getMessage());
    }

    @Override
    void cleanUp() {
        currentChannel = null;
    }

    // Must be implemented by children
    public abstract String getMessage();
}
