package part2;

import java.util.List;

public class Email {

    private List<Integer> attributeList;

    /** right-most column for labelledSpam.data is the class: 1 = spam, 0 = non-spam. */
    private int spam_or_notSpam = -1;

    /**
     * A constructor. It construct a new instance of Email.
     *
     * @param attributeList
     * @param spam
     *            right-most column for labelledSpam.data is the class: 1 = spam, 0 = non-spam.
     */
    public Email(List<Integer> attributeList, int spam) {
        this.attributeList = attributeList;
        if (spam != 0 || spam != 1) {
            // if it's unLabelled email, then set it to -1
            this.spam_or_notSpam = -1;
        }
        this.spam_or_notSpam = spam;

    }

}
