function fis = fizzyDin(lag)
%Cria nome para oconjunto fuzzy
name = strcat('fuzzy', num2str(lag))
%cria fuzzy
fis = newfis(name)

% num de inputs = lag
for i=1:lag+1
    nameVarIn = strcat('in', num2str(i))
    fis = addvar(fis,'input',nameVarIn,[0 1]);
    namemf = strcat('mf', num2str(i))
   
    %para cada variavel linguistica crio 4 termos linguisticos
        fis = addmf(fis,'input',i,'mf1','trimf',[-0.3 0.0 0.3]);
        fis = addmf(fis,'input',i,'mf2','trimf',[0.05  0.35 0.65]);
        fis = addmf(fis,'input',i,'mf3','trimf',[0.4 0.7 1.0]);
        fis = addmf(fis,'input',i,'mf4','trimf',[0.75  1.0 1.3]);
        
    %plotmf(fis,'input',1)
      
end

%crio output
      
    fis = addvar(fis,'output','VarOut',[0 1]);    

%Adciono 4 mfs para outuput
        fis = addmf(fis,'output',1,'mfOut1','trimf',[-0.2 0.0 0.2]);
        fis = addmf(fis,'output',1,'mfOut2','trimf',[0.05  0.25 0.45]);
        fis = addmf(fis,'output',1,'mfOut3','trimf',[0.30 0.5 0.7]);
        fis = addmf(fis,'output',1,'mfOut4','trimf',[0.55 0.75 0.95]);
        fis = addmf(fis,'output',1,'mfOut5','trimf',[0.80 1.0 1.2]);
   
 %Crio todas as regras possiveis!!
 rule1 = [1 1 1 1 1];
 rule2 = [1 2 2 1 1];
 rule3 = [2 3 3 1 1];
 rule4 = [3 4 4 1 1];
 ruleList = [rule1;rule2;rule3;rule4];
 fis = addrule(fis,ruleList);
 
 %Transformo fizz num arquivo
    nomeFuzzy = strcat('FuzzyLag', num2str(lag))
    writefis(fis, nomeFuzzy)
 
end

