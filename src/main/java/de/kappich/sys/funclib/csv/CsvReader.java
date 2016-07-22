/*
 * Copyright 2012 by Kappich Systemberatung Aachen
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
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Klasse zum einlesen von CSV-Daten
 *
 * @author Kappich Systemberatung
 * @version $Revision$
 */
@SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
public class CsvReader {

	/**
	 * Leere Zeilen standardmäßig ignorieren
	 */
	public static final String DEFAULT_IGNORE_LINE_PATTERN = "^$";

	/**
	 * CSV-Trennzeichen (z.B. Komma oder Semikolon)
	 */
	private final char _csvSeparator;

	/**
	 * CSV-Anführungszeichen
	 */
	private final char _csvQuote;

	/**
	 * Aktuelle Zeile, die gelesen wird
	 */
	private int _lineNum;

	/**
	 * Anzahl Spalten
	 */
	private int _lastCellCount = -1;

	/**
	 * Reader, der die CSV-Daten einliest
	 */
	private final BufferedReader _reader;

	/**
	 * Pattern, mit dem leere Zeilen und ähnliches ignoriert werden können.
	 */
	private Pattern _ignoreLinePattern;

	/**
	 * Erstellt einen neuen CsvReader mit ';' als Trennzeichen und '"' als Anführungszeichen
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param inputStream InputStream als Datenquelle
	 */
	public CsvReader(final String encoding, final InputStream inputStream) {
		this(Charset.forName(encoding), inputStream);
	}

	/**
	 * Erstellt einen neuen CsvReader mit ';' als Trennzeichen und '"' als Anführungszeichen
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param inputStream InputStream als Datenquelle
	 */
	public CsvReader(final Charset encoding, final InputStream inputStream) {
		this(encoding, inputStream, ';', '"');
	}

	/**
	 * Erstellt einen neuen CsvReader
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param inputStream InputStream als Datenquelle
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 */
	public CsvReader(final String encoding, final InputStream inputStream, final char csvSeparator, final char csvQuote) {
		this(encoding, inputStream, csvSeparator, csvQuote, "^$");
	}

	/**
	 * Erstellt einen neuen CsvReader
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param inputStream InputStream als Datenquelle
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 */
	public CsvReader(final Charset encoding, final InputStream inputStream, final char csvSeparator, final char csvQuote) {
		this(encoding, inputStream, csvSeparator, csvQuote, "^$");
	}

	/**
	 * Erstellt einen neuen CsvReader
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param inputStream InputStream als Datenquelle
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 * @param ignoreLinePattern Regulärer Ausdruck, mit dem zu ignorierende Zeilen vorgegeben werden können
	 */
	public CsvReader(final String encoding, final InputStream inputStream, final char csvSeparator, final char csvQuote, final String ignoreLinePattern) {
		this(Charset.forName(encoding), inputStream, csvSeparator, csvQuote, ignoreLinePattern);
	}

	/**
	 * Erstellt einen neuen CsvReader
	 * @param encoding Dateikodierung, z.B. UTF-8
	 * @param inputStream InputStream als Datenquelle
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 * @param ignoreLinePattern Regulärer Ausdruck, mit dem zu ignorierende Zeilen vorgegeben werden können
	 */
	public CsvReader(final Charset encoding, final InputStream inputStream, final char csvSeparator, final char csvQuote, final String ignoreLinePattern) {
		if(ignoreLinePattern != null){
			_ignoreLinePattern = Pattern.compile(ignoreLinePattern);
		}
		else {
			_ignoreLinePattern = null;
		}
		_reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
		_csvSeparator = csvSeparator;
		_csvQuote = csvQuote;
		_lineNum = 0;
	}

	/**
	 * Erstellt einen neuen CsvReader mit ';' als Trennzeichen und '"' als Anführungszeichen
	 * @param reader Reader als Datenquelle
	 */
	public CsvReader(final Reader reader) {
		this(reader, ';', '"');
	}

	/**
	 * Erstellt einen neuen CsvReader
	 * @param reader Reader als Datenquelle
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 */
	public CsvReader(final Reader reader, final char csvSeparator, final char csvQuote) {
		this(reader, csvSeparator, csvQuote, DEFAULT_IGNORE_LINE_PATTERN);
	}

