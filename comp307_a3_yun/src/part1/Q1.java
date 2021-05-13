package part1;

public class Q1 {

    static StringBuffer sb = new StringBuffer("X|Y|Z|P(X,Y,Z) ( i.e. P(X)*P(Y|X)*P(Z|Y) )|\n");

    public Q1() {
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {

                    sb.append(x).append("|").append(y).append("|").append(z).append("|");
                    computeResult(x, y, z);
                    sb.append("\n");
                }

            }

        }

        System.out.println(sb);
    }

    public static void main(String[] args) {
        new Q1();

    }

    public double computeResult(int x, int y, int z) {
        double result = (getX_prob(x) * getY_given_X_prob(x, y) * getZ_given_Y_prob(y, z));
        sb.append(result).append(" == ");
        sb.append(getX_prob(x)).append("*").append(getY_given_X_prob(x, y)).append("*")
                .append(getZ_given_Y_prob(y, z));
        return result;
    }

    double getX_prob(int x) {
        assert x == 0 || x == 1;
        if (x == 0) {
            return 0.35;
        }
        return 0.65;
    }

    double getY_given_X_prob(int x, int y) {
        assert x == 0 || x == 1;
        assert y == 0 || y == 1;
        if (x == 0) {
            if (y == 0) {
                return 0.10;
            }
            return 0.90;
        }

        if (x == 1) {
            if (y == 0) {
                return 0.60;
            }
            return 0.40;
        }

        // dead return, should not execute below
        assert false;
        return 0;
    }

    double getZ_given_Y_prob(int y, int z) {
        assert y == 0 || y == 1;
        assert z == 0 || z == 1;
        if (y == 0) {
            if (z == 0) {
                return 0.70;
            }
            return 0.30;
        }
        if (y == 1) {
            if (z == 0) {
                return 0.20;
            }
            return 0.80;

        }

        // dead return, should not execute below
        assert false;
        return 0;

    }

}
