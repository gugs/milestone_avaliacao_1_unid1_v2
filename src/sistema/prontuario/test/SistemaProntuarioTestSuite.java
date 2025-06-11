package sistema.prontuario.test;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    PacienteTest.class,
    ExameTest.class,
    PersistenciaEmArquivoTest.class
})
public class SistemaProntuarioTestSuite {
}
