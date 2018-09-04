package ru.smartsarov.election.db;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

public class ResultSetToJson {
    public static final Type RESULT_TYPE = new TypeToken<List<Map<String, Object>>>() {
        @SuppressWarnings("unused")
		private static final long serialVersionUID = -3467016635635320150L;
    }.getType();

    public static void queryToJson(Writer writer, /*String connectionProperties,*/ String query, String indent, boolean closeWriter) {
        Connection conn = null;
        Statement stmt = null;
        GsonBuilder gson = new GsonBuilder();
        JsonWriter jsonWriter = new JsonWriter(writer);

        if (indent != null)
        	jsonWriter.setIndent(indent);

        try {
            //Properties props = readConnectionInfo(connectionProperties);
            //Class.forName(props.getProperty("driver"));
            //conn = openConnection(props);
        	
        	conn = SQLiteDB.getConnection();
            stmt = conn.createStatement();

            gson.create().toJson(QueryHelper.select(stmt, query), RESULT_TYPE, jsonWriter);

            if (closeWriter)
            	jsonWriter.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (stmt != null)
                	stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (closeWriter && jsonWriter != null)
                	jsonWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}