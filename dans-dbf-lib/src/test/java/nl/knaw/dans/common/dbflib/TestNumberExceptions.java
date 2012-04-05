package nl.knaw.dans.common.dbflib;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test that the Number related exceptions are thrown at the appropriate times.
 *
 * @author Vesa Åkerman
 */
@RunWith(Parameterized.class)
public class TestNumberExceptions
    extends BaseTestcase
{
    private Table table;

    /**
     * Creates a new TestCharacterExceptions object.
     *
     * @param aVersion test parameter
     * @param aVersionDirectory test parameter
     */
    public TestNumberExceptions(Version aVersion, String aVersionDirectory)
    {
        super(aVersion, aVersionDirectory);
    }

    @Before
    public void setUp()
               throws IOException, CorruptedTableException, InvalidFieldTypeException, InvalidFieldLengthException
    {
        final String outputDir = "target/test-output/" + versionDirectory + "/exceptions";
        UnitTestUtil.recreateDirectory(outputDir);

        final File tableFile = new File(outputDir + "/VALTOOLARGE.DBF");

        final List<Field> fields = new ArrayList<Field>();
        fields.add(new Field("INTFIELD", Type.NUMBER, 5, 0));
        fields.add(new Field("DECFIELD", Type.NUMBER, 5, 2));

        table = new Table(tableFile, version, fields);
        table.open(IfNonExistent.CREATE);
    }

    @After
    public void tearDown()
                  throws IOException
    {
        table.close();
    }

    @Test(expected = ValueTooLargeException.class)
    public void firstValueTooLarge()
                            throws IOException, DbfLibException
    {
        table.addRecord(123456);
    }

    @Test(expected = ValueTooLargeException.class)
    public void secondValueTooLarge()
                             throws IOException, DbfLibException
    {
        table.addRecord(0, 123.45);
    }

    @Test(expected = InvalidFieldLengthException.class)
    public void tooLongNumberField()
                            throws IOException,
                                   CorruptedTableException,
                                   InvalidFieldTypeException,
                                   InvalidFieldLengthException
    {
        final File outputDir = new File("target/test-output/" + versionDirectory + "/types/NUMBER");
        outputDir.mkdirs();

        final File tableFile = new File(outputDir, "TOOLONGFIELD.DBF");
        UnitTestUtil.remove(tableFile);

        final List<Field> fields = new ArrayList<Field>();
        fields.add(new Field("NUMBER_1", Type.NUMBER, 21, 0));

        table = new Table(tableFile, version, fields);
        table.open(IfNonExistent.CREATE);
    }
}
