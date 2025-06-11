# Atividade de Avaliação da Disciplina Programação Orientada a Objetos: Sistema de Prontuário Eletrônico Simplificado

## Objetivo

Desenvolver um sistema de prontuário eletrônico simplificado em Java, implementando as classes `Paciente`, `Exame` e `PersistenciaEmArquivo` no pacote `sistema.prontuario`. O sistema deve gerenciar pacientes e seus exames médicos, realizar cálculos de saúde (Índice de Massa Corporal - IMC, Metabolismo Basal - BMR e Peso Ideal) e persistir dados em arquivo. As classes devem seguir os conceitos de programação orientada a objetos (encapsulamento, associação, serialização) abordados na disciplina. A atividade será avaliada por testes automatizados JUnit 5, que utilizam Java Reflection para validar a estrutura e o comportamento das classes.

## Contexto

O sistema permite cadastrar pacientes, associar exames médicos, consultar, atualizar e remover dados, além de calcular métricas de saúde com base nos atributos do paciente. Os testes fornecidos no pacote `sistema.prontuario.test` (`PacienteTest.java`, `ExameTest.java`, `PersistenciaEmArquivoTest.java` e `SistemaProntuarioTestSuite.java`) verificarão:
- A estrutura das classes (atributos, métodos, modificadores) usando Java Reflection.
- O comportamento funcional (validações, cálculos, persistência).
Os alunos devem implementar as classes conforme especificado, garantindo que os testes passem sem erros.

## Requisitos Gerais

- **Pacotes**:
  - `sistema.prontuario.model`: Contém `Paciente.java` e `Exame.java`.
  - `sistema.prontuario.persistencia`: Contém `PersistenciaEmArquivo.java`.
- **Serializable**: As classes `Paciente` e `Exame` devem implementar `java.io.Serializable` com um atributo `private static final long serialVersionUID` (ex.: `1L` para `Paciente` e `Exame`).
- **Encapsulamento**: Todos os atributos devem ser privados, com *getters* e *setters* públicos, exceto quando especificado.
- **Tipos de Dados**:
  - `String`: Para CPF, nome, sexo ("M" ou "F"), resultados de exames e `idExame`.
  - `double`: Para peso (kg) e altura (m).
  - `int`: Para idade (anos).
  - `java.time.LocalDateTime`: Para data de realização do exame.
  - `java.util.ArrayList`: Para a lista de exames (`Paciente`) e pacientes (`PersistenciaEmArquivo`).
- **Validações**: Métodos devem lançar exceções (`IllegalArgumentException`, `IllegalStateException`) conforme especificado.
- **Cálculos de Saúde**:
  - **IMC**: `peso (kg) / altura² (m²)`.
  - **BMR (Mifflin-St Jeor)**:
    - Homens: `(10 × peso em kg) + (6.25 × altura em cm) – (5 × idade em anos) + 5`.
    - Mulheres: `(10 × peso em kg) + (6.25 × altura em cm) – (5 × idade em anos) – 161`.
  - **Peso Ideal (Lorentz)**:
    - Homens: `altura (cm) - 100 - ((altura (cm) - 150) / 4)`.
    - Mulheres: `altura (cm) - 100 - ((altura (cm) - 150) / 2)`.
- **Testes**:
  - Os testes JUnit usam Java Reflection para verificar a estrutura das classes (ex.: atributos, métodos, modificadores).
  - Testes de casos inválidos (ex.: `calcularBMR`, `calcularPesoIdeal`) configuram valores diretamente nos campos via reflexão, esperando `IllegalStateException`.
  - Execute a suíte `SistemaProntuarioTestSuite.java` para validar a implementação.

## Especificações das Classes

### 1. Classe `Paciente`

Representa um paciente com atributos pessoais e uma lista de exames médicos.

#### Atributos

- `cpf`: `String`, privado, identificador único do paciente.
- `nome`: `String`, privado, nome completo do paciente.
- `peso`: `double`, privado, peso em kg.
- `altura`: `double`, privado, altura em metros.
- `sexo`: `String`, privado, "M" (masculino) ou "F" (feminino).
- `idade`: `int`, privado, idade em anos.
- `exames`: `ArrayList<Exame>`, privado, lista de exames associados.
- `serialVersionUID`: `private static final long`, para serialização (ex.: `1L`).

