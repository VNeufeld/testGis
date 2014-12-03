package com.bpcs.eml.app;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

    public static final String CMD_OPEN = "com.bpcs.eml.app.open";
    public static final String CMD_OPEN_MESSAGE = "com.bpcs.eml.app.openMessage";
    public static final String CMD_STOP_PROCESS = "com.bpcs.eml.app.stopProcess";
    
}
