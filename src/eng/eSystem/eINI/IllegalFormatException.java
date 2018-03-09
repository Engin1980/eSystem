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
public class IllegalFormatException extends Exception {
  public IllegalFormatException(String line, String message){
    super ("Illegal INI file format at line: " + line + "; reason: " + message);
  }
}
