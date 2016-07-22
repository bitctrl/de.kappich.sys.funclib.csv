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

import java.io.IOException;
import java.util.*;

/**
 * @author Kappich Systemberatung
 * @version $Revision$
 */
public abstract class IterableCsvData implements Iterable<IterableCsvData.CsvRow> {
	/**
	 * Spaltenköpfe
	 */
	protected final String[] _headerCells;
	/**
	 * Map Spaltenkopf->Index
	 */
	protected final HashMap<String,Integer> _columnNameToIndexMap;

	public IterableCsvData(final String[] headerCells, final HashMap<String, Integer> columnNameToIndexMap) {
		_headerCells = headerCells;
		_columnNameToIndexMap = columnNameToIndexMap;
	}

	/**
	 * Gibt die Zuordnung Spaltenüberschrift zu Spaltenindex zurück. Die Rückgabe ist eine Map, die zu jeder Spaltenüberschrift den Spaltenindex speichert.
	 * @return die Zuordnung Spaltenüberschrift zu Spaltenindex
	 */
	public Map<String, Integer> getColumnNameToIndexMap() {
		return Collections.unmodifiableMap(_columnNameToIndexMap);
	}

	/**
	 * Gibt die Spaltenköpfe zurück
	 * @return die Spaltenköpfe oder <code>null</code> wenn keine Spaltenköpfe definiert wurden
	 */
	public String[] getHeaderCells() {
		return _headerCells == null ? null : _headerCells.clone();
	}

	@Override
	public abstract Iterator<CsvRow> iterator();

	/**
	 * Findet eine passende Spalte die String-Werte enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param header Spaltenüberschrift
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public CsvColumn<String> getColumn(String header) throws CsvParseException {
		return getColumn(header, new CsvStringParser());
	}

	/**
	 * Findet eine passende Spalte die String-Werte enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param columnIndex Spaltenindex
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public CsvColumn<String> getColumn(int columnIndex) throws CsvParseException {
		return getColumn(columnIndex, new CsvStringParser());
	}

	/**
	 * Findet eine passende Spalte die Integer-Werte enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param header Spaltenüberschrift
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public CsvColumn<Integer> getIntColumn(String header) throws CsvParseException {
		return getColumn(header, new CsvIntegerParser());
	}

	/**
	 * Findet eine passende Spalte die Integer-Werte enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param columnIndex Spaltenindex
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public CsvColumn<Integer> getIntColumn(int columnIndex) throws CsvParseException {
		return getColumn(columnIndex, new CsvIntegerParser());
	}

	/**
	 * Findet eine passende Spalte die Long-Werte enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param header Spaltenüberschrift
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public CsvColumn<Long> getLongColumn(String header) throws CsvParseException {
		return getColumn(header, new CsvLongParser());
	}

	/**
	 * Findet eine passende Spalte die Long-Werte enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param columnIndex Spaltenindex
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public CsvColumn<Long> getLongColumn(int columnIndex) throws CsvParseException {
		return getColumn(columnIndex, new CsvLongParser());
	}

	/**
	 * Findet eine passende Spalte die Double-Werte enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param header Spaltenüberschrift
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public CsvColumn<Double> getDoubleColumn(String header) throws CsvParseException {
		return getColumn(header, new CsvDoubleParser());
	}

	/**
	 * Findet eine passende Spalte die Double-Werte enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param columnIndex Spaltenindex
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public CsvColumn<Double> getDoubleColumn(int columnIndex) throws CsvParseException {
		return getColumn(columnIndex, new CsvDoubleParser());
	}

	/**
	 * Findet eine passende Spalte die Werte in einem benutzerdefinierten Format enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param header Spaltenüberschrift
	 * @param parser Klasse, die den String-Wert dieser Spalte in ein benutzerdefiniertes Objekt konvertiert.
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public <T> CsvColumn<T> getColumn(String header, CsvParser<T> parser) throws CsvParseException {
		return getColumn(getHeaderIndex(header), parser);
	}

	/**
	 * Findet eine passende Spalte die Werte in einem benutzerdefinierten Format enthält und gibt ein {@link de.kappich.sys.funclib.csv.CsvColumn}-Objekt zurück, was den Zugriff auf die
	 * Werte dieser Spalte erlaubt. Siehe {@link IterableCsvData.CsvRow#getValue(de.kappich.sys.funclib.csv.CsvColumn)}.
	 * @param columnIndex Spaltenindex
	 * @param parser Klasse, die den String-Wert dieser Spalte in ein benutzerdefiniertes Objekt konvertiert.
	 * @return Objekt, das die Spalte repräsentiert.
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Wenn die Spalte nicht gefunden wurde
	 */
	public <T> CsvColumn<T> getColumn(int columnIndex, CsvParser<T> parser) throws CsvParseException {
		if(_headerCells != null){
			if(columnIndex >= _headerCells.length){
				throw new CsvParseException("Spaltenindex \"" + columnIndex + "\" nicht gefunden", 0);
			}
		}
		return new CsvColumn<T>(this, columnIndex, parser);
	}

