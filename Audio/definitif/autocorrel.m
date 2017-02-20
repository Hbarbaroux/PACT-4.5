function t = autocorrel(signal)
signalb = -1*signal;
t = ifft(fft(signal).*fft(signalb));
end

