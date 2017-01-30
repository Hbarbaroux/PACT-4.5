function [ freq ] = estim_note_autoc( signal, k )
a = 0;
b = 0;
c = 0;
for j=1:(length(signal)-1)
    b = b + signal(j)*signal(j)
    if j-k > 0
        a = a + signal(j)*signal(j-k);
        c= c+ signal(j-k)*signal(j-k);
    end
end
freq = sqrt(b*c) / a
end

