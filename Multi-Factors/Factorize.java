import java.math.BigInteger;
import java.util.ArrayList;

/*
 * @author: Neelesh Nidadhavolu
 */

public class Factorize {
	static BigInteger nn,rn;
	static ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
	
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.out.println("Usage: java Factor 123456");
        }

        nn = new BigInteger(args[0]);
        factors = factorize();
        
        System.out.println("Factors:");
        for (BigInteger xx : factors) {
            System.out.println(xx);
        }
    }

    static ArrayList<BigInteger> factorize() throws InterruptedException {
	
	BigMath bm = new BigMath();
	bm.start();
	bm.join();	
	
        System.out.println("Input: " + nn);
        System.out.println("Sqrt: "  + rn);

        TwoFactorize tf = new TwoFactorize();
        RestFactorize rf = new RestFactorize();
        
        tf.start();
        rf.start();
        
        tf.join();
        rf.join();
        
        factors.add(nn);
        return factors;
    }
}

class BigMath extends Thread{
    public final static BigInteger TWO  = new BigInteger("2");
    public final static BigInteger ZERO = new BigInteger("0"); 
    
    public void run(){
	Factorize.rn = sqrt(Factorize.nn);
    }
    
    static BigInteger sqrt(BigInteger nn) {
        return sqrtSearch(nn, TWO, nn);
    }

    static BigInteger sqrtSearch(BigInteger nn, BigInteger lo, BigInteger hi) {
        BigInteger xx = lo.add(hi).divide(TWO);

        if (xx.equals(lo) || xx.equals(hi)) {
            return xx;
        }
        
        BigInteger dy = nn.subtract(xx.multiply(xx));
        if (dy.compareTo(ZERO) < 0) {
            return sqrtSearch(nn, lo, xx);
        }
        else {
            return sqrtSearch(nn, xx, hi);
        }
    }
}

class TwoFactorize extends Thread {
	
	public void run(){
	BigMath bm1 = new BigMath();
	bm1.start();
	while (Factorize.nn.mod(BigMath.TWO).equals(BigMath.ZERO)) {
            Factorize.factors.add(BigMath.TWO);
            Factorize.nn = Factorize.nn.divide(BigMath.TWO);
            bm1.run();
        }
	}
}

class RestFactorize extends Thread {
	
    public void run(){
	BigMath bm2 = new BigMath();
	bm2.start();
	BigInteger ii = new BigInteger("3");
        while (ii.compareTo(Factorize.rn) <= 0) {
            if (Factorize.nn.mod(ii).equals(BigMath.ZERO)) {
            	Factorize.factors.add(ii);
            	Factorize.nn = Factorize.nn.divide(ii);
            	bm2.run();
            }
            else {
                ii = ii.add(BigMath.TWO);
            }
        }
    }
}
