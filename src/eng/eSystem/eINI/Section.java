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
class Section extends KeyItem {

  static final Section REF = new Section(null);
  final ItemList items = new ItemList();

  public Section(String key) {
    super(key);
  }

  @Override
  public String toString() {
    return "Section{" + super.getKey() + '}';
  }
}
