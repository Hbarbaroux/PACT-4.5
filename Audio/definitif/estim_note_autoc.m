function freq = estim_note_autoc(signal)
freq=0;
j = 82.5;
T = [];
while j < 2000
    T = [T, autocor(signal, 1/j)]; %#ok<AGROW>
    j = j*2^(1/12);
end
z = findmax(T);
freq = 82.5*2^(z/12); 
end

