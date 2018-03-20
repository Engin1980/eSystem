package eng.eSystem.utilites;

public interface Selector<Tsource,Tresult> {
  Tresult getValue(Tsource obj);
}
