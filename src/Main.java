import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import hashTable.*;

public class Main {
    public static final String DATABASE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "database" + File.separator;
    public static void main(String[] args) throws Exception {
        //gerarDadosAleatorios(300_000, "desordenados-300_000", 1_000_000);
        //gerarDadosRepetidos(500_000, "desordenados-500_000", 1_000_000);

        int[] database, busca;
        int fatorDeCarga, encontrados = 0;

        database = lerArquivo("desordenados-300_000", 300_000);
        busca = lerArquivo("desordenados-300_000", 500_000);
        fatorDeCarga = (int) Math.ceil((double) database.length / 12);

        //endereçamento fechado
        HTClosedAddressing closedHashTable = new HTClosedAddressing(fatorDeCarga);
        for (int i = 0; i < database.length; i++) {closedHashTable.insert(database[i]);}
        for (int i = 0; i < busca.length; i++){ 
            if(closedHashTable.search(busca[i])){encontrados++;}
        }
        System.out.printf("# Endereçamento Fechado #\n" +
                        "Fator de carga atual: %.2f\n" +
                        "Número de colisões: %d\n" + 
                        "Tamanho atual da tabela: %d\n" +
                        "Elementos achados: %d\n\n", 
                        closedHashTable.getLoadFactor(), closedHashTable.getCollisions(), 
                        closedHashTable.getSize(), encontrados
        );
        closedHashTable = null;
        encontrados = 0;

        //endereçamento aberto (linear)
        HTOpenAddressingLinearProbing linearHashTable = new HTOpenAddressingLinearProbing(database.length);
        for(int i = 0; i < database.length; i++){linearHashTable.insert(database[i]);}
        for (int i = 0; i < busca.length; i++){
            if(linearHashTable.search(busca[i])){encontrados++;}
        }
        System.out.printf("# Endereçamento Aberto (linear) #\n" +
                        "Rehashs: %d\n" +
                        "Número de colisões: %d\n\n",
                        linearHashTable.getRehashCount(), 
                        linearHashTable.getCollisions()
        );
        linearHashTable = null;
        encontrados = 0;

        //endereçamento aberto (double)
        HTOpenAddressingDouble doubleHashTable = new HTOpenAddressingDouble(database.length);
        for(int i = 0; i < database.length; i++){doubleHashTable.insert(database[i]);}
        for (int i = 0; i < busca.length; i++){
            if(doubleHashTable.search(busca[i])){encontrados++;}
        }
        System.out.printf("# Endereçamento Aberto (double) #\n" +
                        "Rehashs: %d\n" +
                        "Número de colisões: %d\n\n",
                        doubleHashTable.getRehashCount(), 
                        doubleHashTable.getCollisions()
        );
        doubleHashTable = null;
        encontrados = 0;

        //endereçamento aberto (quadratica)
        HTOpenAddressingQuadraticProbing quadraticHashTable = new HTOpenAddressingQuadraticProbing(database.length);
        for(int i = 0; i < database.length; i++){quadraticHashTable.insert(database[i]);}
        for (int i = 0; i < busca.length; i++){
            if(quadraticHashTable.search(busca[i])){encontrados++;}
        }
        System.out.printf("# Endereçamento Aberto (quadratica) #\n" +
                    "Rehashs: %d\n" +
                    "Número de colisões: %d\n\n",
                    quadraticHashTable.getRehashCount(),
                    quadraticHashTable.getCollisions()
        );
        quadraticHashTable = null;
        encontrados = 0;
    }

    public static int[] lerArquivo(String nome, int qtd) {
        String path = DATABASE_PATH + nome + ".txt";
        int[] data = new int[qtd];

        try (BufferedReader arquivo = new BufferedReader(new FileReader(path))) {
            String number;
            int i = 0;
            while ((number = arquivo.readLine()) != null && i < qtd) {
                data[i] = Integer.parseInt(number.trim());
                i++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao extrair informações");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter número: " + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    public static void gerarDadosAleatorios(int qtdElementos, String nome, int intervalo){
        String path = DATABASE_PATH + nome + ".txt";
        Set<Integer> numeros = new HashSet<>();
        Random random = new Random();

        while (numeros.size() < qtdElementos) {
            int numero = random.nextInt(intervalo+1);
            numeros.add(numero);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int numero : numeros) {
                writer.write(numero + "\n");
            }
            System.out.println("Arquivo gerado com sucesso em: " + path);
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo: " + e.getMessage());
        }
    }

    public static void gerarDadosRepetidos(int qtdElementos, String nome, int intervalo){
        String path = DATABASE_PATH + nome + ".txt";
        ArrayList<Integer> numeros = new ArrayList<>();
        Random random = new Random();

        while (numeros.size() < qtdElementos) {
            int numero = random.nextInt(intervalo+1);
            numeros.add(numero);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int numero : numeros) {
                writer.write(numero + "\n");
            }
            System.out.println("Arquivo gerado com sucesso em: " + path);
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo: " + e.getMessage());
        }
    }
}