	/**
	 * Erstellt einen neuen CsvReader
	 * @param reader Reader als Datenquelle
	 * @param csvSeparator Trennzeichen
	 * @param csvQuote Maskierungszeichen (Anführungszeichen)
	 * @param ignoreLinePattern Regulärer Ausdruck, mit dem zu ignorierende Zeilen vorgegeben werden können
	 */
	public CsvReader(final Reader reader, final char csvSeparator, final char csvQuote, final String ignoreLinePattern) {
		if(ignoreLinePattern != null){
			_ignoreLinePattern = Pattern.compile(ignoreLinePattern);
		}
		else {
			_ignoreLinePattern = null;
		}
		_reader = new BufferedReader(reader);
		_csvSeparator = csvSeparator;
		_csvQuote = csvQuote;
		_lineNum = 0;
	}

	/**
	 * Verarbeitet die CSV-Daten. Die erste Zeile wird als Spaltenkopf interpretiert. Bei dieser Methode wird die
	 * CSV-Datei am Stück eingelesen und komplett im Speicher gehalten. Wenn kein wahlfreier Zugriff auf die Zeilen notwendig ist,
	 * sollte stattdessen {@link #read()} verwendet werden.
	 * @return CsvData-Objekt, welchen des Dateiinhalt enthält
	 * @throws IOException Ein-Ausgabe-Fehler
	 * @throws CsvParseException Fehler beim Parsen der CSV-Datei
	 */
	public CsvData readAll() throws IOException {
		_lineNum++;
		String[] headerCells = null;
		while(headerCells == null){
			final String headerLine = _reader.readLine();
			if(headerLine == null) throw new CsvParseException("Datei enthält keine Spaltenüberschriften (ist leer).", _lineNum, 0);
			headerCells = splitLineToCells(headerLine, _reader);
		}
		return readAll(headerCells);
	}

	/**
	 * Verarbeitet die CSV-Daten. Die erste Zeile wird als Spaltenkopf interpretiert.
	 * @return CsvData-Objekt, welchen des Dateiinhalt enthält
	 * @throws IOException Ein-Ausgabe-Fehler
	 * @throws CsvParseException Fehler beim Parsen der CSV-Datei
	 */
	public IterableCsvData read() throws IOException {
		_lineNum++;
		String[] headerCells = null;
		while(headerCells == null){
			final String headerLine = _reader.readLine();
			if(headerLine == null) throw new CsvParseException("Datei enthält keine Spaltenüberschriften (ist leer).", _lineNum, 0);
			headerCells = splitLineToCells(headerLine, _reader);
		}
		return read(headerCells);
	}

	/**
	 * Verarbeitet die CSV-Daten. Der Spaltenkopf wird als Parameter übergeben, die erste Zeile wird als Daten gewertet.
	 * Bei dieser Methode wird die
	 * CSV-Datei am Stück eingelesen und komplett im Speicher gehalten. Wenn kein wahlfreier Zugriff auf die Zeilen notwendig ist,
	 * sollte stattdessen {@link #read(String[])} verwendet werden.
	 * @param headerCells Spaltenkopf (falls null, kann auf die Spalten nur über Index zugegriffen werden)
	 * @return CsvData-Objekt, welchen des Dateiinhalt enthält
	 * @throws IOException Ein-Ausgabe-Fehler
	 * @throws CsvParseException Fehler beim Parsen der CSV-Datei
	 */
	public CsvData readAll(String[] headerCells) throws IOException {
		// Datei zeilenweise einlesen
		final List<String[]> entries = new ArrayList<String[]>();
		String line;
		while((line = _reader.readLine()) != null) {
			_lineNum++;
			final String[] cells = splitLineToCells(line, _reader);
			if(cells != null){
				entries.add(cells);
			}
		}

		final HashMap<String,Integer> columnNameToIndexMap = new HashMap<String, Integer>();
		if(headerCells != null){
			// Spaltenindex initialisieren
			for(int i = 0; i < headerCells.length; i++) {
				final String headerCell = headerCells[i];
				columnNameToIndexMap.put(headerCell, i);
			}
		}
		return new CsvData(headerCells, columnNameToIndexMap, entries);
	}

