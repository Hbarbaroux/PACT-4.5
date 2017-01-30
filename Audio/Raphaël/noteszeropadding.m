
function tabnotes = noteszeropadding(signal)% renvoie un tableau des positions temporelles de chaque note avec leur fréquence
signalb = filter(1, [1, (1/10000)], signal);% filtre passe bas se débarassant des fréquences trop aigues n'appartenant pas à la guitare
denv = AttaqueEnveloppe(signalb,0.999); % partie positive de la dérivée de l'enveloppe 
Y = []; % index des montées
L = []; % index des montées et des descentes autour de la valeur limite (max à 90%)
E = []; % ecarts entre les montées
tabnotes = [];
M = max(denv);
i = 1;
limite = 0.1;
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
for k=1:(length(Y)-1)
    E = [E, Y(k+1) - Y(k)];
end
o = zeros(floor(max(E)/4));
for k=1:length(Y)
    o = [o, signalb(L(2*k-1):L(2*k))] ; 
    tabnotes = [tabnotes, estimation_hauteur_note(q), Y(k)]; 
end
end
    