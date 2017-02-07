function spectre = spectre(signal, Fe, NFFT) %#ok<STOUT>
Y = fft(signal, NFFT);
Z = 10*log(abs(Y)/0.001);
f = (0:NFFT-1)*Fe/NFFT; 
plot(f(1:floor(NFFT/2)),Z(1:floor(NFFT/2)))
end

