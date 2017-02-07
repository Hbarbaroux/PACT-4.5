function freq = estimation_hauteur_note(signal)
n = length(signal);
L = 2^nextpow2(n*20); %zero_padding
Y = fft(signal, L)/L;
[M, I] = max(abs(Y));
freq = I*(44100/L);
end 