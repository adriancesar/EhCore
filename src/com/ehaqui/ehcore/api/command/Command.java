package com.ehaqui.ehcore.api.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Command
{
    /**
     * A list of aliases for the command. The first alias is the most
     * important -- it is the main name of the command. (The method name
     * is never used for anything).
     *
     * @return Aliases for a command
     */
    String[] aliases();
    
    /**
     * A modifier field that is used for determining between two commands of the
     * same command label. The modifier determines which command to use based on
     * the first command argument.
     */
    String[] modifiers()    default "";
    
    /**
     * @return A short description for the command.
     */
    String desc();
    
    /**
     * Usage instruction. Example text for usage could be
     * <code>[-h harps] [name] [message]</code>.
     *
     * @return Usage instructions for a command
     */
    String usage()          default "";
    
    /**
     * Flags allow special processing for flags such as -h in the command,
     * allowing users to easily turn on a flag. This is a string with
     * each character being a flag. Use A-Z and a-z as possible flags.
     * Appending a flag with a : makes the flag character before a value flag,
     * meaning that if it is given it must have a value
     *
     * @return Flags matching a-zA-Z
     */
    String flags()          default "";
    
    /**
     * @return A long description for the command.
     */
    String help()           default "";
    
    /**
     * The minimum number of arguments. This should be 0 or above.
     *
     * @return the minimum number of arguments
     */
    int min()               default 0;
    
    /**
     * The maximum number of arguments. Use -1 for an unlimited number
     * of arguments.
     *
     * @return the maximum number of arguments
     */
    int max()               default -1;
    
    
    /**
     * @return A permission for command.
     */
    String permission()     default "";
    

}