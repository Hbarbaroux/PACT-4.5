function freq = estim_note_autoc(signal)
freq=0;
T = abs(xcorr(signal));
if max(T) > 0.7
    z = findmax(T);
    freq = 1/z; 
end

