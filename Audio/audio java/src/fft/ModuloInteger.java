package fft;

public class ModuloInteger implements RingNumber {
    int mod;
    int n;
    ModuloInteger(int mod, int n) {
	this.mod = mod;
	this.n = n % mod;
    }
    public RingNumber newNumber(int i) {
	return new ModuloInteger(mod, i);
    }
    public RingNumber add(RingNumber y) {
	return new ModuloInteger(mod, n+((ModuloInteger) y).n);
    }
    public RingNumber sub(RingNumber y) {
	return new ModuloInteger(mod, n-((ModuloInteger) y).n+mod);
    }
    public RingNumber mult(RingNumber y) {
	return new ModuloInteger(mod, n*((ModuloInteger) y).n);
    }
    public String toString() { return Integer.toString(n); }
}
   
    