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
 * Wandelt einen String in einen Long um
 *
 * @author Kappich Systemberatung
 * @version $Revision$
 */
class CsvLongParser implements CsvParser<Long> {
	@Override
	public Long parseString(final String s) throws IllegalArgumentException {
		return Long.parseLong(s.trim());
	}
}
