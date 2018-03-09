/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.eINI;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marek
 */
public class IniFile {

  private final ItemList items = new ItemList();

  public static IniFile tryLoad(String path) {
    IniFile ret;
    try {
      ret = IniFile.load(path);
    } catch (Exception ex) {
      ret = null;
    }
    return ret;
  }

  public static IniFile load(String path) throws IOException, IllegalFormatException {
    Path p = Paths.get(path);
    IniFile ret = IniFile.load(p);
    return ret;
  }

  public static IniFile load(Path filePath) throws IOException, IllegalFormatException {
    List<String> lines = java.nio.file.Files.readAllLines(filePath);

    IniFile ret = new IniFile();
    ItemList curItems = ret.items;

    for (String line : lines) {
      Item item = decodeItem(line);

      if (item instanceof Section) {
        Section sec = (Section) item;
        ret.items.add(sec);
        curItems = sec.items;
      } else {
        curItems.add(item);
      }
    }

    return ret;
  }

  public void save(String filePath) throws IOException {
    this.save(filePath, true);
  }

  public void save(String filePath, boolean preserveEmptySections) throws IOException {
    Path p = Paths.get(filePath);
    save(p, preserveEmptySections);
  }

  public void save(Path filePath) throws IOException {
    this.save(filePath, true);
  }

  public void save(Path filePath, boolean preserveEmptySections) throws IOException {
    List<String> lines = new ArrayList<>();
    String line;

    for (Item it : items) {
      if (it instanceof Section) {
        Section sec = (Section) it;
        if (preserveEmptySections == false && sec.items.isEmpty()) {
          continue; // skip empty sections
        }
        line = encodeLine(it);
        lines.add(line);
        for (Item subIt : sec.items) {
          line = encodeLine(subIt);
          lines.add(line);
        }
        lines.add("");
      } else {
        // non-section item
        line = encodeLine(it);
        lines.add(line);
      }
    }

    java.nio.file.Files.write(filePath, lines, java.nio.file.StandardOpenOption.CREATE);
  }

  public String getValue(String key) {
    return ItemLists.getValue(items, key);
  }

  public String getValue(String section, String key) {
    String ret = null;
    Section sec = getSection(section);
    if (sec != null) {
      ret = ItemLists.getValue(sec.items, key);
    }
    return ret;
  }

  public void setValue(String key, String value) {
    if (value == null) {
      throw new IllegalArgumentException("Argument \"value\" cannot be null.");
    }

    ItemLists.setValue(items, key, value);
  }

  public void setValue(String section, String key, String value) {
    if (value == null) {
      throw new IllegalArgumentException("Argument \"value\" cannot be null.");
    }

    Section sec = getAddSection(section);
    ItemLists.setValue(sec.items, key, value);
  }

  public void removeValue(String key) {
    ItemLists.removeValue(items, key);
  }

  public void removeValue(String section, String key) {
    Section sec = getSection(section);
    if (sec != null) {
      ItemLists.removeValue(sec.items, key);
    }
  }

  private Section getSection(String key) {
    Section ret = items.get(key, Section.REF);
    return ret;
  }

  private Section getAddSection(String key) {
    Section sec = getSection(key);
    if (sec == null) {
      sec = new Section(key);
      items.add(sec);
    }
    return sec;
  }

  private String encodeLine(Item it) {
    if (it instanceof Empty) {
      return "";
    } else if (it instanceof Comment) {
      return ";" + ((Comment) it).getText();
    } else if (it instanceof Section) {
      Section sec = (Section) it;
      return "[" + sec.getKey() + "]";
    } else if (it instanceof KeyValueItem) {
      KeyValueItem kvi = (KeyValueItem) it;
      return kvi.getKey() + "=" + kvi.getValue();
    } else {
      return null;
    }
  }

  private static Item decodeItem(String line) throws IllegalFormatException {
    if (line.trim().length() == 0) {
      return new Empty();
    } else if (line.startsWith(";")) {
      return new Comment(line.substring(1));
    } else if (line.startsWith("[")) {
      if (line.endsWith("]")) {
        return new Section(line.substring(1, line.length() - 1));
      } else {
        throw new IllegalFormatException(line, "Invalid section format.");
      }
    } else {
      int spl = line.indexOf("=");
      if (spl < 0) {
        throw new IllegalFormatException(line, "Unable to parse key-value.");
      }
      String key = line.substring(0, spl).trim();
      String val = line.substring(spl + 1).trim();
      return new KeyValueItem(key, val);
    }
  }
}
