function t_att = t_attaque (signal,a,N)
env=AttaqueEnveloppe(signal,a,N);
m=max(env) ;
t_att = find(env==m);

