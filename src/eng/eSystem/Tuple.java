package eng.eSystem;

public class Tuple<Ta, Tb> {
  private Ta a;
  private Tb b;

  public Tuple(Ta a, Tb b) {
    this.a = a;
    this.b = b;
  }

  public Tuple() {
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
}
