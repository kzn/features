package ru.iitp.proling.features;

import com.google.common.base.Objects;

public class Values {
	public static final class Final implements Value {
		final Object value;
		
		public Final(Object value) {
			this.value = value;
		}
		
		/* (non-Javadoc)
		 * @see ru.iitp.proling.features.Value#get()
		 */
		@Override
		public Object get() {
			return value;
		}
		
		/* (non-Javadoc)
		 * @see ru.iitp.proling.features.Value#isNull()
		 */
		@Override
		public boolean isNull() {
			return value == null;
		}
		
//		@Override
//		public StringBuilder toStringBuilder(StringBuilder sb) {
//			return sb.append(value);
//		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Final))
				return false;
			Final other = (Final) obj;
			return Objects.equal(other.value, value);
		}
		
		@Override
		public String toString() {
			return String.format("value{%s}", value);
		}

	}


	public static class Simple implements Value.Mutable {
		Object value;

		public Simple() {

		}

		public Simple(Object value) {
			this.value = value;
		}

		/* (non-Javadoc)
		 * @see ru.iitp.proling.features.Value#get()
		 */
		@Override
		public Object get() {
			return value;
		}

		@Override
		public void set(Object value) {
			this.value = value;
		}

		/* (non-Javadoc)
		 * @see ru.iitp.proling.features.Value#isNull()
		 */
		@Override
		public boolean isNull() {
			return value == null;
		}

		@Override
		public void clear() {
			value = null;
		}

//		@Override
//		public StringBuilder toStringBuilder(StringBuilder sb) {
//			return sb.append(value);
//		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Simple))
				return false;
			Simple other = (Simple) obj;
			return Objects.equal(other.value, value);
		}

		@Override
		public String toString() {
			return String.format("value{%s}", value);
		}



	}

}
