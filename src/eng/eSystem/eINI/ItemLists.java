/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.eINI;

/**
 *
 * @author Marek Vajgl
 */
class ItemLists {

  static String getValue(ItemList lst, String key) {
    String ret = null;
    KeyValueItem it = lst.get(key, KeyValueItem.REF);
    if (it != null) {
      ret = it.getValue();
    }
    return ret;
  }

  static void setValue(ItemList lst, String key, String value) {
    int index = lst.find(key, KeyValueItem.REF);
    if (index < 0) {
      lst.add(0, new KeyValueItem(key, value));
    } else {
      lst.set(index, new KeyValueItem(key, value));
    }
  }

  static void removeValue(ItemList items, String key) {
    int index = items.find(key, KeyValueItem.REF);
    if (index >= 0)
      items.remove(index);
  }
}
