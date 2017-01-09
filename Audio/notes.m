function notes = notes(signal) % renvoie un tableau des positions temporelles de chaque note avec leur fréquence
denv = AttaqueHilbert(signal,4410);
n = length(signal)
Y = []; % index des montées
L = []; % index des montées et des déscentes autour de la valeur limite (max à 90%)
notes = [];
M = max(denv);
i = 1;
    while i < length(denv) 
        while denv(i) < M*0.99&& i < length(denv)
            i = i + 1;
        end
        L =  [L, i];
        while denv(i) > M*0.99&& i < length(denv)
            i = i + 1;
        end
       L = [L, i];
    end
    for k=1:(length(L)/2)
        Y = [Y, L(2*k-1)];
    end
    for k=1:(length(Y) - 1)
        notes = [estimation_hauteur_note(signal(Y(k:k+1)))]; % notes contient dans les indices impairs les fréquences des notes et dans les indices pairs la position correspondante dans le morceau
    end
end
    