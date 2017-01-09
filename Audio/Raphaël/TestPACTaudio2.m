Fs = 10000;
[y, Fs]=audioread('/cal/homes/rteboul/workspace/PACT-4.5/Audio/note.mp3'); % on charge le son enregistré
T = 1/Fs ;
L = 10000;
t = (0:L-1)*T;
f = (0:L-1)/L*Fs;
NS= sin(880*pi*t).*(1-exp(-10*t));
denv = AttaqueHilbert(y,4410)
M = max(denv);
indiceDuMax = (find(denv==M));
R = t(indiceDuMax);
t1 = R - 0.1;
t2 = R + 0.1;
T1 = indiceDuMax ;
T2 = indiceDuMax + 1000;
Y = [zeros(1,T1-1),fft(y(T1:T2)),zeros(1,length(y)-T2)];
Z = abs([zeros(1,999),Y(1000:length(Y))]);
freq = f(find(Z==max(Z)))/10
plot(t,denv)

