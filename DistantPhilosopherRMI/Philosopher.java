import java.io.BufferedWriter;

public class Philosopher implements Runnable {

    private BufferedWriter writer;
    private ForkInterface leftFork;
    private ForkInterface rightFork;
    private int countEat = 0;
    private boolean print;

    public Philosopher (ForkInterface leftFork, ForkInterface rightFork, BufferedWriter writer, boolean print) {
        this.print = print;
        this.writer = writer;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private synchronized void doAction (String action) {
        try {
            if (print) {
                System.out.println(Thread.currentThread().getName() + " " + action);
            }
            writer.write(Thread.currentThread().getName() + " " + action + "\n");
            writer.flush();
            Thread.sleep(((int) (Math.random() * 256)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void think () {
        doAction("Thinking");
    }

    public synchronized void pickupLeft () {
        try {
            while(!leftFork.isFree());
            leftFork.lock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        doAction("Picking up left fork");
    }

    public synchronized void pickupRight () {
        try {
            while(!rightFork.isFree());
            rightFork.lock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        doAction("Picking up right fork, eating meal " + countEat);
    }

    public synchronized void freeForks () {
        try {
            leftFork.unlock();
            rightFork.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
        doAction("Putting down forks, back to thinking");
    }
    

    @Override
    public void run () {
        try {
            while (true) {
                think();
                countEat ++;
                pickupLeft();
                pickupRight();
                freeForks();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}