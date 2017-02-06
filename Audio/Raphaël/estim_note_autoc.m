function [ freq ] = estim_note_autoc(signal, NJ) % NJ correspond au niveau de justesse choisi par l'utilisateur: 0.7 = facile _ 0.8 = moyen _ 0.9 = difficile
T = [];
freq=0;
j = 155.55;
while j < 1000;
    T = [T, abs(autoc(signal, 1/j))]; %#ok<AGROW>
    j = j*2^(1/12);
end
if max(T) > NJ
    z = findmax(T);
    freq = 155.55*(2^(1/12)^(z)); 
end

