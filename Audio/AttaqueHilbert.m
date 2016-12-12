function derenv = AttaqueHilbert(signal,N)
n=length(signal)
env=abs(hilbert(signal))
derenv=derivLarge(env,N)*n
