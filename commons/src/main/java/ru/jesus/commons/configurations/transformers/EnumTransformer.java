/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.jesus.commons.configurations.transformers;

import java.lang.reflect.Field;

import ru.jesus.commons.configurations.PropertyTransformer;
import ru.jesus.commons.configurations.TransformationException;


/**
 * Transforms enum string representation to enum. String must match case definition of enum, for instance:
 * 
 * <pre>
 * enum{
 *  FILE,
 *  URL
 * }
 * </pre>
 * 
 * will be parsed with string "FILE" but not "file".
 * 
 * @author SoulKeeper
 */
public class EnumTransformer implements PropertyTransformer<Enum<?>>
{
	/**
	 * Shared instance of this transformer. It's thread-safe so no need of multiple instances
	 */
	public static final EnumTransformer	SHARED_INSTANCE	= new EnumTransformer();

	/**
	 * Trnasforms string to enum
	 * 
	 * @param value
	 *            value that will be transformed
	 * @param field
	 *            value will be assigned to this field
	 * @return Enum object representing the value
	 * @throws TransformationException
	 *             if somehting went wrong
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Enum<?> transform(String value, Field field,Object ...data) throws TransformationException
	{
		@SuppressWarnings("rawtypes")
		Class<? extends Enum> clazz = (Class<? extends Enum>) field.getType();

		try
		{
			try {
				int val = Integer.parseInt(value);
				for(Enum<?> e : clazz.getEnumConstants()) {
					if(e.ordinal()==val)
						return e;
				}
			} catch(NumberFormatException nfe) {
			}
			for(Enum<?> e : clazz.getEnumConstants()) {
				if(e.name().toUpperCase().equals(value.toUpperCase()))
					return e;
			}
			return null;
		}
		catch(Exception e)
		{
			throw new TransformationException(e);
		}
	}
}