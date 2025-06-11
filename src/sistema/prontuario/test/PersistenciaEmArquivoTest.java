package sistema.prontuario.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class PersistenciaEmArquivoTest {
    private Class<?> persistenciaClass;
    private Class<?> pacienteClass;

    @BeforeEach
    public void setUp() throws Exception {
        try {
            persistenciaClass = Class.forName("sistema.prontuario.persistencia.PersistenciaEmArquivo");
            pacienteClass = Class.forName("sistema.prontuario.model.Paciente");
        } catch (ClassNotFoundException e) {
            fail("Classe PersistenciaEmArquivo ou Paciente não encontrada nos pacotes corretos");
        }
    }

    @Test
    public void testPersistenciaEmArquivoClassStructure() throws Exception {
        // Verificar atributos
        assertTrue(hasField(persistenciaClass, "pacientes", ArrayList.class, true), 
                   "Atributo pacientes (ArrayList) deve estar presente e ser privado");
        assertTrue(hasField(persistenciaClass, "ARQUIVO", String.class, true, true, true), 
                   "Atributo ARQUIVO (String, private static final) deve estar presente");

        // Verificar métodos
        assertTrue(hasMethod(persistenciaClass, "adicionarPaciente", new Class[]{pacienteClass}), 
                   "Método adicionarPaciente deve estar presente");
        assertTrue(hasMethod(persistenciaClass, "removerPaciente", new Class[]{String.class}), 
                   "Método removerPaciente deve estar presente");
        assertTrue(hasMethod(persistenciaClass, "localizarPacientePorCpf", new Class[]{String.class}), 
                   "Método localizarPacientePorCpf deve estar presente");
        assertTrue(hasMethod(persistenciaClass, "atualizarPaciente", new Class[]{pacienteClass}), 
                   "Método atualizarPaciente deve estar presente");
        assertTrue(hasMethod(persistenciaClass, "salvarEmArquivo", new Class[]{}), 
                   "Método salvarEmArquivo deve estar presente");
        assertTrue(hasMethod(persistenciaClass, "carregarDeArquivo", new Class[]{}), 
                   "Método carregarDeArquivo deve estar presente");
    }

    @Test
    public void testAdicionarPaciente() throws Exception {
        Object persistencia = persistenciaClass.getDeclaredConstructor().newInstance();
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);

        Method adicionarPaciente = persistenciaClass.getMethod("adicionarPaciente", pacienteClass);
        adicionarPaciente.invoke(persistencia, paciente);

        Field pacientesField = persistenciaClass.getDeclaredField("pacientes");
        pacientesField.setAccessible(true);
        ArrayList<?> pacientes = (ArrayList<?>) pacientesField.get(persistencia);
        assertEquals(1, pacientes.size(), "Paciente deve ser adicionado ao ArrayList");
        assertEquals("12345678901", pacienteClass.getMethod("getCpf").invoke(pacientes.get(0)), 
                     "CPF do paciente adicionado deve ser correto");
        assertEquals("Ana", pacienteClass.getMethod("getNome").invoke(pacientes.get(0)), 
                     "Nome do paciente adicionado deve ser correto");
        assertEquals(70.0, pacienteClass.getMethod("getPeso").invoke(pacientes.get(0)), 
                     "Peso do paciente adicionado deve ser correto");
        assertEquals(1.65, pacienteClass.getMethod("getAltura").invoke(pacientes.get(0)), 
                     "Altura do paciente adicionado deve ser correto");
        assertEquals("F", pacienteClass.getMethod("getSexo").invoke(pacientes.get(0)), 
                     "Sexo do paciente adicionado deve ser correto");
        assertEquals(30, pacienteClass.getMethod("getIdade").invoke(pacientes.get(0)), 
                     "Idade do paciente adicionado deve ser correta");
    }

    @Test
    public void testAdicionarPacienteNulo() throws Exception {
        Object persistencia = persistenciaClass.getDeclaredConstructor().newInstance();
        Method adicionarPaciente = persistenciaClass.getMethod("adicionarPaciente", pacienteClass);
        try {
            adicionarPaciente.invoke(persistencia, new Object[]{null});
            fail("Deveria lançar IllegalArgumentException para paciente nulo");
        } catch (java.lang.reflect.InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException, 
                       "Exceção deve ser IllegalArgumentException para paciente nulo");
        }
    }

    @Test
    public void testRemoverPaciente() throws Exception {
        Object persistencia = persistenciaClass.getDeclaredConstructor().newInstance();
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);

        Method adicionarPaciente = persistenciaClass.getMethod("adicionarPaciente", pacienteClass);
        adicionarPaciente.invoke(persistencia, paciente);

        Method removerPaciente = persistenciaClass.getMethod("removerPaciente", String.class);
        removerPaciente.invoke(persistencia, "12345678901");

        Field pacientesField = persistenciaClass.getDeclaredField("pacientes");
        pacientesField.setAccessible(true);
        ArrayList<?> pacientes = (ArrayList<?>) pacientesField.get(persistencia);
        assertEquals(0, pacientes.size(), "Paciente deve ser removido do ArrayList");
    }

    @Test
    public void testLocalizarPacientePorCpf() throws Exception {
        Object persistencia = persistenciaClass.getDeclaredConstructor().newInstance();
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);

        Method adicionarPaciente = persistenciaClass.getMethod("adicionarPaciente", pacienteClass);
        adicionarPaciente.invoke(persistencia, paciente);

        Method localizarPaciente = persistenciaClass.getMethod("localizarPacientePorCpf", String.class);
        Object pacienteLocalizado = localizarPaciente.invoke(persistencia, "12345678901");
        assertNotNull(pacienteLocalizado, "Paciente deve ser localizado por CPF");
        assertEquals("12345678901", pacienteClass.getMethod("getCpf").invoke(pacienteLocalizado), 
                     "CPF do paciente localizado deve ser correto");
        assertEquals("Ana", pacienteClass.getMethod("getNome").invoke(pacienteLocalizado), 
                     "Nome do paciente localizado deve ser correto");
        assertEquals(70.0, pacienteClass.getMethod("getPeso").invoke(pacienteLocalizado), 
                     "Peso do paciente localizado deve ser correto");
        assertEquals(1.65, pacienteClass.getMethod("getAltura").invoke(pacienteLocalizado), 
                     "Altura do paciente localizado deve ser correta");
        assertEquals("F", pacienteClass.getMethod("getSexo").invoke(pacienteLocalizado), 
                     "Sexo do paciente localizado deve ser correto");
        assertEquals(30, pacienteClass.getMethod("getIdade").invoke(pacienteLocalizado), 
                     "Idade do paciente localizado deve ser correta");

        Object pacienteNaoExistente = localizarPaciente.invoke(persistencia, "98765432109");
        assertNull(pacienteNaoExistente, "Deve retornar null para CPF inexistente");
    }

    @Test
    public void testAtualizarPaciente() throws Exception {
        Object persistencia = persistenciaClass.getDeclaredConstructor().newInstance();
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);

        Method adicionarPaciente = persistenciaClass.getMethod("adicionarPaciente", pacienteClass);
        adicionarPaciente.invoke(persistencia, paciente);

        Object pacienteAtualizado = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Omair", 75.0, 1.70, "M", 35);
        Method atualizarPaciente = persistenciaClass.getMethod("atualizarPaciente", pacienteClass);
        atualizarPaciente.invoke(persistencia, pacienteAtualizado);

        Method localizarPaciente = persistenciaClass.getMethod("localizarPacientePorCpf", String.class);
        Object pacienteLocalizado = localizarPaciente.invoke(persistencia, "12345678901");
        assertEquals("Omair", pacienteClass.getMethod("getNome").invoke(pacienteLocalizado), 
                     "Nome do paciente deve ser atualizado");
        assertEquals(75.0, pacienteClass.getMethod("getPeso").invoke(pacienteLocalizado), 
                     "Peso do paciente deve ser atualizado");
        assertEquals(1.70, pacienteClass.getMethod("getAltura").invoke(pacienteLocalizado), 
                     "Altura do paciente deve ser atualizada");
        assertEquals("M", pacienteClass.getMethod("getSexo").invoke(pacienteLocalizado), 
                     "Sexo do paciente deve ser atualizado");
        assertEquals(35, pacienteClass.getMethod("getIdade").invoke(pacienteLocalizado), 
                     "Idade do paciente deve ser atualizada");
    }

    @Test
    public void testSalvarECarregarEmArquivo() throws Exception {
        Object persistencia = persistenciaClass.getDeclaredConstructor().newInstance();
        Object paciente = pacienteClass.getDeclaredConstructor(String.class, String.class, double.class, double.class, String.class, int.class)
                .newInstance("12345678901", "Ana", 70.0, 1.65, "F", 30);

        Method adicionarPaciente = persistenciaClass.getMethod("adicionarPaciente", pacienteClass);
        adicionarPaciente.invoke(persistencia, paciente);

        Method salvarEmArquivo = persistenciaClass.getMethod("salvarEmArquivo");
        salvarEmArquivo.invoke(persistencia);

        Object novaPersistencia = persistenciaClass.getDeclaredConstructor().newInstance();
        Method carregarDeArquivo = persistenciaClass.getMethod("carregarDeArquivo");
        carregarDeArquivo.invoke(novaPersistencia);

        Field pacientesField = persistenciaClass.getDeclaredField("pacientes");
        pacientesField.setAccessible(true);
        ArrayList<?> pacientes = (ArrayList<?>) pacientesField.get(novaPersistencia);
        assertEquals(1, pacientes.size(), "Deve carregar um paciente do arquivo");

        Object pacienteCarregado = pacientes.get(0);
        assertEquals("12345678901", pacienteClass.getMethod("getCpf").invoke(pacienteCarregado), 
                     "CPF do paciente carregado deve ser correto");
        assertEquals("Ana", pacienteClass.getMethod("getNome").invoke(pacienteCarregado), 
                     "Nome do paciente carregado deve ser correto");
        assertEquals(70.0, pacienteClass.getMethod("getPeso").invoke(pacienteCarregado), 
                     "Peso do paciente carregado deve ser correto");
        assertEquals(1.65, pacienteClass.getMethod("getAltura").invoke(pacienteCarregado), 
                     "Altura do paciente carregada deve ser correta");
        assertEquals("F", pacienteClass.getMethod("getSexo").invoke(pacienteCarregado), 
                     "Sexo do paciente carregado deve ser correto");
        assertEquals(30, pacienteClass.getMethod("getIdade").invoke(pacienteCarregado), 
                     "Idade do paciente carregada deve ser correta");
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