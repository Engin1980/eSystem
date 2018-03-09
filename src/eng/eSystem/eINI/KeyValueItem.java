/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eng.eSystem.eINI;

/**
 *
 * @author Marek
 */
class KeyValueItem extends KeyItem {
  private String value;

  public static final KeyValueItem REF = new KeyValueItem(null, null);
  
  public String getValue() {
    return value;
  }
  public KeyValueItem(String key, String value) {
    super(key);
    this.value = value;
  }

  @Override
  public String toString() {
    return "KeyValueItem{" + super.getKey() + "=" + value + '}';
  }
}
