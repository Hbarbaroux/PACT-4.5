function t = autocor(signal, k)
a = ifft(fft(signal(j))*fft(signal(j-floor(k*44100))));
for j=1:(length(signal)-1)
    if j-k*44100 > 0
        b = b + signal(j)*signal(j);
        c = c + signal(j-floor(k*44100))*signal(j-floor(k*44100));
    end
end
t = a / sqrt(b*c);
end
