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

import java.util.*;

/**
 * Klasse, die die CSV-Daten aus einer CSV-Datei enthält. Diese Klasse wird mit einem CsvReader erstellt.
 *
 * @author Kappich Systemberatung
 * @version $Revision$
 */
public class CsvData extends IterableCsvData {

	/**
	 * Liste mit Zeilen der Csv-Datei
	 */
	private final List<String[]> _entries;

	/**
	 * Erstellt ein neues CsvData-Objekt
	 */
	CsvData(final String[] headerCells, final HashMap<String, Integer> columnNameToIndexMap, final List<String[]> entries) {
		super(headerCells, columnNameToIndexMap);
		_entries = entries;
	}

	/**
	 * Gibt alle Werte als Rohdaten zurück. Die Rückgabe ist eine Liste mit Zeilen, wobei jede Zeile als String-Array dargestellt wird.
	 * @return alle Werte als Rohdaten
	 */
	public List<String[]> getEntries() {
		return Collections.unmodifiableList(_entries);
	}

	/**
	 * Gibt die Anzahl Zeilen/Datensätze zurück
	 * @return die Anzahl Zeilen
	 */
	public int getNumRows(){
		return _entries.size();
	}

	/**
	 * Gibt die Daten als CsvRow-Objekt zurück, welches verarbeitete Daten zurückgeben kann.
	 * @return Liste mit Datensätzen.
	 * @see #getColumn(String)
	 */
	public List<CsvRow> getRows(){
		return new RowList();
	}

	/**
	 * Iteriert über die Datensätze
	 * @return Iterator über die Datensätze (CsvRows)
	 */
	@Override
	public Iterator<CsvRow> iterator() {
		return getRows().iterator();
	}

	public CsvRow getRow(final int i) {
		return new CsvRow(i, _entries.get(i));
	}

	private class RowList extends AbstractList<CsvRow> {
		@Override
		public CsvRow get(final int index) {
			return new CsvRow(index, _entries.get(index));
		}

		@Override
		public int size() {
			return _entries.size();
		}
	}
}
