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
class Comment extends Item {
  private final String text;

  public String getText() {
    return text;
  }

  public Comment(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "Comment{" + "text=" + text + '}';
  }
}
