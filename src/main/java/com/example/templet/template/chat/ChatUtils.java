package com.example.templet.template.chat;

import com.example.templet.template.chat.DBChat.ChatTheme;
import com.example.templet.template.chat.DBChat.GlobalThemStructure;
import com.example.templet.template.chat.DBChat.IsInChat;
import com.example.templet.template.chat.DBChat.IsInChatService;
import java.util.List;

public class ChatUtils {
  public String getFirst() {
    return super.toString();
  }

  public String getLast() {
    return super.toString();
  }

  public static String getPropsForTakeTheme() {
    String themeList = "";
    for (ChatTheme c : ChatTheme.values())
      themeList = themeList + "\"" + c.name() + c.name() + "\" ; ";

    String value =
        "Voici une liste de thèmes que tu connais et que tu ne peux ni modifier ni ajouter : "
            + themeList
            + ". Pour chacun de ces thèmes, ton rôle est de vérifier s'il y a un lien, même ténu,"
            + " avec les besoins du client. Pour chaque thème que tu trouves pertinent, tu me"
            + " renverras exactement cette liste de thèmes, sans aucune modification.";
    return value;
  }

  public static String generateApropriatePropsTheme(
      String theme, List<GlobalThemStructure> globalThemStructures) {
    String value = "";
    for (GlobalThemStructure globalThemStructure : globalThemStructures) {
      ChatTheme chatTheme = globalThemStructure.getChatTheme();
      IsInChatService isInChatService = globalThemStructure.getIsInChatService();
      List<IsInChat> isInChats = isInChatService.getIsInChatToConsider();
      if (("---" + theme.toUpperCase() + "---")
              .split(chatTheme.name().toUpperCase() + chatTheme.name().toUpperCase())
              .length
          > 1) {
        value += IsInChat.generateThemeData(isInChats);
        value += "\n";
      }
    }
    return value;
  }

  public static String generateAapliationdefinition() {
    String giveActualData = "en ce moment vous pouvez voir ici les renseignements actuelle : \n";
    return "Notre plateforme permet aux investisseurs de trouver des projets à financer"
        + "et aux particuliers de trouver des solutions techniques ou des financements"
        + "pour leurs projets. Elle offre la possibilité aux particuliers de"
        + "rechercher des solutions techniques ou des investisseurs en fonction"
        + "de leurs préférences, ou de bénéficier d'un accompagnement personnalisé "
        + "grâce à 3 intelligence artificielle: une IA pour generer des"
        + "suggestions,une IA pour faciliter la rechrche des projets dans la"
        + "base de donnee, une IA pour generer des solutions techniques"
        + "\n"
        + giveActualData;
  }

  public static String askChat() {
    return "Voici les informations à prendre en compte. Réponds aux"
        + "questions des clients en t'appuyant sur ces données."
        + "Si une question ne concerne pas ces informations, réponds poliment que"
        + "tu n'as pas été programmé pour y répondre. Surtout,"
        + "tu n'es absolument pas autoriser à ajouter ou modifier des informations.";
  }
}
