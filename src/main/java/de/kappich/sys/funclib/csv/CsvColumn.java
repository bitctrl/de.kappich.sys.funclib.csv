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

/**
 * Definiert eine Spalte einer CSV-Datei. Diese Klasse wird von {@link CsvData#getColumn(String, CsvParser)} erzeugt,
 * und ist für die Verwendung in {@link CsvData.CsvRow#getValue(CsvColumn)} vorgesehen.
 *
 * @author Kappich Systemberatung
 * @version $Revision$
 */
public final class CsvColumn<T> {
	private final IterableCsvData _csvData;
	private final int _columnIndex;
	private final CsvParser<T> _parser;

	/**
	 * Konstruktor
	 * @param csvData Csv-Daten
	 * @param columnIndex Spaltenindex innerhalb der CSV-Datei
	 * @param parser Klasse, der den Spaltentext in das Ausgabeformat (T) umwandelt.
	 */
	CsvColumn(final IterableCsvData csvData, final int columnIndex, final CsvParser<T> parser) {
		_csvData = csvData;
		_columnIndex = columnIndex;
		_parser = parser;
	}

	/**
	 * Gibt den Spaltenindex zurück
	 * @return den Spaltenindex
	 */
	int getColumnIndex() {
		return _columnIndex;
	}

	/**
	 * Gibt den Parser zurück
	 * @return den Parser
	 */
	CsvParser<T> getParser() {
		return _parser;
	}

	/**
	 * Gibt das zugehörige Csv-Daten-Objekt zurück
	 * @return das zugehörige Csv-Daten-Objekt
	 */
	IterableCsvData getCsvData() {
		return _csvData;
	}
}
