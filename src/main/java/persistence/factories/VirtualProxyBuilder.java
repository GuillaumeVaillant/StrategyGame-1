package persistence.factories;

import java.lang.reflect.*;

public class VirtualProxyBuilder<T> implements InvocationHandler  {

	T realObject = null; 
	Factory<T> factory; 
	Class<?> iface; // = Personne.CLASS
	
	public VirtualProxyBuilder(Class<?> iface, Factory<T> factory) {
		this.iface = iface;
        this.factory = factory;
    }
	
	public T getProxy() {
        @SuppressWarnings("unchecked")
        T r  = (T) Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[] { iface }, this);
        return r;
    }
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	        if (realObject == null) {
	            realObject = factory.create(); 
	        }
	        return method.invoke(realObject, args);
	}

}
