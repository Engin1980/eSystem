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
abstract class KeyItem extends Item {
  private final String key;
  public String getKey(){
    return key;
  }

  public KeyItem(String key) {
    this.key = key;
  }
  
  @Override
  boolean isKey(String key){
    return key.equals(getKey());
  }
  
}
