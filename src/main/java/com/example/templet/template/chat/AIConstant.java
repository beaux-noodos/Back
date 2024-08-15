package com.example.templet.template.chat;

public class AIConstant {
  // long max = 2⁶64 = Plage de valeurs: de -2^63 à 2^63-1 (environ -9 200 000 000 000 000 000 à 9.2
  // quintillions)
  public static final long BASE_VIEW_POINT_RATE = 25;
  public static final long BASE_STARS_POINT_RATE = 30;
  public static final long BASE_LIKE_POINT_RATE = 45;

  // the total or point of point by React cant by except that
  public static final long REACTION_POINT_MAX = 1_000_000_000_000L;

  // the total or point of point by user profile cant by except that
  public static final long SELF_POINT_COEFFICIENT_MAX = 500;

  // the total or point of point by what the user vew cant by except that
  public static final long SELF_VEW_POINT_COEFFICIENT_MAX = 1_000;

  // Use to make down the over problem of the long
  public static final long NOT_OVER_FOLL_COEFFICIENT = 1_000_000;
}
