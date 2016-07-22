/*
 * Copyright 2013 by Kappich Systemberatung Aachen
 * 
 * This file is part of de.kappich.sys.funclib.csv.
 * 
 * de.kappich.sys.funclib.csv is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * de.kappich.sys.funclib.csv is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with de.kappich.sys.funclib.csv; If not, see <http://www.gnu.org/licenses/>.

 * Contact Information:
 * Kappich Systemberatung
 * Martin-Luther-Straße 14
 * 52062 Aachen, Germany
 * phone: +49 241 4090 436 
 * mail: <info@kappich.de>
 */

package de.kappich.sys.funclib.csv;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;

/**
 * @author Kappich Systemberatung
 * @version $Revision$
 */
public class CsvWriter extends BufferedWriter{

	/**
	 * CSV-Trennzeichen (z.B. Komma oder Semikolon)
	 */
	private final char _csvSeparator;

	/**
	 * CSV-Anführungszeichen
	 */
	private final char _csvQuote;

	/**
	 * Bestimmt, ob Anführungszeichen überall gesetzt werden (true) oder nur dort wo notwendig (false)
	 */
	private boolean _quoteAll;

	/**
	 * Erstellt einen neuen CsvWriter mit ';' als Trennzeichen und '"' als Anführungszeichen
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param outputStream OutputStream als Ziel zum schreiben
	 */
	public CsvWriter(final String encoding, final OutputStream outputStream) {
		this(Charset.forName(encoding), outputStream);
	}

	/**
	 * Erstellt einen neuen CsvWriter mit ';' als Trennzeichen und '"' als Anführungszeichen
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param outputStream OutputStream als Ziel zum schreiben
	 */
	public CsvWriter(final Charset encoding, final OutputStream outputStream) {
		this(encoding, outputStream, ';', '"');
	}

	/**
	 * Erstellt einen neuen CsvWriter
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param outputStream OutputStream als Ziel zum schreiben
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 */
	public CsvWriter(final String encoding, final OutputStream outputStream, final char csvSeparator, final char csvQuote) {
		this(encoding, outputStream, csvSeparator, csvQuote, false);
	}

	/**
	 * Erstellt einen neuen CsvWriter
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param outputStream OutputStream als Ziel zum schreiben
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 */
	public CsvWriter(final Charset encoding, final OutputStream outputStream, final char csvSeparator, final char csvQuote) {
		this(encoding, outputStream, csvSeparator, csvQuote, false);
	}

	/**
	 * Erstellt einen neuen CsvWriter
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param outputStream OutputStream als Ziel zum schreiben
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 * @param quoteAll Bestimmt, ob Anführungszeichen überall gesetzt werden (true) oder nur dort wo notwendig (false)
	 */
	public CsvWriter(final String encoding, final OutputStream outputStream, final char csvSeparator, final char csvQuote, final boolean quoteAll) {
		this(Charset.forName(encoding), outputStream, csvSeparator, csvQuote, quoteAll);
	}

	/**
	 * Erstellt einen neuen CsvWriter
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param outputStream OutputStream als Ziel zum schreiben
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 * @param quoteAll Bestimmt, ob Anführungszeichen überall gesetzt werden (true) oder nur dort wo notwendig (false)
	 */
	public CsvWriter(final Charset encoding, final OutputStream outputStream, final char csvSeparator, final char csvQuote, final boolean quoteAll) {
		super(new OutputStreamWriter(outputStream, encoding));
		_csvSeparator = csvSeparator;
		_csvQuote = csvQuote;
		_quoteAll = quoteAll;
	}

	/**
	 * Erstellt einen neuen CsvWriter mit ';' als Trennzeichen und '"' als Anführungszeichen
	 * @param writer Writer als Ziel zum schreiben
	 */
	public CsvWriter(final Writer writer) {
		this(writer, ';', '"');
	}

