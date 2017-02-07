function dLF=derivLargeFilter (env,fech,N)
%on "simplifie" l'enveloppe (on prend 1 point tous les 100 points par
%exemple)
env=env(1:fech:end);
env=db(env);
dLF=filter([ones(1,N) -ones(1,N)],1,env);
