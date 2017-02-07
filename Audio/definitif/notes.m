
function tabnotes = notes(signal)% renvoie un tableau des positions temporelles de chaque note avec leur fréquence
denv = AttaqueEnveloppe(signal,0.999); % partie positive de la dérivée de l'enveloppe 
Y = []; % index des montées
L = []; % index des montées et des descentes autour de la valeur limite (max à 90%)
tabnotes = [];
i = 1;
M=max(denv);
while i < length(denv) 
    while denv(i) < 0.2*M && i < length(denv)
        i = i + 1;
    end
    L =  [L, i]; %#ok<*AGROW>
    while denv(i) >= 0.2*M && i < length(denv)
        i = i + 1;
    end
    L = [L, i];
end
for k=1:(length(L)/2)
    Y = [Y, L(2*k-1)];
end
for j=1:length(Y)
    ecart = L(2*j)-L(2*j-1);
    if ecart > 0
        q=signal(L(2*j-1)*100:L(2*j)*100);
        est=estim_note_autoc(q);
        if est<2000 && est>50
            tabnotes=[tabnotes,est,Y(j)/441];
        end
    end
end
end