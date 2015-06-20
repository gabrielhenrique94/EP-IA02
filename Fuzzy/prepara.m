
function[X,Y]=Calc_Series(S,lag)

Xold=zeros(1,lag);
X = [];

for i=1 : length(S)-1 
 X = [X;[S(i,1), Xold]];
 Xold(1,2:end) = Xold(1,1:end-1);
 Xold(1,1) = X(i,1);
 Y(i,1) = S(i+1,1);
end
%Tira valores com zeros
X=X(lag+1:end,:);
Y=Y(lag+1:end,:);

