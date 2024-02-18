package edu.java.bot.utils;

@SuppressWarnings("HideUtilityClassConstructor")
public class MessageUtils {
    public static String getCommandFromMessage(String message) {
        return message.split(" ")[0];
    }

    public static String getLinkFromMessage(String message) {
        return message.split(" ")[1];
    }

    public static boolean messageHasLink(String message) {
        return message.split(" ").length == 2;
    }

}