	/**
	 * Verarbeitet die CSV-Daten. Der Spaltenkopf wird als Parameter übergeben, die erste Zeile wird als Daten gewertet.
	 * @param headerCells Spaltenkopf (falls null, kann auf die Spalten nur über Index zugegriffen werden)
	 * @return IterableCsvData-Objekt, mit dem man über den Dateiinhalt iterieren kann
	 * @throws IOException Ein-Ausgabe-Fehler
	 * @throws CsvParseException Fehler beim Parsen der CSV-Datei
	 */
	public IterableCsvData read(String[] headerCells) throws IOException {
		final HashMap<String,Integer> columnNameToIndexMap = new HashMap<String, Integer>();
		if(headerCells != null){
			// Spaltenindex initialisieren
			for(int i = 0; i < headerCells.length; i++) {
				final String headerCell = headerCells[i];
				columnNameToIndexMap.put(headerCell, i);
			}
		}
		return new CsvIterableData(headerCells, columnNameToIndexMap);
	}

	/**
	 * Zerlegt eine Zeile in ein String-Array mit den einzelnen Einträgen
	 *
	 * @param line Zeile
	 * @param reader Reader (falls weitere Zeilen benötigt werden)
	 * @return String-Array
	 * @throws IOException IO-Fehler
	 * @throws CsvParseException Fehler beim Parsen der CSV-Datei
	 */
	String[] splitLineToCells(final String line, final BufferedReader reader) throws IOException {

		if(_ignoreLinePattern != null){
			if(_ignoreLinePattern.matcher(line).matches()){
				return null;
			}
		}

		char[] chars = line.toCharArray();
		boolean inQuote = false;

		final ArrayList<String> cells = new ArrayList<String>();

		final StringBuilder cell = new StringBuilder();

		while(true) {
			for(int i = 0; i < chars.length; i++) {
				final char c = chars[i];
				if(inQuote) {
					if(c == _csvQuote) {
						// Doppelte Anführungszeichen innerhalb Anführungszeichen durch eins ersetzen
						if((i + 1 < chars.length) && (chars[i + 1] == _csvQuote)) {
							cell.append(_csvQuote);
							++i;
						}
						else {
							inQuote = false;
						}
					}
					else {
						cell.append(c);
					}
				}
				else {
					if(c == _csvQuote) {
						inQuote = true;
					}
					else if(c == _csvSeparator) {
						cells.add(cell.toString());
						cell.setLength(0);
					}
					else {
						cell.append(c);
					}
				}
			}
			if(inQuote){
				// Mehrzeiliger Text, weitere Zeile einlesen
				String tmp = reader.readLine();
				if(tmp == null) {
					throw new CsvParseException("Unerwartetes Dateiende", _lineNum, cells.size() - 1);
				}
				chars = tmp.toCharArray();
				cell.append('\n');
			}
			else {
				break;
			}
		}

		cells.add(cell.toString());

		if(_lastCellCount < 0) {
			_lastCellCount = cells.size();
		}
		return cells.toArray(new String[cells.size()]);
	}


	private class CsvIterableData extends IterableCsvData {


		public CsvIterableData(final String[] headerCells, final HashMap<String, Integer> columnNameToIndexMap) {
			super(headerCells, columnNameToIndexMap);
		}

		@Override
		public Iterator<CsvRow> iterator() {
			return new CsvRowIterator();
		}


		private class CsvRowIterator implements Iterator<IterableCsvData.CsvRow> {
			private String[] line;
			private IOException exception;

			public CsvRowIterator() {
				readNextLine();
			}

			@Override
			public boolean hasNext() {
				return line != null;
			}

			@Override
			public IterableCsvData.CsvRow next() {
				if(line == null) throw new NoSuchElementException();
				if(exception != null){
					return new CsvRow(_lineNum, exception);
				}
				String[] tmp = line;
				readNextLine();
				return new IterableCsvData.CsvRow(_lineNum, tmp);
			}

			private void readNextLine()  {
				try {
					_lineNum++;
					String s = _reader.readLine();
					if(s == null){
						line = null;
					}
					else {
						line = splitLineToCells(s, _reader);
						while(line == null){
							s = _reader.readLine();
							if(s == null){
								line = null;
								return;
							}
							line = splitLineToCells(s, _reader);
						}
					}
				}
				catch(IOException e) {
					exception = e;
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}
	}
}
