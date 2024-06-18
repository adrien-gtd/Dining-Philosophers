public interface ForkInterface extends java.rmi.Remote {
    public void lock() throws java.rmi.RemoteException;
    public void unlock() throws java.rmi.RemoteException;
    public boolean isFree() throws java.rmi.RemoteException;
    
    
}
