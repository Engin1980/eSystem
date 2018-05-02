package eng.eSystem;

public class Triple<Ta, Tb, Tc> {
  private Ta a;
  private Tb b;
  private Tc c;

  public Triple(Ta a, Tb b, Tc c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public Triple() {
  }

  public Ta getA() {
    return a;
  }

  public void setA(Ta a) {
    this.a = a;
  }

  public Tb getB() {
    return b;
  }

  public void setB(Tb b) {
    this.b = b;
  }

  public Tc getC() {
    return c;
  }

  public void setC(Tc c) {
    this.c = c;
  }
}
