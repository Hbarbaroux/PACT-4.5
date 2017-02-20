package fft;

interface RingNumber {
    
    RingNumber newNumber(int i);
    // Opérations de base
    RingNumber add(RingNumber y);
    RingNumber sub(RingNumber y);
    RingNumber mult(RingNumber y);
}