#### Construtores

- **Padrão**: Inicializa `exames` como um `ArrayList` vazio, `peso` e `altura` como `0.0`, `sexo` como `""`, `idade` como `0`.
- **Com argumentos**: Recebe `cpf`, `nome`, `peso`, `altura`, `sexo`, `idade`, inicializando `exames` como vazio.

#### Métodos

- **Getters e Setters**:
  - Para todos os atributos, públicos.
  - *Getter* de `exames` retorna uma cópia do `ArrayList` para proteger o encapsulamento.
  - Validações nos *setters*:
    - `setCpf`: Não permite `null` ou vazio (`IllegalArgumentException`).
    - `setNome`: Não permite `null` ou vazio (`IllegalArgumentException`).
    - `setPeso`: Deve ser > 0 (`IllegalArgumentException`).
    - `setAltura`: Deve ser > 0 (`IllegalArgumentException`).
    - `setSexo`: Deve ser "M" ou "F" (`IllegalArgumentException`).
    - `setIdade`: Deve ser >= 0 (`IllegalArgumentException`).
- `adicionarExame(Exame exame)`: Adiciona um exame se não for `null` e não existir na lista (`IllegalArgumentException` para `null`).
- `removerExame(String idExame)`: Remove o exame com o `idExame` especificado, se existir.
- `localizarExamePorId(String idExame)`: Retorna o exame com o `idExame` ou `null` se não encontrado.
- `atualizarExame(Exame novoExame)`: Substitui o exame com o mesmo `idExame`, se existir.
- `calcularIMC()`: Retorna o IMC. Lança `IllegalStateException` se `peso` ou `altura` <= 0.
- `calcularBMR()`: Retorna o BMR (Mifflin-St Jeor). Lança `IllegalStateException` se `peso`, `altura` ou `idade` <= 0 ou `sexo` não for "M"/"F". Mensagens de erro específicas para cada caso (ex.: "Peso deve ser maior que zero para calcular o BMR.").
- `calcularPesoIdeal()`: Retorna o Peso Ideal (Lorentz). Lança `IllegalStateException` se `altura` <= 0 ou `sexo` não for "M"/"F". Mensagens de erro específicas (ex.: "Altura deve ser maior que zero para calcular o peso ideal.").
- `toString()`: Retorna uma representação textual (ex.: `Paciente [cpf=12345678901, nome=Ana, peso=70.0, altura=1.65, sexo=F, idade=30, exames=2]`).
- `equals(Object o)`: Compara pacientes pelo `cpf`.
- `hashCode()`: Gera hash baseado no `cpf`.

#### Observações

- Implemente `Serializable` com `serialVersionUID`.
- Use `Objects.equals` e `Objects.hash` para `equals` e `hashCode`.
- Proteja o `ArrayList` de exames contra modificações externas.
- Os métodos `calcularBMR` e `calcularPesoIdeal` devem lançar `IllegalStateException` com mensagens específicas para cada validação.

### 2. Classe `Exame`

Representa um exame médico associado a um paciente.

#### Atributos

- `idExame`: `String`, privado, identificador único.
- `resultado`: `String`, privado, descrição do resultado (ex.: "Normal").
- `status`: `boolean`, privado, indica se o exame está ativo (`true`) ou inativo (`false`).
- `dataExame`: `LocalDateTime`, privado, data de realização.
- `serialVersionUID`: `private static final long`, para serialização (ex.: `1L`).

#### Construtores

- **Padrão**: Inicializa `resultado` como `""`, `status` como `true`, `dataExame` como `LocalDateTime.now()`.
- **Com argumentos**: Recebe `idExame`, `resultado`, `status`, `dataExame`. Se `resultado` ou `dataExame` forem `null`, usa `""` e `LocalDateTime.now()`.

#### Métodos

