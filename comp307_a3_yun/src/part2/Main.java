package part2;

public class Main {
    public static void main(String[] args) throws Exception {
        String labelled_file = "spamLabelled.dat";
        String unLabelled_file = "spamUnlabelled.dat";

        new NaiveBayes(labelled_file, unLabelled_file);

        System.out.println("Finish");
    }
}
