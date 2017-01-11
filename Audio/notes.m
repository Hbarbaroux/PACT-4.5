
function notes = notes(signal)% renvoie un tableau des positions temporelles de chaque note avec leur fréquence
signalb = filter(1, [1, (1/10000)], signal);
denv = AttaqueEnveloppe(signalb,0.999);
Y = []; % index des montées
L = []; % index des montées et des descentes autour de la valeur limite (max à 90%)
notes = [];
M = max(denv);
i = 1;
limite = 0.5
    while i < length(denv) 
        while denv(i) < M*limite && i < length(denv)
            i = i + 1;
        end
        L =  [L, i]; %#ok<*AGROW>
        while denv(i) > M*limite && i < length(denv)
            i = i + 1;
        end
       L = [L, i];
    end
    for k=1:(length(L)/2)
        Y = [Y, L(2*k-1)];
    end
    for k=1:(length(Y) - 1)
        q = signalb((Y(k)):Y(k+1));
        notes = [notes, estimation_hauteur_note(q), ((Y(k)*75+Y(k+1)*25)/100)]; % notes contient dans les indices impairs les fréquences des notes et dans les indices pairs la position correspondante dans le morceau
    end
end
    