- **Getters e Setters**: Para todos os atributos, públicos.
  - `setIdExame`: Não permite `null` ou vazio (`IllegalArgumentException`).
  - `setResultado`: Aceita `null` (converte para `""`).
  - `setDataExame`: Aceita `null` (usa `LocalDateTime.now()`).
- `registrarResultado(String resultado)`: Define o resultado se o exame estiver ativo e o resultado não for `null`/vazio. Lança `IllegalStateException` (inativo) ou `IllegalArgumentException` (inválido).
- `validarExame()`: Define `status` como `true`.
- `invalidarExame()`: Define `status` como `false`.
- `toString()`: Retorna uma representação textual (ex.: `Exame [idExame=EX001, resultado=Normal, status=true, dataExame=2025-06-11T14:00:00]`).
- `equals(Object o)`: Compara exames pelo `idExame`.
- `hashCode()`: Gera hash baseado no `idExame`.

#### Observações

- Implemente `Serializable` com `serialVersionUID`.
- Validações devem ser rigorosas em `registrarResultado`.

### 3. Classe `PersistenciaEmArquivo`

Gerencia a persistência de pacientes em um arquivo `dados` na raiz do projeto.

#### Atributos

- `pacientes`: `ArrayList<Paciente>`, privado, lista de pacientes.
- `ARQUIVO`: `String`, `private static final`, valor `"dados"`.

#### Construtores

- **Padrão**: Inicializa `pacientes` como um `ArrayList` vazio.

#### Métodos

- `adicionarPaciente(Paciente paciente)`: Adiciona um paciente se não for `null` e não existir (verifica pelo `cpf`). Lança `IllegalArgumentException` para `null`.
- `removerPaciente(String cpf)`: Remove o paciente com o CPF especificado.
- `localizarPacientePorCpf(String cpf)`: Retorna o paciente com o CPF ou `null` se não encontrado.
- `atualizarPaciente(Paciente novoPaciente)`: Substitui o paciente com o mesmo CPF, se existir.
- `salvarEmArquivo()`: Salva a lista de pacientes no arquivo `dados` usando `ObjectOutputStream`. Trate exceções de I/O.
- `carregarDeArquivo()`: Carrega a lista de pacientes do arquivo `dados` usando `ObjectInputStream`. Se o arquivo não existir, inicializa `pacientes` como vazio.

#### Observações

- A classe não precisa implementar `Serializable`, pois apenas a lista `pacientes` é serializada.
- Use `ObjectOutputStream` e `ObjectInputStream` para serializar a lista de pacientes.
- Trate `IOException` e `ClassNotFoundException` adequadamente, inicializando `pacientes` como vazio em caso de erro.

## Instruções para Implementação

1. **Estrutura do Projeto**:
   - Importe o projeto `sistema_prontuario` no Eclipse.
   - Crie o pacote `sistema.prontuario.model` e implemente `Paciente.java` e `Exame.java`.
   - Crie o pacote `sistema.prontuario.persistencia` e implemente `PersistenciaEmArquivo.java`.
   - O pacote `sistema.prontuario.test` já contém os testes JUnit (`PacienteTest.java`, `ExameTest.java`, `PersistenciaEmArquivoTest.java`, `SistemaProntuarioTestSuite.java`).

2. **Configuração do Ambiente**:
   - **Java**: Use Java 8 ou superior.
   - **JUnit 5**:
     - No Eclipse, clique com o botão direito no projeto > `Build Path` > `Add Libraries` > `JUnit` > `JUnit 5`.
   - Verifique se não há erros de compilação antes de executar os testes.
   - Adicione as dependências do Java (pacotes `java.io`, `java.time`, `java.util`) ao projeto, se necessário.
   - Certifique-se de que o diretório do projeto tem permissões de escrita para criar o arquivo `dados`.

