function env = enveloppe (signal,a)
%Prend en entrée un signal et renvoie son enveloppe avec paramètre a
env=signal.^2;
env=filter(1,[1 -a],env);
end
