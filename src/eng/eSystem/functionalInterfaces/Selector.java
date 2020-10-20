package eng.eSystem.functionalInterfaces;

public interface Selector<Tsource,Tresult> {
  Tresult select(Tsource obj);
}