3. **Implementação das Classes**:
   - Siga as especificações detalhadas acima para cada classe.
   - Use Java Reflection nos testes para validar a estrutura das classes (atributos, métodos, modificadores).
   - Implemente validações rigorosas nos *setters* e métodos de cálculo.
   - Para `Paciente`, garanta que `calcularBMR` valida `idade <= 0` e que `calcularPesoIdeal` usa divisão de ponto flutuante (ex.: `4.0`, `2.0`).
   - Para `PersistenciaEmArquivo`, serialize apenas a lista `pacientes`, não a classe inteira.
   - Proteja o `ArrayList` de exames (`Paciente`) e pacientes (`PersistenciaEmArquivo`) contra modificações externas.

4. **Execução dos Testes**:
   - Clique com o botão direito em `SistemaProntuarioTestSuite.java` > `Run As` > `JUnit Test`.
   - Os testes verificarão:
     - **Estrutura**: Atributos (`cpf`, `nome`, `peso`, etc.), métodos, modificadores e `Serializable` (via Java Reflection).
     - **Funcionais**: Construtores, gerenciamento de exames, cálculos de saúde (IMC, BMR, Peso Ideal), persistência.
     - **Validações**: Exceções lançadas corretamente (`IllegalArgumentException`, `IllegalStateException`).
     - **Casos Inválidos**: Testes como `testCalcularBMRDadosInvalidos` e `testCalcularPesoIdealDadosInvalidos` usam reflexão para configurar valores inválidos diretamente nos campos, esperando `IllegalStateException`.
   - Analise os resultados dos testes para corrigir erros. Verifique mensagens de erro do JUnit para detalhes.

5. **Dicas**:
   - **Java Reflection**: Os testes usam reflexão para verificar a estrutura das classes e configurar valores inválidos. Certifique-se de que nomes, tipos e modificadores correspondam às especificações.
   - **Encapsulamento**: Use cópias defensivas em *getters* de `ArrayList` (ex.: `getExames`).
   - **Validações**: Teste casos inválidos (ex.: peso <= 0, sexo diferente de "M"/"F") para garantir exceções corretas.
   - **Serialização**: Use `Objects.equals` e `Objects.hash` para `equals` e `hashCode` consistentes. Apenas `Paciente` e `Exame` precisam de `Serializable`.
   - **Persistência**: Verifique se o arquivo `dados` é criado/lido na raiz do projeto. Delete-o entre execuções de teste para evitar interferências.
   - **Debugging**: Use mensagens de erro do JUnit para identificar falhas. Para erros de persistência, verifique permissões de escrita. Para falhas em cálculos, teste os métodos com valores válidos e inválidos manualmente.

## Critérios de Avaliação

- **Correção Estrutural (30%)**:
  - Atributos com nomes, tipos e modificadores corretos (verificados via reflexão).
  - Métodos com assinaturas corretas (ex.: `calcularIMC`, `adicionarExame`).
  - Implementação de `Serializable` com `serialVersionUID` para `Paciente` e `Exame`.
- **Correção Funcional (40%)**:
  - Passagem em todos os testes funcionais (construtores, gerenciamento de exames, cálculos, persistência).
  - Validações corretas com lançamento de exceções apropriadas.
- **Encapsulamento e Boas Práticas (20%)**:
  - Atributos privados com *getters*/*setters*.
  - Código limpo, com nomes claros e comentários quando necessário.
  - Tratamento adequado de exceções em `PersistenciaEmArquivo`.
- **Persistência (10%)**:
  - Correta serialização/deserialização da lista de pacientes no arquivo `dados`.
- **Nota Final**: Calculada como percentual de testes aprovados na suíte JUnit.

## Entrega

- A entrega deve ocorrer ao término da avaliação em sala de aula.
- Submeta o projeto completo (código-fonte e arquivo `dados`, se gerado) conforme instruções do professor.

## Observações Finais

- Certifique-se de que o projeto compila sem erros antes de executar os testes.
- Os testes JUnit são sensíveis a nomes de atributos/métodos. Siga exatamente as especificações.
- Para erros de persistência, verifique permissões de escrita no diretório do projeto.
- Consulte a documentação do Java para `Serializable`, `LocalDateTime`, `ArrayList` e `ObjectOutputStream`/`ObjectInputStream`.
- Em caso de dúvidas, analise as mensagens de erro dos testes JUnit ou consulte o professor.

**Boa sorte na implementação!**