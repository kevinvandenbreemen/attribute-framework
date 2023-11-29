import com.vandenbreemen.kevincommon.db.DatabaseSchema
import com.vandenbreemen.kevincommon.db.SQLiteDAO
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class AttributeInteractorTest {

    val fileName = "test.dat"
    val dao = SQLiteDAO(fileName)

    @BeforeEach
    fun setup() {

        val schema = DatabaseSchema(dao)
        schema.addDatabaseChange(1, """
            CREATE TABLE person(
                id INTEGER PRIMARY KEY AUTOINCREMENT, 
                name TEXT
            )
        """.trimIndent())

    }

    @AfterEach
    fun teardown() {
        File(fileName).delete()
    }

    @Test
    fun `should store an attribute by id`() {

        val interactor = AttributeInteractor(
            dao
        )

        interactor.provideAttributesFor("person")



        interactor.defineAttribute(1, "Test_Class")

        dao.insert("INSERT INTO person(name) VALUES (?)", arrayOf("Test Person"))

        interactor.storeAttributeFor("person", 1, 1, "Test")

        val attributeValue = interactor.getAttributeOf("person", 1, 1)
        attributeValue shouldBeEqualTo "Test"

    }

}