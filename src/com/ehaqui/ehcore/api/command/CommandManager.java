package com.ehaqui.ehcore.api.command;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.ehaqui.ehcore.api.command.exception.CommandException;
import com.ehaqui.ehcore.api.command.exception.CommandUsageException;
import com.ehaqui.ehcore.api.command.exception.NoPermissionsException;
import com.ehaqui.ehcore.api.command.exception.ServerCommandException;
import com.ehaqui.ehcore.api.command.exception.UnhandledCommandException;
import com.ehaqui.ehcore.api.command.exception.WrappedCommandException;
import com.ehaqui.ehcore.api.util.Messages;
import com.ehaqui.ehcore.api.util.Messaging;
import com.ehaqui.ehcore.api.util.StringHelper;
import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class CommandManager
{
    private final Map<Class<? extends Annotation>, CommandAnnotationProcessor> annotationProcessors  = Maps.newHashMap();
    
    /*
     * Mapping of commands (including aliases) with a description. Root commands
     * are stored under a key of null, whereas child commands are cached under
     * their respective Method. The child map has the key of the command name
     * (one for each alias) with the method.
     */
    private final Map<String, Method>                                          commands              = new HashMap<String, Method>();
    
    // Stores the injector used to getInstance.
    private Injector                                                           injector;
    // Used to store the instances associated with a method.
    private final Map<Method, Object>                                          instances             = new HashMap<Method, Object>();
    private final ListMultimap<Method, Annotation>                             registeredAnnotations = ArrayListMultimap.create();
    
    private final Set<Method>                                                  serverCommands        = new HashSet<Method>();
    
    /**
     * 
     * Attempt to execute a command using the root {@link Command} given. A list
     * of method arguments may be used when calling the command handler method.
     * 
     * A command handler method should follow the form <code>command(CommandContext args, CommandSender sender)</code> where {@link CommandSender} can be replaced with {@link Player} to only
     * accept
     * players. The method parameters must include the method args given, if
     * any.
     * 
     * @param command
     *            The command to execute
     * @param args
     *            The arguments of the command
     * @param sender
     *            The sender of the command
     * @param methodArgs
     *            The method arguments to be used when calling the command
     *            handler
     * @throws CommandException
     *             Any exceptions caused from execution of the command
     */
    public void execute(org.bukkit.command.Command command, String[] args, CommandSender sender, Object... methodArgs) throws CommandException
    {
        // must put command into split.
        String[] newArgs = new String[args.length + 1];
        System.arraycopy(args, 0, newArgs, 1, args.length);
        newArgs[0] = command.getName().toLowerCase();
        
        Object[] newMethodArgs = new Object[methodArgs.length + 1];
        System.arraycopy(methodArgs, 0, newMethodArgs, 1, methodArgs.length);
        executeMethod(newArgs, sender, newMethodArgs);
    }
    
    // Attempt to execute a command.
    private void executeMethod(String[] args, CommandSender sender, Object[] methodArgs) throws CommandException
    {
        String cmdName = args[0];
        String modifier = args.length > 1 ? args[1] : "";
        
        Method method = commands.get(cmdName.toLowerCase() + " " + modifier.toLowerCase());
        if(method == null)
            method = commands.get(cmdName.toLowerCase() + " *");
        
        if(method == null)
            throw new UnhandledCommandException();
        
        if(!serverCommands.contains(method) && sender instanceof ConsoleCommandSender)
            throw new ServerCommandException();
        
        if(!hasPermission(method, sender))
            throw new NoPermissionsException();
        
        Command cmd = method.getAnnotation(Command.class);
        CommandContext context = new CommandContext(sender, args);
               
        if(context.argsLength() < cmd.min())
            throw new CommandUsageException(Messages.COMMAND_TOO_FEW_ARGUMENTS, getUsage(args, cmd));
        
        if(cmd.max() != -1 && context.argsLength() > cmd.max())
            throw new CommandUsageException(Messages.COMMAND_TOO_MANY_ARGUMENTS, getUsage(args, cmd));
        
        if(!cmd.flags().contains("*"))
        {
            for (char flag : context.getFlags())
                if(cmd.flags().indexOf(String.valueOf(flag)) == -1)
                    throw new CommandUsageException("Unknown flag: " + flag, getUsage(args, cmd));
        }
        
        methodArgs[0] = context;
        
        for (Annotation annotation : registeredAnnotations.get(method))
        {
            CommandAnnotationProcessor processor = annotationProcessors.get(annotation.annotationType());
            processor.process(sender, context, annotation, methodArgs);
        }
        
        Object instance = instances.get(method);
        try
        {
            method.invoke(instance, methodArgs);
        } catch (IllegalArgumentException e)
        {
            logger.log(Level.SEVERE, "Failed to execute command", e);
        } catch (IllegalAccessException e)
        {
            logger.log(Level.SEVERE, "Failed to execute command", e);
        } catch (InvocationTargetException e)
        {
            if(e.getCause() instanceof CommandException)
                throw (CommandException) e.getCause();
            throw new WrappedCommandException(e.getCause());
        }
    }
    
    /**
     * A safe version of <code>execute</code> which catches and logs all errors
     * that occur. Returns whether the command handler should print usage or
     * not.
     * 
     * @see #execute(Command, String[], CommandSender, Object...)
     * @return Whether further usage should be printed
     */
    public boolean executeSafe(org.bukkit.command.Command command, String[] args, CommandSender sender, Object... methodArgs)
    {
        try
        {
            try
            {
                execute(command, args, sender, methodArgs);
            } catch (ServerCommandException ex)
            {
                Messaging.send(sender, Messages.COMMAND_MUST_BE_INGAME);
            } catch (CommandUsageException ex)
            {
                Messaging.sendError(sender, ex.getMessage());
                Messaging.sendError(sender, ex.getUsage());
            } catch (UnhandledCommandException ex)
            {
                return false;
            } catch (WrappedCommandException ex)
            {
                throw ex.getCause();
            } catch (CommandException ex)
            {
                Messaging.sendError(sender, ex.getMessage());
            } catch (NumberFormatException ex)
            {
                Messaging.sendError(sender, Messages.COMMAND_INVALID_NUMBER);
            }
        } catch (Throwable ex)
        {
            ex.printStackTrace();
            if(sender instanceof Player)
            {
                Messaging.sendError(sender, Messages.COMMAND_REPORT_ERROR);
                Messaging.sendError(sender, ex.getClass().getName() + ": " + ex.getMessage());
            }
        }
        return true;
    }
    
    /**
     * Searches for the closest modifier using Levenshtein distance to the given
     * top level command and modifier.
     * 
     * @param command
     *            The top level command
     * @param modifier
     *            The modifier to use as the base
     * @return The closest modifier, or empty
     */
    public String getClosestCommandModifier(String command, String modifier)
    {
        int minDist = Integer.MAX_VALUE;
        command = command.toLowerCase();
        String closest = "";
        for (String cmd : commands.keySet())
        {
            String[] split = cmd.split(" ");
            if(split.length <= 1 || !split[0].equals(command))
                continue;
            int distance = StringHelper.getLevenshteinDistance(modifier, split[1]);
            if(minDist > distance)
            {
                minDist = distance;
                closest = split[1];
            }
        }
        
        return closest;
    }
    
    /**
     * Gets the {@link CommandInfo} for the given top level command and
     * modifier, or null if not found.
     * 
     * @param rootCommand
     *            The top level command
     * @param modifier
     *            The modifier (may be empty)
     * @return The command info for the command
     */
    public CommandInfo getCommand(String rootCommand, String modifier)
    {
        String joined = Joiner.on(' ').join(rootCommand, modifier);
        for (Entry<String, Method> entry : commands.entrySet())
        {
            if(!entry.getKey().equalsIgnoreCase(joined))
                continue;
            Command commandAnnotation = entry.getValue().getAnnotation(Command.class);
            if(commandAnnotation == null)
                continue;
            return new CommandInfo(commandAnnotation);
        }
        return null;
    }
    
    /**
     * Gets all modified and root commands from the given root level command.
     * For example, if <code>/npc look</code> and <code>/npc jump</code> were
     * defined, calling <code>getCommands("npc")</code> would return {@link CommandInfo}s for both commands.
     * 
     * @param command
     *            The root level command
     * @return The list of {@link CommandInfo}s
     */
    public List<CommandInfo> getCommands(String command)
    {
        List<CommandInfo> cmds = Lists.newArrayList();
        command = command.toLowerCase();
        for (Entry<String, Method> entry : commands.entrySet())
        {
            if(!entry.getKey().startsWith(command))
                continue;
            Command commandAnnotation = entry.getValue().getAnnotation(Command.class);
            if(commandAnnotation == null)
                continue;
            cmds.add(new CommandInfo(commandAnnotation));
        }
        return cmds;
    }
    
    // Get the usage string for a command.
    private String getUsage(String[] args, Command cmd)
    {
        StringBuilder command = new StringBuilder("/");
        command.append(args[0] + " ");
        // removed arbitrary positioning of flags.
        command.append(cmd.usage());
        return command.toString();
    }
    
    /**
     * Checks to see whether there is a command handler for the given command at
     * the root level. This will check aliases as well.
     * 
     * @param cmd
     *            The command to check
     * @param modifier
     *            The modifier to check (may be empty)
     * @return Whether the command is handled
     */
    public boolean hasCommand(org.bukkit.command.Command cmd, String modifier)
    {
        return commands.containsKey(cmd.getName().toLowerCase() + " " + modifier.toLowerCase()) || commands.containsKey(cmd.getName().toLowerCase() + " *");
    }
    
    // Returns whether a CommandSenders has permission.
    private boolean hasPermission(CommandSender sender, String perm)
    {
        return sender.hasPermission("citizens." + perm);
    }
    
    // Returns whether a player has access to a command.
    private boolean hasPermission(Method method, CommandSender sender)
    {
        Command cmd = method.getAnnotation(Command.class);
        if(cmd.permission().isEmpty() || hasPermission(sender, cmd.permission()) || hasPermission(sender, "admin"))
            return true;
        
        return false;
    }
    
    /**
     * Register a class that contains commands (methods annotated with {@link Command}). If no dependency {@link Injector} is specified, then
     * only static methods of the class will be registered. Otherwise, new
     * instances the command class will be created and instance methods will be
     * called.
     * 
     * @see #setInjector(Injector)
     * @param clazz
     *            The class to scan
     */
    public void register(Class<?> clazz)
    {
        registerMethods(clazz, null);
    }
    
    /**
     * Registers an {@link CommandAnnotationProcessor} that can process
     * annotations before a command is executed.
     * 
     * Methods with the {@link Command} annotation will have the rest of their
     * annotations scanned and stored if there is a matching {@link CommandAnnotationProcessor}. Annotations that do not have a
     * processor are discarded. The scanning method uses annotations from the
     * declaring class as a base before narrowing using the method's
     * annotations.
     * 
     * @param processor
     *            The annotation processor
     */
    public void registerAnnotationProcessor(CommandAnnotationProcessor processor)
    {
        annotationProcessors.put(processor.getAnnotationClass(), processor);
    }
    
    /*
     * Register the methods of a class. This will automatically construct
     * instances as necessary.
     */
    private void registerMethods(Class<?> clazz, Method parent)
    {
        Object obj = injector != null ? injector.getInstance(clazz) : null;
        registerMethods(clazz, parent, obj);
    }
    
    // Register the methods of a class.
    private void registerMethods(Class<?> clazz, Method parent, Object obj)
    {
        for (Method method : clazz.getMethods())
        {
            if(!method.isAnnotationPresent(Command.class))
                continue;
            boolean isStatic = Modifier.isStatic(method.getModifiers());
            
            Command cmd = method.getAnnotation(Command.class);
            
            // Cache the aliases too
            for (String alias : cmd.aliases())
            {
                for (String modifier : cmd.modifiers())
                {
                    commands.put(alias + " " + modifier, method);
                }
            }
            
            List<Annotation> annotations = Lists.newArrayList();
            for (Annotation annotation : method.getDeclaringClass().getAnnotations())
            {
                Class<? extends Annotation> annotationClass = annotation.annotationType();
                if(annotationProcessors.containsKey(annotationClass))
                    annotations.add(annotation);
            }
            for (Annotation annotation : method.getAnnotations())
            {
                Class<? extends Annotation> annotationClass = annotation.annotationType();
                if(!annotationProcessors.containsKey(annotationClass))
                    continue;
                Iterator<Annotation> itr = annotations.iterator();
                while (itr.hasNext())
                {
                    Annotation previous = itr.next();
                    if(previous.annotationType() == annotationClass)
                    {
                        itr.remove();
                    }
                }
                annotations.add(annotation);
            }
            
            if(annotations.size() > 0)
                registeredAnnotations.putAll(method, annotations);
            
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length <= 1 || parameterTypes[1] == CommandSender.class)
                serverCommands.add(method);
            
            // We want to be able invoke with an instance
            if(!isStatic)
            {
                // Can't register this command if we don't have an instance
                if(obj == null)
                    continue;
                
                instances.put(method, obj);
            }
        }
    }
    
    public void setInjector(Injector injector)
    {
        this.injector = injector;
    }
    
    public static class CommandInfo
    {
        private final Command commandAnnotation;
        
        public CommandInfo(Command commandAnnotation)
        {
            this.commandAnnotation = commandAnnotation;
        }
        
        @Override
        public boolean equals(Object obj)
        {
            if(this == obj)
            {
                return true;
            }
            if(obj == null || getClass() != obj.getClass())
            {
                return false;
            }
            CommandInfo other = (CommandInfo) obj;
            if(commandAnnotation == null)
            {
                if(other.commandAnnotation != null)
                {
                    return false;
                }
            }
            else if(!commandAnnotation.equals(other.commandAnnotation))
            {
                return false;
            }
            return true;
        }
        
        public Command getCommandAnnotation()
        {
            return commandAnnotation;
        }
        
        @Override
        public int hashCode()
        {
            return 31 + ((commandAnnotation == null) ? 0 : commandAnnotation.hashCode());
        }
    }
    
    // Logger for general errors.
    private static final Logger logger = Logger.getLogger(CommandManager.class.getCanonicalName());
}