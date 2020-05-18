package eng.eSystem.functionalInterfaces;

public interface Selector<Tsource,Tresult> {
  Tresult getValue(Tsource obj);
}
