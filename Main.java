import java.util.Arrays;

public class Main {
    private static final int size = 10000000;
    private static float[] arr1;
    private static float[] arr2;
    private static int nrOfProcessors;

    public static void main(String[] args) throws InterruptedException {
        nrOfProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Доступно процессоров/ядер: " + nrOfProcessors);

        arr1 = new float[size];
        arr2 = new float[size];
        Arrays.fill(arr1, 1f);
        Arrays.fill(arr2, 1f);

        calcWithoutThreads();
        calcWithThreads();

        System.out.println( "Массивы " + (validateArrays(arr1, arr2) ? "": "НЕ") + "равны.");
    }

    private static void calcWithoutThreads() {
        System.out.print("Считаем в одном потоке, в цикле... ");
        long time = System.currentTimeMillis();
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
        }
        System.out.println("Время: " + (System.currentTimeMillis() - time));
    }

    private static void calcWithThreads() throws InterruptedException {
        System.out.print("Считаем в " + nrOfProcessors + " потоках... ");
        long time = System.currentTimeMillis();
        MultiThreadCalc mTCalc = new MultiThreadCalc(arr2, nrOfProcessors);
        mTCalc.startCalcAndWait();
        System.out.println("Время: " + (System.currentTimeMillis() - time));
    }

    private static boolean validateArrays(float[] arr1, float[] arr2) {
        if( (arr1 == null) || (arr2 == null) || (arr1.length != arr2.length) || (arr1.length == 0) ) {
            return false;
        }

        boolean equal = true;
        for (int i = 0; i < arr1.length; i++) {
            if(arr1[i] != arr2[i]) {
                System.out.printf("Index: %d. arr1(%f) != arr2(%f)\n\r", i, arr1[i], arr2[i]);
                equal = false;
            }
        }
        return equal;
    }
}