# DIO Bank - Sistema Bancário Digital

## Sobre o Projeto

Este projeto é uma aplicação de console em Java que simula um sistema bancário. Ele foi desenvolvido como parte de um desafio de projeto do curso de Java da **DIO**, aplicado e orientado pelo desenvolvedor **José Luiz Abreu Cardoso Junior**.

O objetivo principal foi consolidar e aplicar na prática os pilares da Programação Orientada a Objetos (POO), além de compreender as Estruturas de Dados utilizadas no Java.

---

## Funcionalidades

* **Gestão de Contas:**
    * Criação de conta com saldo inicial e uma ou mais chaves PIX.
    * Depósitos em contas existentes.
    * Saques com verificação de saldo.
    * Transferências entre contas via PIX.
    * Listagem de todas as contas criadas.
* **Carteira de Investimentos:**
    * Criação de tipos de investimento com taxa de rendimento.
    * Criação de uma carteira de investimentos vinculada a uma conta.
    * Aporte de valores da conta para a carteira de investimentos.
    * Resgate de valores da carteira de volta para a conta.
    * Simulação de rendimentos sobre o saldo investido.
* **Histórico de Transações:**
    * Visualização do extrato detalhado de uma conta, agrupado por data e hora.

---

## Arquitetura e Conceitos de POO Aplicados

Este projeto foi estruturado para demonstrar a aplicação prática dos quatro pilares da Programação Orientada a Objetos.

### 1. Herança
A herança foi utilizada para criar uma hierarquia de "carteiras" (`Wallet`). Existe uma classe base abstrata `Wallet` que define comportamentos e atributos comuns.
* `AccountWallet` herda de `Wallet` e adiciona funcionalidades específicas de uma conta corrente.
* `InvestmentWallet` também herda de `Wallet`, representando uma carteira de investimentos.

### 2. Polimorfismo
O polimorfismo é evidente no método `toString()`. Cada classe (`Wallet`, `AccountWallet`, `InvestmentWallet`) possui sua própria implementação, permitindo que a mesma chamada (`System.out.println(wallet)`) produza saídas diferentes e adequadas.

### 3. Encapsulamento
O encapsulamento é garantido pelo uso de modificadores de acesso (`private`, `protected`). Os atributos, como a lista `money` em `Wallet`, são protegidos, e o acesso a eles é feito através de métodos públicos (`getFunds()`, `addMoney()`), garantindo a integridade dos dados.

### 4. Abstração
A classe `Wallet` é `abstract`, pois não faz sentido existir uma "carteira genérica". Ela apenas define um contrato comum que suas classes filhas devem seguir, simplificando o modelo.

---

## Estruturas de Dados e Decisões de Implementação

A escolha das estruturas de dados foi fundamental para a funcionalidade e eficiência do sistema.

* ### `List`
    A `List` (principalmente `ArrayList`) é a estrutura central do projeto. Ela é usada para armazenar coleções de objetos de forma dinâmica, como:
    * A lista de `Money` dentro de cada `Wallet`, onde o tamanho da lista representa o saldo em centavos.
    * A lista de `AccountWallet` no `AccountRepository`, simulando a persistência de contas em memória.
    * A lista de chaves `pix` em `AccountWallet`.

* ### `Stream`
    A API de Streams do Java foi amplamente utilizada para processar coleções de dados de forma declarativa e funcional, tornando o código mais limpo e legível. Exemplos notáveis incluem:
    * **Filtragem e Busca:** No método `findByPix`, um `stream` é usado para filtrar a lista de contas e encontrar aquela que contém a chave PIX desejada.
    * **Transformação e Agregação (`flatMap`):** O método `getFinancialTransactions` usa `flatMap` para transformar uma lista de listas de históricos (`List<List<MoneyAudit>>`) em uma única lista (`List<MoneyAudit>`), simplificando a obtenção do extrato completo.
    * **Geração de Dados:** O método `generateMoney` utiliza `Stream.generate()` para criar múltiplos objetos `Money` de forma eficiente.

* ### `Map`
    O `Map` foi crucial na implementação do histórico de transações (`getHistory`). Foi utilizado `Collectors.groupingBy` para agrupar as transações por data e hora (`OffsetDateTime`), resultando em um `Map<OffsetDateTime, List<MoneyAudit>>`. Essa estrutura permite organizar o extrato de forma cronológica e coesa, que é exatamente o que um usuário esperaria de um extrato bancário.

---

## Tecnologias Utilizadas

* **Java 21:** Versão da linguagem utilizada para o desenvolvimento.
* **Gradle:** Ferramenta de automação de build.
* **Lombok:** Biblioteca para reduzir código boilerplate.
* **Records:** Utilizados em `MoneyAudit` e `Investment` para criar classes de dados imutáveis.
* **Enums:** O `BankService` é um `enum` para garantir a segurança de tipos.

---

## Como Executar o Projeto

**Pré-requisitos:**
* JDK 21 ou superior instalado.
* O Gradle Wrapper (`gradlew`) já está incluído no projeto.

**Passos:**

1.  Clone o repositório:
    ```bash
    git clone https://github.com/JuanP-04/dio-java-bank.git)
    ```

2.  Navegue até o diretório do projeto:
    ```bash
    cd dio-java-bank
    ```

3.  Execute a aplicação usando o Gradle Wrapper:
    ```bash
    ./gradlew run
    ```
