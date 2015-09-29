import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyGenerator {
	private Key privateKey, publicKey;
	
	private class Key {
		private BigInteger x,y;
		Key (BigInteger x, BigInteger y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
	
	RSAKeyGenerator(int bits) {
		BigInteger p = BigInteger.probablePrime(bits, new SecureRandom());
		BigInteger q;
		do {
			q = BigInteger.probablePrime(bits, new SecureRandom());
		} while (q.equals(p));
		BigInteger n = p.multiply(q);
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger e, d;
	
		do {
			e = BigInteger.probablePrime(bits, new SecureRandom());
		} while ((e.compareTo(BigInteger.ONE) != 1) || (e.compareTo(phi) != -1) || (e.gcd(phi).compareTo(BigInteger.ONE) != 0));
		
		d = e.modInverse(phi);
		privateKey = new Key(d, n);
		publicKey = new Key(e, n);
		//below is a testing code for the keys. 
		/*
		System.out.println("p: " + p);
		System.out.println("q: " + q);
		System.out.println("privateKey: " + this.getPrivateKey().toString());
		System.out.println("publicKey: " + this.getPublicKey().toString());
		BigInteger msg = BigInteger.valueOf(100);
		System.out.println("message: " + msg);
		BigInteger trans = msg.modPow(e, n);
		System.out.println("trans: " + trans);
		System.out.println("decoded message: " + trans.modPow(d, n));
		*/
	}
	
	public Key getPrivateKey() {
		return privateKey;
	}
	
	public Key getPublicKey() {
		return publicKey;
	}
	
	public static void main(String[] args) {
		RSAKeyGenerator test = new RSAKeyGenerator(5);
		System.out.println(test.getPrivateKey().toString());
		System.out.println(test.getPublicKey().toString());		
	}
}
