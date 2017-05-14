
import java.io.*;
import java.sql.*;

import org.apache.poi.hssf.usermodel.*;

import java.util.*;

public class Reuslatstoexcel {

	private Connection connection = null;

	public Reuslatstoexcel() throws ClassNotFoundException {  
		connection = Connectionsql.getInstance2();
	}

	public void generateXls(String nom_fichier)
			throws SQLException, FileNotFoundException, IOException {

		// Create new Excel workbook and sheet
		HSSFWorkbook xlsWorkbook = new HSSFWorkbook();
		HSSFSheet xlsSheet = xlsWorkbook.createSheet();
		short rowIndex = 0;

		// Execute SQL query
		PreparedStatement stmt =
				connection.prepareStatement("select * from resultats");
		ResultSet rs = stmt.executeQuery();

		// Get the list of column names and store them as the first
		// row of the spreadsheet.
		ResultSetMetaData colInfo = rs.getMetaData();
		List<String> colNames = new ArrayList<String>();
		HSSFRow titleRow = xlsSheet.createRow(rowIndex++);

		for (int i = 1; i <= colInfo.getColumnCount(); i++) {
			colNames.add(colInfo.getColumnName(i));
			titleRow.createCell((short) (i-1)).setCellValue(
					new HSSFRichTextString(colInfo.getColumnName(i)));
			xlsSheet.setColumnWidth((short) (i-1), (short) 4000);
		}

		// Save all the data from the database table rows
		while (rs.next()) {
			HSSFRow dataRow = xlsSheet.createRow(rowIndex++);
			short colIndex = 0;
			for (String colName : colNames) {
				dataRow.createCell(colIndex++).setCellValue(
						new HSSFRichTextString(rs.getString(colName)));
			}
		}

		// Write to disk
		xlsWorkbook.write(new FileOutputStream(nom_fichier));
		connection.close();
	}
}


