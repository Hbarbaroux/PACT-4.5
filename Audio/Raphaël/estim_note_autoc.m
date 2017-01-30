function [ freq ] = estim_note_autoc(signal)
T = [];
j = 82.4;
while j < 1000;
    T = [T, abs(autoc(signal, 1/j))]; %#ok<AGROW>
    j = j*2^(1/12);
end
T
if max(T) > 0.7
    z = findmax(T);
    freq = 82.4*(2^(1/12)^z);
end