	/**
	 * Erstellt einen neuen CsvWriter
	 * @param writer Writer als Ziel zum schreiben
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 */
	public CsvWriter(final Writer writer, final char csvSeparator, final char csvQuote) {
		this(writer, csvSeparator, csvQuote, false);
	}

	/**
	 * Erstellt einen neuen CsvWriter
	 * @param writer Writer als Ziel zum schreiben
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 * @param quoteAll Bestimmt, ob Anführungszeichen überall gesetzt werden (true) oder nur dort wo notwendig (false)
	 */
	public CsvWriter(final Writer writer, final char csvSeparator, final char csvQuote, final boolean quoteAll) {
		super(writer);
		_csvSeparator = csvSeparator;
		_csvQuote = csvQuote;
		_quoteAll = quoteAll;
	}

	/**
	 * Schreibt eine Zeile Csv-Daten. Die einzelnen Parameter werden wie folgt behandelt:
	 * <ul>
	 *     <li>
	 *         <code>null</code> führt zu einem leeren Eintrag
	 *     </li>
	 *     <li>
	 *         Strings werden falls notwendig mit Anführungszeichen versehen und in die Ausgabe geschrieben
	 *     </li>
	 *     <li>
	 *         Zahlen werden in einer sprachunabhängigen Form in die Ausgabe geschrieben
	 *     </li>
	 *     <li>
	 *         Arrays werden in mehrere Spalten/Werte aufgeteilt
	 *     </li>
	 *     <li>
	 *         Andere Objekte werden mit toString() in einen String konvertiert
	 *     </li>
	 * </ul>
	 * @param data Zu schreibende Daten. Anzahl parameter/Arraylänge = anzahl der Daten in einer Zeile
	 * @throws IOException
	 */
	public void writeCsv(final Object... data) throws IOException {
		for(int i = 0; ; i++) {
			final Object o = data[i];
			writeData(o);

			if(i == data.length - 1) {
				writeNewLine();
				return;
			}
			writeSeperator();
		}
	}

	public void writeNewLine() throws IOException {
		write("\r\n");
	}

	public void writeSeperator() throws IOException {
		write(_csvSeparator);
	}

	/**
	 * Schreibt ein oder mehrere Csv-Daten ohne einen Zeilenumbruch einzufügen.
	 * Die einzelnen Parameter werden wie folgt behandelt:
	 * <ul>
	 *     <li>
	 *         <code>null</code> führt zu einem leeren Eintrag
	 *     </li>
	 *     <li>
	 *         Strings werden falls notwendig mit Anführungszeichen versehen und in die Ausgabe geschrieben
	 *     </li>
	 *     <li>
	 *         Zahlen werden in einer sprachunabhängigen Form in die Ausgabe geschrieben
	 *     </li>
	 *     <li>
	 *         Arrays werden in mehrere Spalten/Werte aufgeteilt
	 *     </li>
	 *     <li>
	 *         Andere Objekte werden mit toString() in einen String konvertiert
	 *     </li>
	 * </ul>
	 * @param data Zu schreibende Daten
	 * @throws IOException
	 */
	public void writeData(final Object data) throws IOException {
		if(data != null && data.getClass().isArray()) {
			int len = Array.getLength(data);
			for(int f = 0; f < len; f++) {
				writeData(Array.get(data, f));
				if(f == len - 1) {
					break;
				}
				writeSeperator();
			}
		}
		else {
			writeQuoted(data);
		}
	}


	private void writeQuoted(final Object o) throws IOException {
		if(o == null) return;
		char[] chars = o.toString().toCharArray();
		if(_quoteAll || needsQuote(chars)){
			write(_csvQuote);
			for(char c : chars) {
				if(c == _csvQuote){
					write(c);
				}
				write(c);
			}
			write(_csvQuote);
		}
		else {
			write(chars);
		}
	}

	private boolean needsQuote(final char[] chars) {
		if(chars.length == 0) return false;
		for(char c : chars) {
			if(c == '\r' || c == '\n' || c == _csvQuote || c == _csvSeparator) {
				return true;
			}
		}
		return false;
	}

}
