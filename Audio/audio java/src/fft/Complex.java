package fft;

public class Complex implements RingNumber {
    double re;
    double im;

    Complex(double re, double im) {
	this.re = re;
	this.im = im;
    }
    public RingNumber newNumber(int i) {
	return new Complex(i, 0);
    }
    public RingNumber add(RingNumber y) {
	Complex z = (Complex) y;
	return new Complex(re + z.re, im + z.im);
    }
    public RingNumber sub(RingNumber y) {
	Complex z = (Complex) y;
	return new Complex(re - z.re, im - z.im);
    }
    public RingNumber mult(RingNumber y) {
	Complex z = (Complex) y;
	return new Complex(re*z.re - im*z.im, re*z.im + im*z.re);
    }
    public String toString() {
	return Double.toString(re) + "+" + Double.toString(im) + "*I";
    }
}
