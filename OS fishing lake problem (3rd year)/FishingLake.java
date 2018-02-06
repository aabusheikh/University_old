import java.util.concurrent.Semaphore;

public class FishingLake {

    private Semaphore rod, bait;
    private Vacationer[] vacationers;
    private String strRep;

    public FishingLake(int rodPermits, int baitPermits) {
        this.strRep = rodPermits+","+baitPermits;

        this.rod = new Semaphore(rodPermits, true);
        this.bait = new Semaphore(baitPermits, true);

        this.vacationers = new Vacationer[10];
        for (int i = 0; i < 10 ; ++i) {
            this.vacationers[i] = new Vacationer(this);
        }
    }

    public String getStrRep() {
        return this.strRep;
    }

    public Vacationer[] getVacationers() {
        return this.vacationers;
    }

    public void rodAcquire() {
        try {
            this.rod.acquire();
        } catch (InterruptedException ie) {}
    }

    public void rodRelease() {
        this.rod.release();
    }

    public void baitAcquire() {
        try {
            this.bait.acquire();
        } catch (InterruptedException ie) {}
    }

    public void baitRelease() {
        this.bait.release();
    }
}
