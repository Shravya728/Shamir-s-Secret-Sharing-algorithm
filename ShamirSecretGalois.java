package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShamirSecretGalois extends ShamirSecret {
    
    private final int prime = 257;

    public ShamirSecretGalois(int secret, int shares, int threshold) {
        super(secret, shares, threshold);
    }

    @Override
    public void generateShares() {
        Random random = new Random();
        int[] coefficients = new int[threshold - 1];
        
        for (int i = 0; i < threshold - 1; i++) {
            coefficients[i] = random.nextInt(prime);
        }

        List<int[]> generatedShares = new ArrayList<>();
        for (int i = 1; i <= shares; i++) {
            int x = i;
            int y = computePolynomial(x, coefficients);
            generatedShares.add(new int[]{x, y});
        }

        this.sharesList = generatedShares;
    }

    private int computePolynomial(int x, int[] coefficients) {
        int y = secret;
        for (int i = 0; i < coefficients.length; i++) {
            y = addMod(y, mulMod(coefficients[i], powerMod(x, i + 1, prime), prime), prime);
        }
        return y;
    }

    private int addMod(int a, int b, int mod) {
        return (a + b) % mod;
    }

    private int mulMod(int a, int b, int mod) {
        return (a * b) % mod;
    }

    private int powerMod(int base, int exp, int mod) {
        int result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = mulMod(result, base, mod);
            }
            base = mulMod(base, base, mod);
            exp >>= 1;
        }
        return result;
    }
}
