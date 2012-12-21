package com.ehaqui.ehcore.api.command.exception;

import com.ehaqui.ehcore.api.util.Messaging;


public class CommandException extends Exception
{
    public CommandException()
    {
        super();
    }
    
    public CommandException(String message)
    {
        super(Messaging.tryTranslate("ERRO: " + message));
    }
    
    public CommandException(String key, Object... replacements)
    {
        super(String.format(key, replacements));
    }
    
    public CommandException(Throwable t)
    {
        super(t);
    }
    
    private static final long serialVersionUID = 870638193072101739L;
}