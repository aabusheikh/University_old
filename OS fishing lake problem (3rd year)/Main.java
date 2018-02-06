public class Main {
    public static void main(String[] args) {

        FishingLake[] lakes = new FishingLake[3];
        lakes[0] = new FishingLake(3,3);
        lakes[1] = new FishingLake(4,4);
        lakes[2] = new FishingLake(3,4);

        for (FishingLake lake : lakes) {
            Vacationer[] vacationers = lake.getVacationers();

            for (int j = 0; j < 5; ++j) {
                System.out.println("Running trial "+(j+1)+" out of 5 for experiment "+lake.getStrRep()+"...");
                Vacationer.setInitTime(System.currentTimeMillis());
                for (int i = 0; i < 10; ++i) {
                    vacationers[i].fish();
                }
                try {
                    Thread.sleep(24000);
                } catch (InterruptedException ie) {}
            }

            System.out.println("\nResults for "+lake.getStrRep()+":\n");
            System.out.println("Average and total fish caught by each vacationer over the 5 'days' (avg, total):");
            for (int k = 0; k < 10; ++k) {
                System.out.println("\t"+(k+1)+": "+(vacationers[k].getNumFishCaught()/5)+
                                              ", "+vacationers[k].getNumFishCaught());
            }
            System.out.println("---------------\n");
        }
    }
}
