# 🧠 Padrão de Projeto: Flyweight aplicado em Ordens de Serviço

## 📌 O que é o padrão Flyweight?

O **Flyweight** é um padrão estrutural que visa **compartilhar objetos imutáveis** para economizar memória e melhorar desempenho, principalmente quando se trabalha com **muitas instâncias idênticas ou similares**.

---

## 🎯 Contexto no Sistema `MecanicaBase`

O sistema simula uma **oficina mecânica**, onde:

- Cada `OrdemDeServico` pode conter várias `PecaItem`;
- Muitas ordens utilizam as **mesmas peças** (ex: velas, parafusos);
- Os dados de `Peca` (nome, valor, etc.) são **imutáveis** e reutilizáveis.

### Situação identificada:

Durante benchmarks com **grandes volumes de ordens (10 mil a 100 mil)**, observou-se criação redundante de objetos `Peca`, mesmo que com os mesmos atributos.

---

## ✅ Solução com Flyweight em `Peca`

### Estratégia aplicada:

- Criada a classe `PecaFactory`, que armazena um **cache de peças compartilhadas** com base em `nome + valor`;
- A entidade `PecaItem` armazena o **contexto** (ex: quantidade, ordem associada);
- Evita-se a criação repetida de objetos `Peca` com UUIDs distintos.

### Trecho chave de `PecaFactory`:

```java
public class PecaFactory {
    private static final Map<String, Peca> cache = new HashMap<>();

    public static Peca getPeca(String nome, float valor) {
        String chave = nome.toLowerCase() + "|" + valor;
        return cache.computeIfAbsent(chave, k -> new Peca(nome, valor, 0));
    }

    public static int getTotalInstanciasCompartilhadas() {
        return cache.size();
    }

    public static void limparCache() {
        cache.clear();
    }
}
```

---

## 🧪 Benchmark implementado

### Objetivo:

Avaliar a diferença de performance (tempo e memória) entre:

- Criação de `OrdemDeServico` com múltiplos `PecaItem`;
- Usando `Peca` **com Flyweight** (compartilhada) versus **sem Flyweight** (cada uma única).

### Detalhes técnicos:

- Executado com volumes de 10.000, 50.000 e 100.000 ordens de serviço;
- Cada ordem associa de 1 a 2 peças;
- As peças vêm de uma lista base de 20 itens criados via `PecaFactory`;
- Resultados salvos em `data/medicoes_ordem.txt` e acessíveis pelo menu principal.

---

### 📊 Desempenho — Flyweight **COM** × **SEM**

| OS      | Tempo&nbsp;COM (ms) | Tempo&nbsp;SEM (ms) | Δ&nbsp;Tempo | Memória&nbsp;COM (KB) | Memória&nbsp;SEM (KB) | Δ&nbsp;Memória | Peças&nbsp;compartilhadas |
| ------- | ------------------- | ------------------- | ------------ | --------------------- | --------------------- | -------------- | ------------------------- |
| 10 000  | 1 561               | 2 519               | ↓ 38 %       | 14 615                | 21 120                | ↓ 31 %         | 1 000                     |
| 50 000  | 15 856              | 134 528             | ↓ 88 %       | 73 641                | 107 073               | ↓ 31 %         | 1 000                     |
| 100 000 | 73 961              | 551 775             | ↓ 87 %       | 150 001               | 214 208               | ↓ 30 %         | 1 000                     |

> **Flyweight traz ganhos claros a partir de ~50 k objetos**, poupando **≈ 30 % de memória** e até **≈ 90 % de tempo** quando há muitas repetições de peças.

---

## 🧠 Conclusão

- O padrão Flyweight foi aplicado **especificamente à classe `Peca`**, reutilizada em várias ordens;
- Evitamos a criação de objetos `Peca` duplicados, melhorando **uso de memória** e **tempo de execução**;
- O benchmark integrado permite **avaliar sinteticamente os ganhos**, e o sistema permanece extensível.
