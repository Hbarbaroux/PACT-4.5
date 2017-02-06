function min = minecart(notes)
%Calcule l'écart maximal en temps entre deux notes d'un tableau "notes"
min=notes[length(notes)];
for k=2:length(notes)/2
    e=notes[k*2]-notes[k*2-2];
    if (e<min)
        min=e;
    end
end
return e
end

