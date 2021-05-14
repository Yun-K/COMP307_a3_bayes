package part2;

public class Main {
    public static void main(String[] args) throws Exception {
        String labelled_file = "spamLabelled.dat";
        String unLabelled_file = "spamUnlabelled.dat";

        NaiveBayes nBayesClassfier = new NaiveBayes(labelled_file, unLabelled_file);
        nBayesClassfier.train_constructClassifier();
        nBayesClassfier.test_applyClassifier();

        System.out.println("Finish");
    }
}
