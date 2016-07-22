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

/**
 * @author Kappich Systemberatung
 * @version $Revision$
 */
public class CsvParseException extends IOException {

	private final int _row;
	private final int _col;

	CsvParseException(final Throwable cause, final int row, final int col) {
		super(cause.getMessage() + " Zeile: " + row + " Spalte: " + col, cause);
		_row = row;
		_col = col;
	}

	CsvParseException(final Throwable cause, final int row) {
		super(cause.getMessage() + " Zeile: " + row, cause);
		_row = row;
		_col = -1;
	}

	CsvParseException(final String message, final int row, final int col) {
		super(message + " Zeile: " + row + " Spalte: " + col);
		_row = row;
		_col = col;
	}

	CsvParseException(final String message, final int row) {
		super(message + " Zeile: " + row);
		_row = row;
		_col = -1;
	}

	public int getRow() {
		return _row;
	}

	public int getCol() {
		return _col;
	}
}
