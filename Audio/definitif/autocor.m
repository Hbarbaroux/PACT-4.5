function t = autocor(signal, k)
for j=1:(length(signal)-1)
    if j-k*44100 > 0
        b = b + signal(j)*signal(j);
        c = c + signal(j-floor(k*44100))*signal(j-floor(k*44100));
    end
end
t = ifft(fft(signal(floor(k*44100):length(signal)))*fft(signal(0:(length(signal)-floor(k*44100))))) / sqrt(b*c);
end
