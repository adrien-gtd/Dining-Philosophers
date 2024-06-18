public class Main {

    public static void main (String [] args) {
        Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[philosophers.length];

        for (int i = 0; i < forks.length; i ++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < philosophers.length; i++) {
            if (i != philosophers.length) {
                philosophers[i] = new Philosopher(forks[i], forks[(i+1) % philosophers.length]);
            } else {
                philosophers[i] = new Philosopher(forks[(i+1) % philosophers.length], forks[i]);
            }
            Thread t = new Thread(philosophers[i], "Philosopher " + (i+1) );
            t.start();
        }
    }
}
