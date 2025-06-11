package sistema.prontuario.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

public class ExameTest {
    private Class<?> exameClass;

    @BeforeEach
    public void setUp() throws Exception {
        exameClass = Class.forName("sistema.prontuario.model.Exame");
        assertTrue(java.io.Serializable.class.isAssignableFrom(exameClass), "Exame deve implementar Serializable");
    }

    @Test
    public void testExameClassStructure() throws Exception {
        assertTrue(hasField(exameClass, "idExame", String.class, true), "Atributo idExame (String) deve estar presente e ser privado");
        assertTrue(hasField(exameClass, "resultado", String.class, true), "Atributo resultado (String) deve estar presente e ser privado");
        assertTrue(hasField(exameClass, "status", boolean.class, true), "Atributo status (boolean) deve estar presente e ser privado");
        assertTrue(hasField(exameClass, "dataExame", LocalDateTime.class, true), "Atributo dataExame (LocalDateTime) deve estar presente e ser privado");

        assertTrue(hasMethod(exameClass, "registrarResultado", new Class[]{String.class}), "Método registrarResultado deve estar presente");
        assertTrue(hasMethod(exameClass, "validarExame", new Class[]{}), "Método validarExame deve estar presente");
        assertTrue(hasMethod(exameClass, "invalidarExame", new Class[]{}), "Método invalidarExame deve estar presente");
        assertTrue(hasMethod(exameClass, "toString", new Class[]{}), "Método toString deve estar presente");
        assertTrue(hasMethod(exameClass, "equals", new Class[]{Object.class}), "Método equals deve estar presente");
        assertTrue(hasMethod(exameClass, "hashCode", new Class[]{}), "Método hashCode deve estar presente");
    }

    @Test
    public void testRegistrarResultado() throws Exception {
        Object exame = exameClass.getDeclaredConstructor(String.class, String.class, boolean.class, LocalDateTime.class)
                .newInstance("EX001", "Normal", true, LocalDateTime.now());

        Method registrarResultado = exameClass.getMethod("registrarResultado", String.class);
        registrarResultado.invoke(exame, "Anormal: detalhe");

        Field resultadoField = exameClass.getDeclaredField("resultado");
        resultadoField.setAccessible(true);
        String resultado = (String) resultadoField.get(exame);
        assertEquals("Anormal: detalhe", resultado, "O resultado deve ser atualizado após registro");

        try {
            registrarResultado.invoke(exame, "");
            fail("Registro de resultado vazio não deve ser permitido");
        } catch (Exception e) {
            // Esperado
        }
    }

    @Test
    public void testInvalidarExame() throws Exception {
        Object exame = exameClass.getDeclaredConstructor(String.class, String.class, boolean.class, LocalDateTime.class)
                .newInstance("EX001", "Normal", true, LocalDateTime.now());

        Method invalidarExame = exameClass.getMethod("invalidarExame");
        invalidarExame.invoke(exame);

        Field statusField = exameClass.getDeclaredField("status");
        statusField.setAccessible(true);
        boolean status = (boolean) statusField.get(exame);
        assertFalse(status, "O exame deve estar inativo");

        Method registrarResultado = exameClass.getMethod("registrarResultado", String.class);
        try {
            registrarResultado.invoke(exame, "Anormal");
            fail("Registro em exame inativo não deve ser permitido");
        } catch (Exception e) {
            // Esperado
        }
    }

    @Test
    public void testValidarExame() throws Exception {
        Object exame = exameClass.getDeclaredConstructor(String.class, String.class, boolean.class, LocalDateTime.class)
                .newInstance("EX001", "Normal", false, LocalDateTime.now());

        Method validarExame = exameClass.getMethod("validarExame");
        validarExame.invoke(exame);

        Field statusField = exameClass.getDeclaredField("status");
        statusField.setAccessible(true);
        boolean status = (boolean) statusField.get(exame);
        assertTrue(status, "O exame deve estar ativo");

        Method registrarResultado = exameClass.getMethod("registrarResultado", String.class);
        registrarResultado.invoke(exame, "Anormal");
        Field resultadoField = exameClass.getDeclaredField("resultado");
        resultadoField.setAccessible(true);
        String resultado = (String) resultadoField.get(exame);
        assertEquals("Anormal", resultado, "Registro de resultado deve ser permitido após validação");
    }

    private boolean hasField(Class<?> clazz, String fieldName, Class<?> fieldType, boolean isPrivate) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType().equals(fieldType) && (!isPrivate || java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
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
