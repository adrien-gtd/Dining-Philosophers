
public class Fork implements ForkInterface {
    private boolean isFree = true;

    @Override
    public synchronized void lock() throws java.rmi.RemoteException {
        isFree = false;
    }

    @Override
    public synchronized void unlock() throws java.rmi.RemoteException {
        isFree = true; 
    }

    @Override
    public synchronized boolean isFree() throws java.rmi.RemoteException {
        return isFree;
    }
    
}
