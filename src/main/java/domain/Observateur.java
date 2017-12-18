package domain;

import domain.objects.IDomainObject;

public interface Observateur {
	void action(IDomainObject o);
}
