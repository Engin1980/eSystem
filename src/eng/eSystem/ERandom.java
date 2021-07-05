package eng.eSystem;

import eng.eSystem.exceptions.ApplicationException;

import java.util.Random;

import static eng.eSystem.utilites.FunctionShortcuts.sf;

/**
 * @author Marek
 */
public class ERandom extends Random {
  public int nextInt(int fromInclusive, int toExclusive) {
    int ret;
    try {
      ret = nextInt(toExclusive - fromInclusive) + fromInclusive;
    } catch (Exception ex) {
      throw new ApplicationException(sf("Unable to generate random number from %d to %d.", fromInclusive, toExclusive));
    }
    return ret;
  }

  @Override
  public int nextInt(int maximumExclusive) {
    if (maximumExclusive == 0)
      return 0;
    else
      return super.nextInt(maximumExclusive);
  }

  public double nextDouble(double fromInclusive, double toExclusive) {
    return nextDouble(toExclusive - fromInclusive) + fromInclusive;
  }

  public double nextDouble(double maximum) {
    return super.nextDouble() * maximum;
  }
}
