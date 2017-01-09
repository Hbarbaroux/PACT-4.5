function notes = analyse(signal) % renvoie un tableau des positions temporelles de chaque note avec leur fréquence
denv = AttaqueHilbert(signal,4410);
n = length(signal);
Y = []; % index des montées
L = []; % index des montées et des déscentes autour de la valeur limite (max à -3dB)
notes = [];
tM = (find(denv==max(denv)));
i = 1;
    while i < n
        while denv(i) < tM*0.7
            i += 1;
        end
        append(L,i);
        while denv(i) > tM*0.7
            i += 1;
        end
        append(L,i); 
    end
    for k=1:(length(L)/2)
        append(Y,L(2k-1));
    for k=1:length(Y)
        append(notes,estimation_hauteur_note(signal(Y(k:k+1))));
        append(notes,(Y(k)/44100)); % notes contient dans les indices impairs les fréquences des notes et dans les indices pairs la position correspondante dans le morceau
end
    