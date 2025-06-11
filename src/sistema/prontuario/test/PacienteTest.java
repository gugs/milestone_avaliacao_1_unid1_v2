package sistema.prontuario.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PacienteTest {
    private Class<?> pacienteClass;
    private Class<?> exameClass;

    @BeforeEach
    public void setUp() throws Exception {
        try {
            pacienteClass = Class.forName("sistema.prontuario.model.Paciente");
            exameClass = Class.forName("sistema.prontuario.model.Exame");
            assertTrue(java.io.Serializable.class.isAssignableFrom(pacienteClass), "Paciente deve implementar Serializable");
        } catch (ClassNotFoundException e) {
            fail("Classe Paciente não encontrada no pacote sistema.prontuario.model");
        }
    }

    @Test
    public void testPacienteClassStructure() throws Exception {
        // Verificar atributos
        assertTrue(hasField(pacienteClass, "cpf", String.class, true), "Atributo cpf (String) deve estar presente e ser privado");
        assertTrue(hasField(pacienteClass, "nome", String.class, true), "Atributo nome (String) deve estar presente e ser privado");
        assertTrue(hasField(pacienteClass, "peso", double.class, true), "Atributo peso (double) deve estar presente e ser privado");
        assertTrue(hasField(pacienteClass, "altura", double.class, true), "Atributo altura (double) deve estar presente e ser privado");
        assertTrue(hasField(pacienteClass, "sexo", String.class, true), "Atributo sexo (String) deve estar presente e ser privado");
        assertTrue(hasField(pacienteClass, "idade", int.class, true), "Atributo idade (int) deve estar presente e ser privado");
        assertTrue(hasField(pacienteClass, "exames", ArrayList.class, true), "Atributo exames (ArrayList) deve estar presente e ser privado");

        // Verificar serialVersionUID
        assertTrue(hasField(pacienteClass, "serialVersionUID", long.class, true, true, true), 
                   "Atributo serialVersionUID (long, static, final) deve estar presente");

        // Verificar métodos
        assertTrue(hasMethod(pacienteClass, "getCpf", new Class[]{}), "Método getCpf deve estar presente");
        assertTrue(hasMethod(pacienteClass, "setCpf", new Class[]{String.class}), "Método setCpf deve estar presente");
        assertTrue(hasMethod(pacienteClass, "getNome", new Class[]{}), "Método getNome deve estar presente");
        assertTrue(hasMethod(pacienteClass, "setNome", new Class[]{String.class}), "Método setNome deve estar presente");
        assertTrue(hasMethod(pacienteClass, "getPeso", new Class[]{}), "Método getPeso deve estar presente");
        assertTrue(hasMethod(pacienteClass, "setPeso", new Class[]{double.class}), "Método setPeso deve estar presente");
        assertTrue(hasMethod(pacienteClass, "getAltura", new Class[]{}), "Método getAltura deve estar presente");
        assertTrue(hasMethod(pacienteClass, "setAltura", new Class[]{double.class}), "Método setAltura deve estar presente");
        assertTrue(hasMethod(pacienteClass, "getSexo", new Class[]{}), "Método getSexo deve estar presente");
        assertTrue(hasMethod(pacienteClass, "setSexo", new Class[]{String.class}), "Método setSexo deve estar presente");
        assertTrue(hasMethod(pacienteClass, "getIdade", new Class[]{}), "Método getIdade deve estar presente");
        assertTrue(hasMethod(pacienteClass, "setIdade", new Class[]{int.class}), "Método setIdade deve estar presente");
        assertTrue(hasMethod(pacienteClass, "getExames", new Class[]{}), "Método getExames deve estar presente");
        assertTrue(hasMethod(pacienteClass, "setExames", new Class[]{ArrayList.class}), "Método setExames deve estar presente");
        assertTrue(hasMethod(pacienteClass, "adicionarExame", new Class[]{exameClass}), "Método adicionarExame deve estar presente");
        assertTrue(hasMethod(pacienteClass, "removerExame", new Class[]{String.class}), "Método removerExame deve estar presente");
        assertTrue(hasMethod(pacienteClass, "localizarExamePorId", new Class[]{String.class}), "Método localizarExamePorId deve estar presente");
        assertTrue(hasMethod(pacienteClass, "atualizarExame", new Class[]{exameClass}), "Método atualizarExame deve estar presente");
        assertTrue(hasMethod(pacienteClass, "calcularIMC", new Class[]{}), "Método calcularIMC deve estar presente");
        assertTrue(hasMethod(pacienteClass, "calcularBMR", new Class[]{}), "Método calcularBMR deve estar presente");
        assertTrue(hasMethod(pacienteClass, "calcularPesoIdeal", new Class[]{}), "Método calcularPesoIdeal deve estar presente");
        assertTrue(hasMethod(pacienteClass, "toString", new Class[]{}), "Método toString deve estar presente");
        assertTrue(hasMethod(pacienteClass, "equals", new Class[]{Object.class}), "Método equals deve estar presente");
        assertTrue(hasMethod(pacienteClass, "hashCode", new Class[]{}), "Método hashCode deve estar presente");
    }

    @Test
    public void testConstrutorPadrao() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor().newInstance();
        Field examesField = pacienteClass.getDeclaredField("exames");
        examesField.setAccessible(true);
        assertEquals(0, ((ArrayList<?>) examesField.get(paciente)).size(), "Exames deve ser inicializado como vazio");
        assertEquals(0.0, pacienteClass.getMethod("getPeso").invoke(paciente), "Peso deve ser inicializado como 0.0");
        assertEquals(0.0, pacienteClass.getMethod("getAltura").invoke(paciente), "Altura deve ser inicializada como 0.0");
        assertEquals("", pacienteClass.getMethod("getSexo").invoke(paciente), "Sexo deve ser inicializado como vazio");
        assertEquals(0, pacienteClass.getMethod("getIdade").invoke(paciente), "Idade deve ser inicializada como 0");
    }

    @Test
    public void testConstrutorComArgumentos() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        assertEquals("12345678901", pacienteClass.getMethod("getCpf").invoke(paciente), "CPF deve ser inicializado corretamente");
        assertEquals("Ana", pacienteClass.getMethod("getNome").invoke(paciente), "Nome deve ser inicializado corretamente");
        assertEquals(70.0, pacienteClass.getMethod("getPeso").invoke(paciente), "Peso deve ser inicializado corretamente");
        assertEquals(1.65, pacienteClass.getMethod("getAltura").invoke(paciente), "Altura deve ser inicializada corretamente");
        assertEquals("F", pacienteClass.getMethod("getSexo").invoke(paciente), "Sexo deve ser inicializado corretamente");
        assertEquals(30, pacienteClass.getMethod("getIdade").invoke(paciente), "Idade deve ser inicializada corretamente");
        assertEquals(0, ((ArrayList<?>) pacienteClass.getMethod("getExames").invoke(paciente)).size(), "Exames deve ser inicializado como vazio");
    }

    @Test
    public void testSettersValidations() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor().newInstance();

        // Testar setCpf
        Method setCpf = pacienteClass.getMethod("setCpf", String.class);
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setCpf.invoke(paciente, new Object[]{null}),
                     "setCpf com null deve lançar IllegalArgumentException");
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setCpf.invoke(paciente, ""),
                     "setCpf com string vazia deve lançar IllegalArgumentException");

        // Testar setNome
        Method setNome = pacienteClass.getMethod("setNome", String.class);
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setNome.invoke(paciente, new Object[]{null}),
                     "setNome com null deve lançar IllegalArgumentException");
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setNome.invoke(paciente, ""),
                     "setNome com string vazia deve lançar IllegalArgumentException");

        // Testar setPeso
        Method setPeso = pacienteClass.getMethod("setPeso", double.class);
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setPeso.invoke(paciente, 0.0),
                     "setPeso com valor <= 0 deve lançar IllegalArgumentException");
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setPeso.invoke(paciente, -1.0),
                     "setPeso com valor negativo deve lançar IllegalArgumentException");

        // Testar setAltura
        Method setAltura = pacienteClass.getMethod("setAltura", double.class);
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setAltura.invoke(paciente, 0.0),
                     "setAltura com valor <= 0 deve lançar IllegalArgumentException");
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setAltura.invoke(paciente, -1.0),
                     "setAltura com valor negativo deve lançar IllegalArgumentException");

        // Testar setSexo
        Method setSexo = pacienteClass.getMethod("setSexo", String.class);
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setSexo.invoke(paciente, new Object[]{null}),
                     "setSexo com null deve lançar IllegalArgumentException");
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setSexo.invoke(paciente, "X"),
                     "setSexo com valor inválido deve lançar IllegalArgumentException");

        // Testar setIdade
        Method setIdade = pacienteClass.getMethod("setIdade", int.class);
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> setIdade.invoke(paciente, -1),
                     "setIdade com valor negativo deve lançar IllegalArgumentException");
    }

    @Test
    public void testAdicionarExame() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Object exame = exameClass.getDeclaredConstructor(String.class, String.class, boolean.class, LocalDateTime.class)
                .newInstance("EX001", "Normal", true, LocalDateTime.now());

        Method adicionarExame = pacienteClass.getMethod("adicionarExame", exameClass);
        adicionarExame.invoke(paciente, exame);

        Field examesField = pacienteClass.getDeclaredField("exames");
        examesField.setAccessible(true);
        ArrayList<?> exames = (ArrayList<?>) examesField.get(paciente);
        assertEquals(1, exames.size(), "O exame deve ser adicionado ao ArrayList");
        assertEquals(exame, exames.get(0), "O exame adicionado deve ser o mesmo");
    }

    @Test
    public void testAdicionarExameNulo() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Method adicionarExame = pacienteClass.getMethod("adicionarExame", exameClass);
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> adicionarExame.invoke(paciente, new Object[]{null}),
                     "Adicionar exame nulo deve lançar IllegalArgumentException");
    }

    @Test
    public void testRemoverExame() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Object exame = exameClass.getDeclaredConstructor(String.class, String.class, boolean.class, LocalDateTime.class)
                .newInstance("EX001", "Normal", true, LocalDateTime.now());

        Method adicionarExame = pacienteClass.getMethod("adicionarExame", exameClass);
        adicionarExame.invoke(paciente, exame);

        Method removerExame = pacienteClass.getMethod("removerExame", String.class);
        removerExame.invoke(paciente, "EX001");

        Field examesField = pacienteClass.getDeclaredField("exames");
        examesField.setAccessible(true);
        ArrayList<?> exames = (ArrayList<?>) examesField.get(paciente);
        assertEquals(0, exames.size(), "O exame deve ser removido do ArrayList");
    }

    @Test
    public void testLocalizarExamePorId() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Object exame = exameClass.getDeclaredConstructor(String.class, String.class, boolean.class, LocalDateTime.class)
                .newInstance("EX001", "Normal", true, LocalDateTime.now());

        Method adicionarExame = pacienteClass.getMethod("adicionarExame", exameClass);
        adicionarExame.invoke(paciente, exame);

        Method localizarExame = pacienteClass.getMethod("localizarExamePorId", String.class);
        Object exameLocalizado = localizarExame.invoke(paciente, "EX001");
        assertNotNull(exameLocalizado, "O exame deve ser localizado por ID");
        assertEquals("EX001", exameClass.getMethod("getIdExame").invoke(exameLocalizado), "ID do exame localizado deve ser correto");

        exameLocalizado = localizarExame.invoke(paciente, "EX002");
        assertNull(exameLocalizado, "Deve retornar null para ID de exame inexistente");
    }

    @Test
    public void testAtualizarExame() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Object exame = exameClass.getDeclaredConstructor(String.class, String.class, boolean.class, LocalDateTime.class)
                .newInstance("EX001", "Normal", true, LocalDateTime.now());

        Method adicionarExame = pacienteClass.getMethod("adicionarExame", exameClass);
        adicionarExame.invoke(paciente, exame);

        Object novoExame = exameClass.getDeclaredConstructor(String.class, String.class, boolean.class, LocalDateTime.class)
                .newInstance("EX001", "Anormal", false, LocalDateTime.now());
        Method atualizarExame = pacienteClass.getMethod("atualizarExame", exameClass);
        atualizarExame.invoke(paciente, novoExame);

        Method localizarExame = pacienteClass.getMethod("localizarExamePorId", String.class);
        Object exameLocalizado = localizarExame.invoke(paciente, "EX001");
        Field resultadoField = exameClass.getDeclaredField("resultado");
        resultadoField.setAccessible(true);
        assertEquals("Anormal", resultadoField.get(exameLocalizado), "O resultado do exame deve ser atualizado");
    }

    @Test
    public void testCalcularIMC() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Method calcularIMC = pacienteClass.getMethod("calcularIMC");
        double imcEsperado = 70.0 / (1.65 * 1.65);
        assertEquals(imcEsperado, (double) calcularIMC.invoke(paciente), 0.01, "IMC deve ser calculado corretamente");
    }

    @Test
    public void testCalcularIMCPesoInvalido() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 80.0, 0.0, "F", 30);
        Method calcularIMC = pacienteClass.getMethod("calcularIMC");
        assertThrows(java.lang.reflect.InvocationTargetException.class, () -> calcularIMC.invoke(paciente),
                     "Calcular IMC com altura inválida deve lançar IllegalStateException");
    }

    @Test
    public void testCalcularBMRFeminino() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Method calcularBMR = pacienteClass.getMethod("calcularBMR");
        double alturaCm = 1.65 * 100;
        double bmrEsperado = (10 * 70.0) + (6.25 * alturaCm) - (5 * 30) - 161;
        assertEquals(bmrEsperado, (double) calcularBMR.invoke(paciente), 0.01, "BMR deve ser calculado corretamente para sexo feminino");
    }

    @Test
    public void testCalcularBMRMasculino() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "João", 70.0, 1.65, "M", 40);
        Method calcularBMR = pacienteClass.getMethod("calcularBMR");
        double alturaCm = 1.65 * 100;
        double bmrEsperado = (10 * 70.0) + (6.25 * alturaCm) - (5 * 40) + 5;
        assertEquals(bmrEsperado, (double) calcularBMR.invoke(paciente), 0.01, "BMR deve ser calculado corretamente para sexo masculino");
    }

    @Test
    public void testCalcularBMRDadosInvalidos() throws Exception {
        Method calcularBMR = pacienteClass.getMethod("calcularBMR");

        // Caso 1: Peso inválido (0.0)
        Object paciente1 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Field pesoField = pacienteClass.getDeclaredField("peso");
        pesoField.setAccessible(true);
        pesoField.setDouble(paciente1, 0.0);
        try {
            calcularBMR.invoke(paciente1);
            fail("Deveria lançar IllegalStateException para peso inválido");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException, 
                       "Exceção deve ser IllegalStateException para peso inválido");
        }

        // Caso 2: Altura inválida (0.0)
        Object paciente2 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Field alturaField = pacienteClass.getDeclaredField("altura");
        alturaField.setAccessible(true);
        alturaField.setDouble(paciente2, 0.0);
        try {
            calcularBMR.invoke(paciente2);
            fail("Deveria lançar IllegalStateException para altura inválida");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException, 
                       "Exceção deve ser IllegalStateException para altura inválida");
        }

        // Caso 3: Idade inválida (-1)
        Object paciente3 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Field idadeField = pacienteClass.getDeclaredField("idade");
        idadeField.setAccessible(true);
        idadeField.setInt(paciente3, -1);
        try {
            calcularBMR.invoke(paciente3);
            fail("Deveria lançar IllegalStateException para idade inválida");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException, 
                       "Exceção deve ser IllegalStateException para idade inválida");
        }

        // Caso 4: Sexo inválido ("X")
        Object paciente4 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Field sexoField = pacienteClass.getDeclaredField("sexo");
        sexoField.setAccessible(true);
        sexoField.set(paciente4, "X");
        try {
            calcularBMR.invoke(paciente4);
            fail("Deveria lançar IllegalStateException para sexo inválido");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException, 
                       "Exceção deve ser IllegalStateException para sexo inválido");
        }

        // Caso 5: Sexo nulo
        Object paciente5 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        sexoField.set(paciente5, null);
        try {
            calcularBMR.invoke(paciente5);
            fail("Deveria lançar IllegalStateException para sexo nulo");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException, 
                       "Exceção deve ser IllegalStateException para sexo nulo");
        }
    }

    @Test
    public void testCalcularPesoIdealFeminino() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Method calcularPesoIdeal = pacienteClass.getMethod("calcularPesoIdeal");
        double alturaCm = 1.65 * 100;
        double pesoIdealEsperado = alturaCm - 100 - ((alturaCm - 150) / 2.0);
        assertEquals(pesoIdealEsperado, (double) calcularPesoIdeal.invoke(paciente), 0.01, "Peso ideal deve ser calculado corretamente para sexo feminino");
    }

    @Test
    public void testCalcularPesoIdealMasculino() throws Exception {
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "João", 70.0, 1.65, "M", 40);
        Method calcularPesoIdeal = pacienteClass.getMethod("calcularPesoIdeal");
        double alturaCm = 1.65 * 100;
        double pesoIdealEsperado = alturaCm - 100 - ((alturaCm - 150) / 4.0);
        assertEquals(pesoIdealEsperado, (double) calcularPesoIdeal.invoke(paciente), 0.01, "Peso ideal deve ser calculado corretamente para sexo masculino");
    }

    @Test
    public void testCalcularPesoIdealDadosInvalidos() throws Exception {
        Method calcularPesoIdeal = pacienteClass.getMethod("calcularPesoIdeal");

        // Caso 1: Altura inválida (0.0)
        Object paciente1 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Field alturaField = pacienteClass.getDeclaredField("altura");
        alturaField.setAccessible(true);
        alturaField.setDouble(paciente1, 0.0);
        try {
            calcularPesoIdeal.invoke(paciente1);
            fail("Deveria lançar IllegalStateException para altura inválida");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException, 
                       "Exceção deve ser IllegalStateException para altura inválida");
        }

        // Caso 2: Sexo inválido ("X")
        Object paciente2 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Field sexoField = pacienteClass.getDeclaredField("sexo");
        sexoField.setAccessible(true);
        sexoField.set(paciente2, "X");
        try {
            calcularPesoIdeal.invoke(paciente2);
            fail("Deveria lançar IllegalStateException para sexo inválido");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException, 
                       "Exceção deve ser IllegalStateException para sexo inválido");
        }

        // Caso 3: Sexo nulo
        Object paciente3 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        sexoField.set(paciente3, null);
        try {
            calcularPesoIdeal.invoke(paciente3);
            fail("Deveria lançar IllegalStateException para sexo nulo");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException, 
                       "Exceção deve ser IllegalStateException para sexo nulo");
        }
    }

    @Test
    public void testEquals() throws Exception {
        Object p1 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);
        Object p2 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "João", 80.0, 1.80, "M", 40);
        Method equals = pacienteClass.getMethod("equals", Object.class);
        assertTrue((boolean) equals.invoke(p1, p2), "Pacientes com mesmo CPF devem ser iguais");

        Object p3 = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("98765432109", "João", 80.0, 1.80, "M", 40);
        assertFalse((boolean) equals.invoke(p1, p3), "Pacientes com CPFs diferentes não devem ser iguais");
    }

    // Métodos auxiliares para reflexão
    private boolean hasField(Class<?> clazz, String fieldName, Class<?> fieldType, boolean isPrivate) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType().equals(fieldType) && (!isPrivate || Modifier.isPrivate(field.getModifiers()));
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    private boolean hasField(Class<?> clazz, String fieldName, Class<?> fieldType, boolean isPrivate, boolean isStatic, boolean isFinal) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            int modifiers = field.getModifiers();
            return field.getType().equals(fieldType) &&
                   (!isPrivate || Modifier.isPrivate(modifiers)) &&
                   (!isStatic || Modifier.isStatic(modifiers)) &&
                   (!isFinal || Modifier.isFinal(modifiers));
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    private boolean hasMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            clazz.getMethod(methodName, parameterTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}