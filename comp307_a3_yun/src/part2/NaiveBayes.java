package part2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes {

    /** string for before adding emails to address zero occurance case */
    final String preFix_bef0 = "before_";

    /** prefix for attributes */
    final String preFix_true = "true_", preFix_false = "false_";

    final String spam = "spam_", noSpam = "noSpam_";

    /**
     * spamEmail, it's for solving zero occurance, this email got everything with true value
     */
    Email fakeEmail_allTrue = new Email(new ArrayList<Integer>() {
        { // we've 12 attributes in total, so assign it first
            for (int i = 0; i < 12; i++)
                add(1);
        }
    }, 1);// 1 means it is spam email

    /**
     * non-spam Email, it's for solving zero occurance, this email got everything with false
     * value.
     */
    Email fakeEmail_allFalse = new Email(new ArrayList<Integer>() {
        { // we've 12 attributes in total, so assign it first
            for (int i = 0; i < 12; i++)
                add(0);
        }
    }, 0);// 0 means it is non-spam email

    private List<Email> train_labelledEmailList;

    private List<Email> test_unlabelledEmailList;

    /**
     * map the corresponing att/classLabel to it's Email list
     * <p>
     * for posterior & likelihood use
     */
    private Map<String, List<Email>> classAtt_EmailList_map = new HashMap<String, List<Email>>();

    /**
     * map classLabel to the prior probability.
     * <p>
     * 
     * i.e P(Class=spam=1=true) = ? && P(Class=non-spam=0=false) = ?
     * <p>
     * p(attribute0 = true|1),...,p(att 11 = true|1 ) = ?
     */
    private Map<String, Double> prior_classAttLabel_prob_map = new HashMap<String, Double>();

    /**
     * p(att=? | ClassLabel=?)
     * <p>
     * hint: classLabel=spam OR noSpam
     */
    private Map<String, Double> likelihood_att_prob_map = new HashMap<String, Double>();

    private Map<String, Double> posterior_attLabel_prob_map = new HashMap<String, Double>();

    /**
     * A constructor. It construct a new instance of NaiveBayes.
     *
     * @param labelledFilePath
     * @param unLabelledFilePath
     * @throws Exception
     */
    public NaiveBayes(String labelledFilePath, String unLabelledFilePath) throws Exception {
        this.train_labelledEmailList = Util.readFile(labelledFilePath);
        this.test_unlabelledEmailList = Util.readFile(unLabelledFilePath);

    }

    /**
     * Description: <br/>
     * Responsible for calculating likelihood, posterior.
     * 
     * @author Yun Zhou
     */
    public void train_constructClassifier() {
        setUpPrior();
        putAllLikeliHood();
        // not sure if need to calculate the posterior of each attribute from labelledEmail.dat
        // getPosterior();
        System.out.println("Done for the training process successfully.");
    }

    /**
     * Description: <br/>
     * To classify unlabelled Email objects, and print the classify result on the screen.
     * <p>
     * Need to calculate P(Spam | att0,...,att11) & P(noSpam | att0,...,att11), then see
     * classify this instance with the higher one.
     * <p>
     * Computes P(class | instance data)for each class, and choose the class with the highest
     * probability.
     * 
     * 
     * @author Yun Zhou
     */
    public void test_applyClassifier() {
        System.out.println("\nStart testing..................");
        //
        double correctCount = 0.0, train_index = 0;
        for (Email email : train_labelledEmailList) {
            if (train_index++ == 200) {
                break;
            }

            double spamPosterior = getPosterior(email.getAttributeList(), this.spam);
            double noSpamPosterior = getPosterior(email.getAttributeList(), this.noSpam);
            // System.out.println(spamPosterior + "\n" + noSpamPosterior);

            // this.posterior_attLabel_prob_map.put();

            // set the prediction label
            if (spamPosterior > noSpamPosterior) {
                email.setPredicted_classLabel(1);
            } else {
                email.setPredicted_classLabel(0);
            }

            // check if the prediction is correct
            if (email.getClassLabel_spam_or_notSpam() == email.getPredicted_classLabel()) {
                correctCount++;
            }
        }

        double acc = correctCount / (train_labelledEmailList.size() - 2) * 100;

        System.out.println(
                "For labelledEmail set, without count that 2 emails "
                           + "which are used for solving zero occurance)\n"
                           + "My algorthim got " + correctCount + " correct out of "
                           + (train_labelledEmailList.size() - 2)
                           + "\nacc=" + String.format("%.2f", acc) + "%");

        StringBuffer sb = new StringBuffer("\nUnlabelled emails:");
        int emailIndex = 0, spamClassify = 0, noSpamClassify = 0;
        for (Email email : test_unlabelledEmailList) {
            double spamPosterior = getPosterior(email.getAttributeList(), this.spam);
            double noSpamPosterior = getPosterior(email.getAttributeList(), this.noSpam);
            if (spamPosterior > noSpamPosterior) {
                email.setPredicted_classLabel(1);
                spamClassify++;
            } else {
                email.setPredicted_classLabel(0);
                noSpamClassify++;
            }

            sb.append("\nThis " + ++emailIndex + "th email is classify as "
                      + (email.getPredicted_classLabel() == 0 ? "noSpam" : "spam") + " email.");
        }
        sb.append("\nTotally, we have " + spamClassify + " spam emails\n\tAnd " + noSpamClassify
                  + " noSpam emails.");
        System.out.println(sb.toString());
    }

    /**
     * Description: <br/>
     * Get the posterior/conditional prob of the given classLabel.
     * <p>
     * i.e. P(class=spam |att1=.....att12...)=?
     * 
     * @author Yun Zhou
     * @param attributeList
     * @param classLabel_spamOrNotSpam
     * @return
     */
    private double getPosterior(List<Integer> attributeList, String classLabel_spamOrNotSpam) {
        double postprior_toReturn = 1;
        // we dont need denominator since the comparing itself share the same denominator
        // denominator should be P(att1,att2,att3,....,att12), which are from attributeList
        double denominator = 1.0;

        double classProb = this.prior_classAttLabel_prob_map.get(classLabel_spamOrNotSpam);
        double numerator = classProb;

        for (int attIndex = 0; attIndex < attributeList.size(); attIndex++) {
            boolean isAttTrue = attributeList.get(attIndex) == 1 ? true : false;
            numerator *= getSingleLikeliHood(attIndex, isAttTrue, classLabel_spamOrNotSpam);
        }

        postprior_toReturn = numerator / denominator;

        return postprior_toReturn;
    }

    /**
     * Description: <br/>
     * calculate all single likelihood and put them into the likelihood_att_prob_map
     * 
     * @author Yun Zhou
     */
    private void putAllLikeliHood() {
        // calculate all single likelihood and put them into the likelihood_att_prob_map
        for (int i = 0; i < 12; i++) {
            getSingleLikeliHood(i, false, this.noSpam);
            getSingleLikeliHood(i, false, this.spam);
        }
        System.out.println("Done for putting all likelihood into the Map");
    }

    /**
     * Description: <br/>
     * Calculate the likelihood prob from the given input args.
     * <p>
     * This method will return the likelihood and put att=true && att=false into the
     * likelihood_att_prob_map.
     * <P>
     * i.e. P(att=? | ClassLabel=? )=?
     * 
     * @author Yun Zhou
     * @param attribute
     * @param isAttTrue
     * @param classLabel_spamOrNotSpam
     * @return
     */
    public double getSingleLikeliHood(int attIndex, boolean isAttTrue,
            String classLabel_spamOrNotSpam) {
        double likelihood_toReturn = 0;

        // get emails with corresponding classLabel
        List<Email> limit_emails = this.classAtt_EmailList_map.get(classLabel_spamOrNotSpam);

        // for calcualting att=isAttTrue and att=isAttFalse
        double attCount = 0.0,
                inverse_attCount = 0.0;

        //
        for (Email email : limit_emails) {
            boolean isValTrue = email.getAttributeList().get(attIndex) == 1 ? true : false;
            // check if equals
            if (isValTrue == isAttTrue) {
                attCount++;
            } else {
                inverse_attCount++;
            }
        }
        String key = isAttTrue ? this.preFix_true : this.preFix_false;
        key += "att" + attIndex;
        likelihood_toReturn = attCount / limit_emails.size();
        this.likelihood_att_prob_map.put(key, likelihood_toReturn);

        String inverse_key = isAttTrue ? this.preFix_true : this.preFix_false;
        inverse_key += "att" + attIndex;
        double attInverse_likeliHood = inverse_attCount / limit_emails.size();
        // assert attInverse_likeliHood == 1 - likelihood_toReturn;
        assert attInverse_likeliHood + likelihood_toReturn == 1;
        this.likelihood_att_prob_map.put(inverse_key, attInverse_likeliHood);//

        // System.out.println(attInverse_likeliHood + "\n" + (1 - likelihood_toReturn));
        return likelihood_toReturn;

        // double classLabel_prob =
        // this.prior_classAttLabel_prob_map.get(classLabel_spamOrNotSpam);
        // System.out.println(classLabel_prob);
        // double attr_prob = this.prior_classAttLabel_prob_map.get(attribute);

    }

    public void setUpPrior() {
        // calculate and assign the prior prob from train_dataset
        this.prior_classAttLabel_prob_map = calculatePriorProbability(this.train_labelledEmailList,
                false);

        // add emails to avoid the zero occurance
        this.train_labelledEmailList.add(fakeEmail_allTrue);
        this.train_labelledEmailList.add(fakeEmail_allFalse);
        //
        this.prior_classAttLabel_prob_map
                .putAll(calculatePriorProbability(this.train_labelledEmailList, true));
    }

    /**
     * Description: <br/>
     * Method for calculating the prior probability.(i.e. p(class))
     * <p>
     * i.e P(Class=spam) = ? && P(Class=non-spam) = ?
     * 
     * @author Yun Zhou
     * @param labelledEmails
     *            the labelled data set for training
     * @param isAddZero
     * @return the map that map the classLabel to the corresponding probability
     */
    private Map<String, Double> calculatePriorProbability(List<Email> labelledEmails,
            boolean isAddZero) {
        // calculate p(class) from train_dataSet
        // as well as p(attribute 0....attribute 11)from the train_dataset
        double spamOccurance = 0.0;
        double nonSpamOccurance = 0.0;

        // set up the tempPrefix
        StringBuffer tempPreFix = isAddZero ? new StringBuffer("")
                : new StringBuffer(this.preFix_bef0);

        /*
         * this list is for prior use which is P(att=?)=?
         * 
         * list for assigning attribute occurance among all the labelled email data set, only
         * increase the corresponding index value iff the int att_label is equal to 1
         */
        List<Double> attributeOccuranceList = new ArrayList<Double>() {
            { // we've 12 attributes in total, so assign it first
                for (int i = 0; i < 12; i++)
                    add(0.0);
            }
        };

        for (Email email : labelledEmails) {
            // do the classLabel: spam or noSpam first
            int classLabel = email.getClassLabel_spam_or_notSpam();
            String k = "";
            if (classLabel == 1) {
                spamOccurance++;
                // map classLabel to it's occurance
                k = tempPreFix + this.spam;
            } else if (classLabel == 0) {
                nonSpamOccurance++;
                k = tempPreFix + this.noSpam;
            }
            if (!classAtt_EmailList_map.containsKey(k)) {// map corresponding email to this
                                                         // ClassLabel
                classAtt_EmailList_map.put(k, new ArrayList<Email>());
            }
            classAtt_EmailList_map.get(k).add(email);// add email to the list

            // then calculate the corresponding attribute occurances
            for (int attIndex = 0; attIndex < email.getAttributeList().size(); attIndex++) {
                int att_label = email.getAttributeList().get(attIndex);
                String att_key = "";
                if (att_label == 1) {
                    double oldVal = attributeOccuranceList.get(attIndex);
                    attributeOccuranceList.set(attIndex, (oldVal + 1));
                    // map this email into attribute=1=true
                    att_key = tempPreFix.toString() + this.preFix_true + "att" + attIndex;

                } else if (att_label == 0) {
                    // map this email into attribute=0=false
                    att_key = tempPreFix.toString() + this.preFix_false + "att" + attIndex;
                }

                if (!classAtt_EmailList_map.containsKey(att_key)) {
                    this.classAtt_EmailList_map.put(att_key, new ArrayList<Email>());
                }
                classAtt_EmailList_map.get(att_key).add(email);
                assert classAtt_EmailList_map.get(att_key).size() > 0;
                assert classAtt_EmailList_map.get(att_key).size() <= labelledEmails.size();

            }
        }
        assert attributeOccuranceList.size() == 12;

        // tempMap for storing the prior prob of classLabel/attribute
        // like P(Spam) = ?, P(att0)= ?
        Map<String, Double> tempMap = new HashMap<String, Double>();
        // calculte & assign the p(att)
        for (int attIndex = 0; attIndex < attributeOccuranceList.size(); attIndex++) {
            double prob = attributeOccuranceList.get(attIndex) / labelledEmails.size();
            String key = tempPreFix + String.valueOf(attIndex);
            tempMap.put(key, prob);
            // System.out.println(key + "\t" + prob);
        }

        // assigning the spam class label: p(class)
        double spamProb = spamOccurance / labelledEmails.size();
        double noSpamProb = nonSpamOccurance / labelledEmails.size();
        assert spamProb + noSpamProb == 1;// assertion check
        System.out.println(
                "According to the spamlabelled.dat, we can get:");
        String tempString = isAddZero ? "After " : "Before ";// check if it's before or after
        System.out.println(tempString + "applying Zero-Occurance:\n"
                           + "\tSpam prob, P(Spam)= " + spamProb
                           + "\n\tNo-spam prob,P(noSpam)= " + noSpamProb);

        tempMap.put(tempPreFix.toString() + this.spam, spamProb);
        tempMap.put(tempPreFix.toString() + this.noSpam, noSpamProb);
        // System.out.println(tempPreFix.toString() + this.spam + spamProb);
        return tempMap;
    }

}
