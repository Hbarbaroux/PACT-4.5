function derenv = AttaqueHilbert(signal)
n=length(signal)
env=abs(hilbert(signal))
derenv=derivLarge(env,4410)*n
