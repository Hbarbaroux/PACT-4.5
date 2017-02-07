function t = autocor(signal, k)
b = 0;
c = 0;
for j=1:(length(signal)-1)
    if j-k*44100 > 0
        b = b + signal(j)*signal(j);
        c = c + signal(j-floor(k*44100))*signal(j-floor(k*44100));
    end
end
t = 0;
if length(signal)-floor(k*44100)> 0
    t = (ifft((fft(signal(floor(k*44100)+1:length(signal)))).*(fft(signal(1:length(signal)-floor(k*44100)))))) / sqrt(b*c);
end
end
