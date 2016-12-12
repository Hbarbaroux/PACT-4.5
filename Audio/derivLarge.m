function dL = derivLarge (signal,N)
n=length(signal)
dL=zeros(1,n-2*N)
for i=1:n-2*N+1
    dL(i)=sum(signal(i:i+N-1)) - sum(signal(i+N:i+2*N-1))
end
