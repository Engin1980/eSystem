package eng.eSystem.exceptions;

import eng.eSystem.EStringBuilder;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

/**
 * Used when multiple causes are causing an exception.
 */
public class MultiCauseException extends Exception {
  private static Throwable getUniversalCause(int numberOfCauses) {
    return new Exception(sf(
        "There are %d causes of this exception. Check 'getCauses()' method of this exception.",
        numberOfCauses));
  }
  private Throwable[] causes;

  public MultiCauseException(String message, Throwable[] causes) {
    super(message, getUniversalCause(causes.length));
    this.causes = causes;
  }

  public MultiCauseException(String message, Throwable cause) {
    super(message, cause);
    this.causes = new Throwable[1];
    this.causes[0] = cause;
  }

  @Override
  public synchronized Throwable getCause() {
    return super.getCause();
  }

  public Throwable[] getCauses() {
    return causes;
  }

  @Override
  public String getMessage() {
    EStringBuilder sb = new EStringBuilder();
    sb.append(super.getMessage());
    sb.append(" Inner causes: ");
    sb.appendItems(causes, "; ");
    return sb.toString();
  }
}
