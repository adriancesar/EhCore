package com.ehaqui.ehcore.api.command;

import java.lang.annotation.Annotation;

import com.ehaqui.ehcore.api.command.exception.CommandException;

import org.bukkit.command.CommandSender;

public interface CommandAnnotationProcessor {
    Class<? extends Annotation> getAnnotationClass();

    void process(CommandSender sender, CommandContext context, Annotation instance, Object[] args)
            throws CommandException;
}
