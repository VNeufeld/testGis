package com.dev.gis.app;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

    public static final String CMD_OPEN = "com.dev.gis.app.open";
    public static final String CMD_OPEN_MESSAGE = "com.dev.gis.app.openMessage";
    public static final String CMD_STOP_PROCESS = "com.dev.gis.app.stopProcess";
    public static final String CMD_SWITCH_PERSPECTIVE = "com.dev.gis.app.switch";
    public static final String CMD_SWITCH_TO_APP_PERSPECTIVE = "com.dev.gis.app.switch.app";
    public static final String CMD_SWITCH_TO_SUNNY_APP_PERSPECTIVE = "com.dev.gis.app.switch.sunny.app";
    public static final String CMD_SWITCH_TO_CLUBMOBIL_PERSPECTIVE = "com.dev.gis.app.switch.clubmobil.app";
    public static final String CMD_RESET_PERSPECTIVE = "com.dev.gis.app.perspective.reset";
    public static final String CMD_SHOW_RESPONSE = "com.dev.gis.app.showResponse";
    public static final String CMD_LOGIN_CLUBMOBIL = "com.dev.gis.app.loginClubMobil";
    
}
