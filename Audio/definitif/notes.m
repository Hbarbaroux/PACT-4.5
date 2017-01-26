
function notes = notes(signal)% renvoie un tableau des positions temporelles de chaque note avec leur fréquence
signalb = filter(1, [1, (1/10000)], signal);% filtre passe bas se débarassant des fréquences trop aigues n'appartenant pas à la guitare
denv = AttaqueEnveloppe(signalb,0.999);
Y = []; % index des montées
L = []; % index des montées et des descentes autour de la valeur limite (max à 90%)
notes = [];
M = max(denv);
i = 1;
limite = 0.1;
while i < length(denv) 
    while (denv(i) < M*limite && i < length(denv))
        i = i + 1;
    end
    L =  [L, i]; %#ok<*AGROW>
    while (denv(i) > M*limite && i < length(denv))
        i = i + 1;
    end
    L = [L, i];
end
L=L
for k=1:(length(L)/2)
    Y = [Y, L(2*k-1)];
end
Y=Y
for k=1:length(Y)
    q = signalb(L(2*k-1):L(2*k));
    notes = [notes, estimation_hauteur_note(q), Y(k)];
    est=estimation_hauteur_note(q)% notes contient dans les indices impairs les fréquences des notes et dans les indices pairs la position correspondante dans le morceau
end
end
    