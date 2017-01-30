function imax = findmax(tab)
max=tab(1);
imax=1;
%trouve l'abscisse du premier max d'un tableau
n=length(tab);
for i=1:n
    if (tab(i)>max)
        imax=i;
        max=tab(i);
    end
end
end

