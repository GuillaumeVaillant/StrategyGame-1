package domain;

public interface Observable {
	void add(Observateur o);
	void notifier();
}
