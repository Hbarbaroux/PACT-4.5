function t = autocor(signal, k)
n = length(signal);
nfft = 2^nextpow2(2*n);
t = 0;
if length(signal)-floor(k*44100)> 0
    t = (ifft((fft(signal(floor(k*44100)+1:length(signal), nfft))).*(fft(signal(1:length(signal)-floor(k*44100)nfft)))));
end
t = t(1: n);
end
