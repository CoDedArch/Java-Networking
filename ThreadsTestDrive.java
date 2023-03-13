public class ThreadsTestDrive extends Thread {    
    public static void main(String[] args) {
        // have two threads 
        ThreadsTestDrive thread1, thread2;
        thread1 = new ThreadsTestDrive();
        thread2 = new ThreadsTestDrive();

        thread1.start(); // this will call the run method 
        thread2.start(); // this will call the  run method as well
    }


    public void run()
    {
        int thread0 = 0;
        int thread1 = 0;
        int pause;
        for (int i = 0; i < 10; i++)
        {
            try {
                System.out.println(
                    getName() + " being executed."
                );

                if (getName().equals("Thread-0"))
                    thread0++;
                if (getName().equals("Thread-1"))
                    thread1++;
                pause = (int)(Math.random()*3000);
                sleep(pause);
            }
            catch(InterruptedException inExp)
            {
                System.out.println(inExp);
            }
        }
        System.out.println("Thread-0: "+ thread0);
        System.out.println("Thread-1: "+ thread1);
    }
}
