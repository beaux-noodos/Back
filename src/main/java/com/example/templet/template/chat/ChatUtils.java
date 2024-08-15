package com.example.templet.template.chat;

import java.util.List;

public class ChatUtils {
  public String getFirst() {
    return super.toString();
  }

  public String getLast() {
    return super.toString();
  }

  public static String getPropsForTakeTheme() {
    String themList = "";
    for (ChatTheme c : ChatTheme.values())
      themList = themList + "\"" + c.name() + c.name() + "\" ; ";

    String value =
        "Voici une lisete de theme que tu connai et ne peus ni modifier ni ajouter : "
            + themList
            + "pour chacune de ces thme ton role est de regarder si il y a une relation avec "
            + "les besoint du dumande du clien, meme just un peut. "
            + "pour chaque theme que tu trouve avoir une petite relation : "
            + "tu me renvoi exactiement la liste de ces themes sans les modifier. ";
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
    String giveAtualData = "en ce moment vous pouver voir ici les renseignement atuelle : \n";
    return "Nous some une application de d'apprentissage sui permer à n'iporte qui de pouvoir "
        + "enseigner et aussi de permetre à n'iporte qui de pouvoir appendre ce qu'ils veulent. "
        + "pour touver les ours qui pourai interesser une personne, ils pouront etre assister par "
        + "un chat inteigencce artifiiel qu'il peux demadr oe renseignement et aussi un "
        + "unteligence artificiele qui vous suggera des enseigneent qui pouron vous interesser"
        + "\n"
        + giveAtualData;
  }

  public static String askChat() {
    return "Voilà ce qui nous concerne. Ce que je te demande est de prendre en compte ces"
        + " informations et de répondre aux questions des clients que je te donne. (remarque"
        + " : si la question n'a vraiment rien à voir avec les inforation que j'ai donner,"
        + " repend polient que l'on ne t'as pas entrainer à repondre a ces question."
        + " Surtout, tu n'es apsolument pas autoriser à ajouter ou modifier des information";
  }
}
