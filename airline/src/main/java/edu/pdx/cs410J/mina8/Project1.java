package edu.pdx.cs410J.mina8;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {
  public static void main(String[] args) {
    checkArgs(args);
    if (argsCount != 8 || !validArgs()) {
      System.err.println(USAGE_GUIDE);
      System.exit(1);
    }
    System.out.println(airline + flightNumber + src + depart + dest + arrive + print + readme);
  }

  private static void checkArgs(String[] args) {
    for (String arg : args) {
      if (arg.startsWith("-")) {
        if (arg.equalsIgnoreCase("-print")) {
          print = true;
        }
        if (arg.equalsIgnoreCase("-readme")) {
          readme = true;
        }
      } else {
        try {
          argsList[argsCount] = arg;
          ++argsCount;
        } catch (ArrayIndexOutOfBoundsException e) {
          ++argsCount;
        }
      }
    }
  }

  private static boolean validDateTime(String dateTime) {
    String regex = "^(1[0-2]|0[1-9]|[1-9])/(3[01]|[12][0-9]|0[1-9]|[1-9])/[0-9]{4} (0[0-9]|1[0-9]|2[0-3]|[1-9]):([0-5][0-9])$";
    return dateTime.matches(regex);
  }

  private static boolean validArgs() {
    try {
      airline = argsList[0];
      flightNumber = Integer.parseInt(argsList[1]);
      if (argsList[2].length() != 3 || argsList[5].length() != 3) {
        throw new IllegalArgumentException("src or dest is not a three-letter code");
      }
      src = argsList[2].toUpperCase();
      dest = argsList[5].toUpperCase();
      for (int i = 0; i < 3; ++i) {
        if (!Character.isLetter(src.charAt(i)) || !Character.isLetter(dest.charAt(i))) {
          throw new IllegalArgumentException("src or dest is not a three-letter code");
        }
      }
      depart = argsList[3] + " " + argsList[4];
      arrive = argsList[6] + " " + argsList[7];
      if (!validDateTime(depart) || !validDateTime(arrive)) {
        throw new IllegalArgumentException("depart or arrive is not in correct date/time format");
      }
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  protected static int argsCount = 0;
  protected static boolean print = false;
  protected static boolean readme = false;
  protected static String[] argsList = new String[8];
  protected static String airline = "";
  protected static int flightNumber = 0;
  protected static String src = "";
  protected static String depart = "";
  protected static String dest = "";
  protected static String arrive = "";
  protected final static String USAGE_GUIDE =
          "Incorrect command line arguments. See below for usage guide:\n" +
          "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
          "\targs are (in this order):\n" +
          "\t\tairline\t\t\tThe name of the airline\n" +
          "\t\tflightNumber\tThe flight number\n" +
          "\t\tsrc\t\t\t\tThree-letter code of departure airport\n" +
          "\t\tdepart\t\t\tDeparture date and time (24-hour time)\n" +
          "\t\tdest\t\t\tThree-letter code of arrival airport\n" +
          "\t\tarrive\t\t\tArrival date and time (24-hour time)\n" +
          "\toptions are (options may appear in any order):\n" +
          "\t\t-print\t\t\tPrints a description of the new flight\n" +
          "\t\t-README\t\t\tPrints a README for this project and exits\n" +
          "\tDate and time should be in the format: mm/dd/yyyy hh:mm";
}