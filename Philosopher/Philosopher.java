public class Philosopher implements Runnable {

    private Object leftFork;
    private Object rightFork;
    private int meal = 0;

    public Philosopher (Object leftFork, Object rightFork ) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println(Thread.currentThread().getName() + " End of exectution, meals eaten : " + meal);
            }
        });
    }

    private void doAction (String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep(((int) (Math.random() * 256)));
    }


    @Override
    public void run () {
        try {
            while (true) {
                doAction(": Thinking");
                synchronized (leftFork) {
                    meal += 1;
                    doAction(": Picked up left fork");
                    synchronized (rightFork) {
                        doAction(": Picked up right fork");
                        doAction(": Put down right fork, eating meal number " + meal);
                    }
                    doAction(": Put down left fork, back to thinking");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}