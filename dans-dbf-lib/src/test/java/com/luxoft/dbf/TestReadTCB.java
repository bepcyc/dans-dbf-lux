package com.luxoft.dbf;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;

import org.junit.Test;


public class TestReadTCB {

	@Test
	public void doRead() throws CorruptedTableException, IOException {
		 final Table table = new Table(new File("src/test/resources/dbase5/o0700456.dbf"), "CP866");
		 System.out.println("Charset: " + table.getCharsetName());
		 System.out.println("Name: " + table.getName());
		
		 table.open();
		 System.out.println(table.getVersion());
		 System.out.println("LastModifed: " + table.getLastModifiedDate());
		 
		 List<Field> fields = table.getFields();
		 for (int z = 0; z < fields.size(); z++) {
			 Field f = fields.get(z);
			// System.out.print(z + " ");
			// System.out.println(f.getName() + " L:" + f.getLength() + " DC:"+ f.getDecimalCount() + " T:" + f.getType());
		 }
		 
		 final Iterator<Record> recordIterator = table.recordIterator();
		 while (recordIterator.hasNext()) {
			 Record record = recordIterator.next();
			 for (Field f : fields) {
				 record.getTypedValue(f.getName());
			 }
			 System.out.println(record.getStringValue("ADRI"));
		 }
		 table.close();
	}
	
	@Test
	public void doReadFlipped() throws CorruptedTableException, IOException {
		 final Table table = new Table(new File("src/test/resources/dbase5/o0700341.dbf"), "CP866");
		 System.out.println("Charset: " + table.getCharsetName());
		 System.out.println("Name: " + table.getName());
		
		 table.open();
		 System.out.println(table.getVersion());
		 System.out.println("LastModifed: " + table.getLastModifiedDate());
		 
		 List<Field> fields = table.getFields();
		 for (int z = 0; z < fields.size(); z++) {
			 Field f = fields.get(z);
			 System.out.print(z + " ");
			 System.out.println(f.getName() + " L:" + f.getLength() + " DC:"+ f.getDecimalCount() + " T:" + f.getType());
		 }
		 
		 final Iterator<Record> recordIterator = table.recordIterator();
		 while (recordIterator.hasNext()) {
			 Record record = recordIterator.next();
			 System.out.println(record.getStringValue("ADRI"));
		 }
		 table.close();
	}

}
