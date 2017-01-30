function [ t ] = autoc(signal, k)
a = 0;
b = 0;
c = 0;
for j=1:(length(signal)-1)
    b = b + signal(j)*signal(j);
    if j-k*44100 > 0
        a = a + signal(j)*signal(j-floor(k*44100));
        c= c+ signal(j-floor(k*44100))*signal(j-floor(k*44100));
    end
end
t = a / sqrt(b * c);
end

