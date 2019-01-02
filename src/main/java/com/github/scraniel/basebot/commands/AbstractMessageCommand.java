package com.github.scraniel.basebot.commands;

import com.github.scraniel.basebot.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.ChannelEvent;
import sx.blah.discord.handle.obj.IChannel;

/*
 * A command that sends a message to the channel
 */
public abstract class AbstractMessageCommand extends AbstractCommand {

    protected IChannel currentChannel;

    public AbstractMessageCommand()
    {
        super();
    }

    @Override
    protected boolean setUp()
    {
        // We only handle ChannelEvents here (we need to be able to get which channel to post to if none provided)
        if(!(currentEvent instanceof ChannelEvent)) {
            return false;
        }

        currentChannel = ((ChannelEvent)currentEvent).getChannel();
        return true;
    }

    @Override
    protected void doCommand() {
        BotUtils.sendMessage(currentChannel, getMessage());
    }

    @Override
    protected void cleanUp() {
        currentChannel = null;
    }

    // Must be implemented by children
    public abstract String getMessage();
}
