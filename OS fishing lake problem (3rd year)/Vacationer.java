import java.util.concurrent.ThreadLocalRandom;

public class Vacationer implements Runnable{

    private static long initTime;

    private FishingLake lake;
    private int numFishCaught;

    public Vacationer(FishingLake _lake) {
        this.lake = _lake;
        this.numFishCaught = 0;
    }

    public void fish() {
        (new Thread(this)).start();
    }

    public int getNumFishCaught() {
        return this.numFishCaught;
    }

    public static void setInitTime(long _ms) {
        initTime = _ms;
    }

    @Override
    public void run() {
        while ((System.currentTimeMillis() - initTime) <= 24000) {
            this.lake.rodAcquire();
            this.lake.baitAcquire();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
            }

            this.numFishCaught += ThreadLocalRandom.current().nextInt(0, 11);

            this.lake.baitRelease();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
            }
            this.lake.rodRelease();
        }
    }
}
