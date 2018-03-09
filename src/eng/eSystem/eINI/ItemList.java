/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.eINI;

import java.util.ArrayList;

/**
 *
 * @author Marek
 */
class ItemList extends ArrayList<Item> {
  
  public <T extends Item> int find(String key, T ref){
    int ret = -1;
    for (int i = 0; i < this.size(); i++) {
      Item it = this.get(i);
      if (is(it, key, ref)){
        ret = i;
        break;
      }
    }
    return ret;
  }
  
  <T extends Item> T get (String key, T ref){
    int index = find(key, ref);
    if (index < 0) return null;
    else return (T) this.get(index);
  }
  
  private static <T extends Item> boolean is(Item it, String key, T ref) {
    if (it.isKey(key) && it.getClass().equals(ref.getClass())) {
      return true;
    } else {
      return false;
    }
  }
}
