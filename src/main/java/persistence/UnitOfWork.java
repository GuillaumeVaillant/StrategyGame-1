package persistence;

import java.util.HashSet;
import java.util.Set;

import domain.Observateur;
import domain.Visiteur;
import domain.objects.IDomainObject;

//Permet mémoriser les changements en base de données
public class UnitOfWork implements Observateur {
	
	Set<IDomainObject> dirty; 
	static UnitOfWork inst = null;
	
	public static UnitOfWork getInstance() {
		if (inst == null)
			inst = new UnitOfWork();
		return inst;
	}
	
	public UnitOfWork() {
		dirty = new HashSet<IDomainObject>();
	}

	public void action(IDomainObject o) {
		dirty.add(o);
		commit();
	}
	
	public void commit() {
		Visiteur v = new Committer();
		for (IDomainObject o : dirty) {
			v.visiter(o);
		}
		dirty.clear();
	}

}