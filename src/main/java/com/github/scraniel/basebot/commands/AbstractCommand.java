package com.github.scraniel.basebot.commands;

import sx.blah.discord.api.events.Event;

public abstract class AbstractCommand implements ICommand{

    protected Event currentEvent;
    protected String[] currentArguments;

    public AbstractCommand()
    {
        super();
    }

    @Override
    public void run (Event event, String[] args) {
        currentEvent = event;
        currentArguments = args;

        // Only bother running the command if setUp passes
        if(setUp())
        {
            doCommand();
        }
        cleanUp();

        currentEvent = null;
        currentArguments = null;
    }


    abstract boolean setUp();
    abstract void doCommand();
    abstract void cleanUp();
}
