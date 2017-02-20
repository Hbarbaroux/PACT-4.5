function freq = estim_note_autoc(signal)
freq=0;
U = [];
j = 82.5;
T = autocorrel(signal);
while j < 2000
    U = [U, T(floor(44100/j))]; 
    j = j*2^(1/12);
end
z = findmax(U);
freq = 82.5*2^((z+7)/12); 
end

