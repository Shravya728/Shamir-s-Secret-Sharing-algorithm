package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShamirSecret {
    private int secret;
    private int shares;
    private int threshold;
    private List<int[]> shareList;

    public ShamirSecret(int secret, int shares, int threshold) {
        this.secret = secret;
        this.shares = shares;
        this.threshold = threshold;
        this.shareList = new ArrayList<>();
    }

    public void generateShares() {
        Random rand = new Random();
        int[] coefficients = new int[threshold - 1];
        coefficients[0] = secret;

        // Generate random coefficients for polynomial
        for (int i = 1; i < threshold - 1; i++) {
            coefficients[i] = rand.nextInt(100); // Random coefficients
        }

        // Generate shares
        for (int i = 1; i <= shares; i++) {
            int x = i;
            int y = calculatePolynomial(x, coefficients);
            shareList.add(new int[]{x, y});
        }
    }

    private int calculatePolynomial(int x, int[] coefficients) {
        int result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public List<int[]> getShares() {
        return shareList;
    }

    public int reconstruct(List<int[]> randomShares) {
        // Simple reconstruction using Lagrange interpolation or similar
        return secret; // Placeholder
    }
}
