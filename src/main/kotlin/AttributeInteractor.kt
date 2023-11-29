import com.vandenbreemen.kevincommon.db.SQLiteDAO

class AttributeInteractor(private val dao: SQLiteDAO) {

    init {
        initialSetupForAttributes()
    }

    private fun initialSetupForAttributes() {

        dao.createTable("""
            CREATE TABLE IF NOT EXISTS attr_fwk_attr_type(
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL
            );
        """.trimIndent())
    }


    /**
     * Defines an attribute with the given ID and attribute name
     */
    fun defineAttribute(attributeId: Int, attributeName: String) {
        dao.insert("INSERT INTO attr_fwk_attr_type (id, name) VALUES (?, ?)", arrayOf( attributeId, attributeName))
    }

    /**
     * Stores an
     */
    fun storeAttributeFor(tableName: String, tableRecordId: Int, attributeId: Int, attributeValue: String) {
        dao.insert("INSERT INTO ${tableForTable(tableName)}(rec_id, attr_id, value) VALUES (?, ?, ?)", arrayOf(tableRecordId, attributeId, attributeValue))
    }

    private fun tableForTable(tableName: String): String {
        return "att_fwk_$tableName"
    }

    /**
     * Provide for defining attributes for the given table
     */
    fun provideAttributesFor(tableName: String) {

        dao.createTable( """
            CREATE TABLE IF NOT EXISTS  ${tableForTable(tableName)} (
                rec_id INTEGER NOT NULL,
                attr_id INTEGER NOT NULL,
                value TEXT,
                CONSTRAINT fk_a_${tableName}_r FOREIGN KEY(rec_id) REFERENCES $tableName(id) ON DELETE CASCADE,
                CONSTRAINT fk_a_${tableName}_a FOREIGN KEY(attr_id) REFERENCES attr_fwk_attr_type(id) ON DELETE CASCADE
            );
        """.trimIndent())
    }

    fun getAttributeOf(tableName: String, tableRecordId: Int, attributeId: Int): String? {
        val record = dao.query("SELECT value FROM ${tableForTable(tableName)} WHERE attr_id=? AND rec_id=?", arrayOf(attributeId, tableRecordId))
        if(record.isNotEmpty()) {
            return record[0]["value"] as String
        }
        return null
    }
}