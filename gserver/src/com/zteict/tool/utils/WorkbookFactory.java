package com.zteict.tool.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkbookFactory {
	/**
	* Creates an HSSFWorkbook from the given POIFSFileSystem
	*/
	public static Workbook create(POIFSFileSystem fs) throws IOException {
	   return new HSSFWorkbook(fs);
	}
	/**
	* Creates an XSSFWorkbook from the given OOXML Package
	*/
	public static Workbook create(OPCPackage pkg) throws IOException {
	   return new XSSFWorkbook(pkg);
	} 
	
	public static Workbook create(InputStream inp) throws IOException, InvalidFormatException {
	    if(! inp.markSupported()) {
	    inp = new PushbackInputStream(inp, 8);
	   }
	  
	   if(POIFSFileSystem.hasPOIFSHeader(inp)) {
	    return new HSSFWorkbook(inp);
	   }
	   if(POIXMLDocument.hasOOXMLHeader(inp)) {
	    return new XSSFWorkbook(OPCPackage.open(inp));
	   }
	   throw new IllegalArgumentException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
	}
	}
	 


