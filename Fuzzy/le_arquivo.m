function[arquivo] = le_arquivo(caminho)
arquivo = fopen(caminho, 'r');
format = '%f\n';
arquivo = fscanf(arquivo, format);
end