	/**
	 * Ermittelt den Spaltenindex zu einem Spaltennamen
	 * @param header Spaltenname
	 * @return Index
	 * @throws de.kappich.sys.funclib.csv.CsvParseException Falls es keine Spalte mit dem angegebenen Namen gibt
	 */
	public int getHeaderIndex(final String header) throws CsvParseException {
		Integer col = _columnNameToIndexMap.get(header);
		if(col == null){
			throw new CsvParseException("Spalte \"" + header + "\" nicht gefunden", 0);
		}
		return col;
	}

	/**
	 * Klasse, die die Werte einer CSV-Zeile enthält
	 */
	public final class CsvRow {
		private final String[] _values;
		private final int _row;
		private final IOException _exception;

		/**
		 * Erstellt eine Csv-Zeile mit Werten
		 * @param row Zeilenindex
		 * @param values Werte
		 */
		CsvRow(final int row, final String[] values) {
			_row = row;
			_values = values;
			_exception = null;
		}

		/**
		 * Erstellt eine Csv-Zeile mit Exception. Es werden keine Werte gespeichert, beim Zugriff auf Werte wird
		 * die Exception ausgelöst.
		 * @param row Zeilenindex
		 * @param exception Exception
		 */
		CsvRow(final int row, final IOException exception) {
			_values = null;
			_row = row;
			_exception = exception;
		}

		/**
		 * Gibt den Wert einer Spalte innerhalb dieser Zeile zurück, ermittelt also einen primitiven Datenwert.
		 * @param column Spalte
		 * @param <T> Typ der Rückgabe, beim Anlegen eines {@link de.kappich.sys.funclib.csv.CsvColumn}-Objektes definiert ({@link IterableCsvData#getColumn(String)})
		 * @return Wert
		 * @throws de.kappich.sys.funclib.csv.CsvParseException Falls ein Problem beim Parsen des Wertes auftrat
		 * @throws IllegalArgumentException Falls das übergebene Spaltenobjekt nicht mit dem richtigen {@link IterableCsvData}-Objekt erzeugt wurde
		 */
		public <T> T getValue(CsvColumn<T> column) throws CsvParseException {
			if(_exception != null){
				throw new CsvParseException(_exception, _row);
			}
			if(column.getCsvData() != IterableCsvData.this){
				throw new IllegalArgumentException("Der column-Parameter passt nicht zum CsvData-Objekt.");
			}
			int columnIndex = column.getColumnIndex();
			String stringVal = _values[columnIndex];
			CsvParser<T> parser = column.getParser();
			try {
				return parser.parseString(stringVal);
			}
			catch(IllegalArgumentException e){
				throw new CsvParseException(e, _row, columnIndex);
			}
		}

		/**
		 * Prüft, ob eine Spalte Daten enthält, also nicht leer ist
		 * @param column Spalte
		 * @return Wert
		 * @throws de.kappich.sys.funclib.csv.CsvParseException Falls ein Problem beim Parsen des Wertes auftrat
		 * @throws IllegalArgumentException Falls das übergebene Spaltenobjekt nicht mit dem richtigen {@link IterableCsvData}-Objekt erzeugt wurde
		 */
		public boolean hasValue(CsvColumn<?> column) throws CsvParseException {
			if(_exception != null){
				throw new CsvParseException(_exception, _row);
			}
			if(column.getCsvData() != IterableCsvData.this){
				throw new IllegalArgumentException("Der column-Parameter passt nicht zum CsvData-Objekt.");
			}
			int columnIndex = column.getColumnIndex();
			String stringVal = _values[columnIndex];
			return !stringVal.isEmpty();
		}

		/**
		 * Gibt den Wert einer Spalte innerhalb dieser Zeile zurück, ermittelt also einen primitiven Datenwert. Gibt <code>null</code> zurück, falls kein Wert angegeben wurde.
		 * @param column Spalte
		 * @param <T> Typ der Rückgabe, beim Anlegen eines {@link de.kappich.sys.funclib.csv.CsvColumn}-Objektes definiert ({@link IterableCsvData#getColumn(String)})
		 * @return Wert
		 * @throws de.kappich.sys.funclib.csv.CsvParseException Falls ein Problem beim Parsen des Wertes auftrat
		 * @throws IllegalArgumentException Falls das übergebene Spaltenobjekt nicht mit dem richtigen {@link IterableCsvData}-Objekt erzeugt wurde
		 */
		public <T> T getValueOptional(CsvColumn<T> column) throws CsvParseException {
			if(_exception != null){
				throw new CsvParseException(_exception, _row);
			}
			if(column.getCsvData() != IterableCsvData.this){
				throw new IllegalArgumentException("Der column-Parameter passt nicht zum CsvData-Objekt.");
			}
			int columnIndex = column.getColumnIndex();
			String stringVal = _values[columnIndex];
			if(stringVal.isEmpty()) return null;
			CsvParser<T> parser = column.getParser();
			try {
				return parser.parseString(stringVal);
			}
			catch(IllegalArgumentException e){
				throw new CsvParseException(e, _row, columnIndex);
			}
		}

		@Override
		public String toString() {
			return "Zeile " + _row + " "  + Arrays.toString(_values);
		}

		public List<String> asList() throws CsvParseException {
			if(_exception != null){
				throw new CsvParseException(_exception, _row);
			}
			return Collections.unmodifiableList(Arrays.asList(_values));
		}

		public String getValue(int columnIndex){
			return _values[columnIndex];
		}
	}
}
