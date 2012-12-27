package com.ehaqui.ehcore.api.command.exception;

public class LimitExceededException extends CommandException
{
    private static final long serialVersionUID = -7174356814088618010L;
    protected int             limit;
    
    public LimitExceededException(int limit)
    {
        super("Você nao pode mais criar, o limite já foi alcançado.");
        this.limit = limit;
    }
    
    public LimitExceededException(String message, int limit)
    {
        super(message);
        this.limit = limit;
    }
    
    public int getLimit()
    {
        return limit;
    }
}
