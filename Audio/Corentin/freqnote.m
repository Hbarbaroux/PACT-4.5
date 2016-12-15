function freq = freqnote (signal,f_ech,a,N,inter)
n=length(signal);
n_att=t_attaque(signal,a,N);
analyse=signal(max(1,n_att-inter*f_ech):min(n,n_att+inter*f_ech));
f=fft(analyse);
m=max(f);
nfreq=find(f==m);
freq=nfreq*f_ech/n;



