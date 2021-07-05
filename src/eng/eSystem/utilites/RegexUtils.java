package eng.eSystem.utilites;

import eng.eSystem.collections.EMap;
import eng.eSystem.collections.IMap;
import eng.eSystem.exceptions.DeprecatedException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

public class RegexUtils {

  public static class RegexGroups {
    private final IMap<Integer, String> inner = new EMap<>();

    public RegexGroups(String inputText, Pattern pattern) {
      this(pattern.matcher(inputText), false);
    }

    public RegexGroups(Matcher m, boolean isAlreadyFound) {
      if (!isAlreadyFound) m.find();
      for (int i = 0; i <= m.groupCount(); i++) {
        inner.set(i, m.group(i));
      }
    }

    public RegexGroups(String inputText, String pattern) {
      this(inputText, Pattern.compile(pattern));
    }

    public boolean getBoolean(int groupIndex) {
      return tryGetBoolean(groupIndex).orElseThrow(() -> new IllegalArgumentException(sf("There is no group index %d.", groupIndex)));
    }

    public char getChar(int groupIndex) {
      return tryGetChar(groupIndex).orElseThrow(() -> new IllegalArgumentException(sf("There is no group index %d.", groupIndex)));
    }

    public double getDouble(int groupIndex) {
      return tryGetDouble(groupIndex).orElseThrow(() -> new IllegalArgumentException(sf("There is no group index %d.", groupIndex)));
    }

    public int getInt(int groupIndex) {
      return tryGetInt(groupIndex).orElseThrow(() -> new IllegalArgumentException(sf("There is no group index %d.", groupIndex)));
    }

    public long getLong(int groupIndex) {
      return tryGetLong(groupIndex).orElseThrow(() -> new IllegalArgumentException(sf("There is no group index %d.", groupIndex)));
    }

    public String getString(int groupIndex) {
      return tryGetString(groupIndex).orElseThrow(() -> new IllegalArgumentException(sf("There is no group index %d.", groupIndex)));
    }

    public Optional<Boolean> tryGetBoolean(int groupIndex) {
      try {
        return inner.tryGet(groupIndex).map(q -> Boolean.parseBoolean(q));
      } catch (Exception e) {
        throw new IllegalStateException(sf("Unable achieve value at group-index %d as char."));
      }
    }

    public Optional<Character> tryGetChar(int groupIndex) {
      try {
        return inner.tryGet(groupIndex).map(q -> {
          if (q.length() > 1)
            throw new IllegalStateException(sf("Value '%s' cannot be returned as char as it has not one-char length.", q));
          return q.charAt(0);
        });
      } catch (Exception e) {
        throw new IllegalStateException(sf("Unable achieve value at group-index %d as char."));
      }
    }

    public Optional<Double> tryGetDouble(int groupIndex) {
      try {
        return inner.tryGet(groupIndex).map(q -> Double.parseDouble(q));
      } catch (Exception e) {
        throw new IllegalStateException(sf("Unable achieve value at group-index %d as double."));
      }
    }

    public Optional<Integer> tryGetInt(int groupIndex) {
      try {
        return inner.tryGet(groupIndex).map(q -> Integer.parseInt(q));
      } catch (Exception e) {
        throw new IllegalStateException(sf("Unable achieve value at group-index %d as int."));
      }
    }

    public Optional<Long> tryGetLong(int groupIndex) {
      try {
        return inner.tryGet(groupIndex).map(q -> Long.parseLong(q));
      } catch (Exception e) {
        throw new IllegalStateException(sf("Unable achieve value at group-index %d as int."));
      }
    }

    public Optional<String> tryGetString(int groupIndex) {
      return inner.tryGet(groupIndex);
    }

  }

  public static boolean isMatch(String text, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher m = pattern.matcher(text);
    boolean ret = m.find();
    return ret;
  }

  /**
   * @see #extractGroup(String, String, int)
   */
  @Deprecated
  public static String extractGroupContent(String text, String regex, int groupIndex) {
    throw new DeprecatedException("Call 'extractGroup(...)' instead.");
  }

  public static String extractGroup(String text, String regex, int groupIndex) {
    String ret;
    try {
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(text);
      m.find();
      ret = m.group(groupIndex);
    } catch (Exception ex) {
      throw new IllegalArgumentException(
              "Unable to extract group " + groupIndex + " of pattern " + regex + " from " + text + ".", ex);
    }
    return ret;
  }

  public static String[] extractGroups(String text, String regex) {
    String[] ret;
    try {
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(text);
      m.find();
      ret = new String[m.groupCount() + 1];
      for (int i = 0; i < ret.length; i++) {
        ret[i] = m.group(i);
      }
    } catch (Exception ex) {
      throw new IllegalArgumentException(
              "Unable to extract groups of pattern " + regex + " from " + text + ".", ex);
    }
    return ret;
  }

  public static RegexGroups extractRegexGroups(String text, String regex) {
    return new RegexGroups(text, regex);
  }
}
